package com.hieujavapaws.password_manager.controller;

import com.hieujavapaws.password_manager.dto.AppDTO;
import com.hieujavapaws.password_manager.service.AppService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/apps")
@RequiredArgsConstructor
public class AppController {
    private final AppService appService;

    @PostMapping
    public ResponseEntity<AppDTO> createApp(@Valid @RequestBody AppDTO appDTO) {
        return ResponseEntity.ok(appService.createApp(appDTO));
    }

    @GetMapping
    public ResponseEntity<List<AppDTO>> getAllApps() {
        return ResponseEntity.ok(appService.getAllApps());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppDTO> updateApp(@PathVariable Long id, @Valid @RequestBody AppDTO appDTO) {
        return ResponseEntity.ok(appService.updateApp(id, appDTO));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteApp(@PathVariable Long id) {
        appService.deleteApp(id);
        return ResponseEntity.noContent().build();
    }
}