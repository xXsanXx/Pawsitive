package com.nastena.pawsitive.server.files;

import com.nastena.pawsitive.utils.FileUtils;
import org.springframework.core.io.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(FileUtils.MAPPING)
@RequiredArgsConstructor
public class FileController {

    private final FileStorageService fileStorageService;

    @GetMapping(FileUtils.ENDPOINT + "/{filename}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {

        Resource file = fileStorageService.loadAsResource(filename);

        return ResponseEntity.ok()
                .contentType(MediaTypeFactory
                        .getMediaType(file)
                        .orElse(MediaType.APPLICATION_OCTET_STREAM))
                .body(file);
    }
}
