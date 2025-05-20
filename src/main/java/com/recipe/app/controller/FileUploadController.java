package com.recipe.app.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.recipe.app.service.FileStorageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/upload")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
public class FileUploadController {

    private final FileStorageService fileStorageService;

    @PostMapping
    public ResponseEntity<UploadResponse> uploadFile(@RequestParam("file") MultipartFile file) {
        log.info("Request to upload file: {}", file.getOriginalFilename());

        if (file.isEmpty()) {
            log.error("File is empty");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            String fileUrl = fileStorageService.storeFile(file);

            UploadResponse response = new UploadResponse(
                    file.getOriginalFilename(),
                    fileUrl,
                    file.getSize());

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (IOException e) {
            log.error("Failed to upload file", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Response class for file uploads
    public record UploadResponse(String filename, String url, long size) {}
}
