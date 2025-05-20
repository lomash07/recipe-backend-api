package com.recipe.app.service;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    /**
     * Store a file and return its URL
     *
     * @param file the file to store
     * @return the URL of the stored file
     * @throws IOException if an error occurs during file storage
     */
    String storeFile(MultipartFile file) throws IOException;
}