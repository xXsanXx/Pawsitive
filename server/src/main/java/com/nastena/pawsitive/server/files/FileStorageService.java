package com.nastena.pawsitive.server.files;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {

    private static final String UPLOAD_DIR = "uploads/";

    public String saveFile(MultipartFile file) {

        try {

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

            Path path = Paths.get(UPLOAD_DIR + fileName);

            Files.createDirectories(path.getParent());

            Files.copy(file.getInputStream(), path);

            return fileName;

        } catch (IOException e) {
            throw new RuntimeException("File upload failed");
        }
    }
}