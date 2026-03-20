package com.nastena.pawsitive.server.debug;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@ConditionalOnProperty(name = "custom.dev-mode", havingValue = "true")
@RequestMapping("/api/dev")
public class DevController {

    @Autowired
    private DevService devService;

    @PostMapping("/reset")
    public ResponseEntity<?> reset() {
        log.info("Resetting all data");

        devService.clearAllTables();
        return ResponseEntity.ok("Reset successful!");
    }
}
