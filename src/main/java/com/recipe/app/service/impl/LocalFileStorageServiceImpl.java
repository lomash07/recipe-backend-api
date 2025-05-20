package com.recipe.app.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.recipe.app.service.FileStorageService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LocalFileStorageServiceImpl implements FileStorageService {

    private final Path fileStorageLocation;

    public LocalFileStorageServiceImpl(@Value("${app.upload.dir:${user.home}/uploads/recipe-images}") String uploadDir) {
        this.fileStorageLocation = Paths.get(uploadDir)
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            log.error("Could not create the directory where the uploaded files will be stored", ex);
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored", ex);
        }
    }

    @Override
    public String storeFile(MultipartFile file) throws IOException {
        // Normalize file name
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());

        // Check if the file's name contains invalid characters
        if (originalFileName.contains("..")) {
            throw new IOException("Sorry! Filename contains invalid path sequence " + originalFileName);
        }

        // Generate a unique filename to avoid collisions
        String extension = "";
        int lastIndex = originalFileName.lastIndexOf('.');
        if (lastIndex > 0) {
            extension = originalFileName.substring(lastIndex);
        }
        String filename = UUID.randomUUID().toString() + extension;

        // Copy file to the target location (replacing existing file with the same name)
        Path targetLocation = this.fileStorageLocation.resolve(filename);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        log.info("Stored file: {} as {}", originalFileName, filename);

        // Return the URL for the saved file
        return "/images/" + filename;
    }
}