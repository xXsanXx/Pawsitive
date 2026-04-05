package com.nastena.pawsitive.server.files;

import com.nastena.pawsitive.dto.ErrorCode;
import com.nastena.pawsitive.server.exceptions.ServerRuntimeException;
import com.nastena.pawsitive.utils.FileUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {

    private static final String UPLOAD_DIR = "uploads";

    public String saveFile(MultipartFile file) {

        try {

            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();

            Path path = Paths.get(UPLOAD_DIR).resolve(filename);

            Files.createDirectories(path.getParent());

            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            return filename;

        } catch (IOException e) {
            throw new ServerRuntimeException("File upload failed", ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    public void deleteFile(String filename) {
        try {
            Path path = Paths.get(UPLOAD_DIR).resolve(filename);

            boolean deleted = Files.deleteIfExists(path);

            if (!deleted) {
                throw new ServerRuntimeException("File %s not found".formatted(filename),
                        ErrorCode.INTERNAL_SERVER_ERROR);
            }

        } catch (IOException e) {
            throw new ServerRuntimeException("Failed to delete file %s: %s".formatted(filename, e.getMessage()),
                    ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    public Resource loadAsResource(String filename) {
        try {
            Path path = Paths.get(UPLOAD_DIR).resolve(filename);

            Resource resource = new UrlResource(path.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            throw new ServerRuntimeException("File %s not found".formatted(filename), ErrorCode.INTERNAL_SERVER_ERROR);

        } catch (MalformedURLException e) {
            throw new ServerRuntimeException("File %s not found".formatted(filename), ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}