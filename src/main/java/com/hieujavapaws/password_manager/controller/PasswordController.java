package com.hieujavapaws.password_manager.controller;

import com.hieujavapaws.password_manager.dto.PasswordDTO;
import com.hieujavapaws.password_manager.service.PasswordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/passwords")
@RequiredArgsConstructor
public class PasswordController {
    private final PasswordService passwordService;

    @PostMapping
    public ResponseEntity<PasswordDTO> createPassword(@Valid @RequestBody PasswordDTO passwordDTO) {
        return ResponseEntity.ok(passwordService.createPassword(passwordDTO));
    }

    @GetMapping("/app/{appId}")
    public ResponseEntity<List<PasswordDTO>> getPasswordsByApp(@PathVariable Long appId) {
        return ResponseEntity.ok(passwordService.getPasswordsByApp(appId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PasswordDTO> getPasswordById(@PathVariable Long id) {
        return ResponseEntity.ok(passwordService.getPasswordById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PasswordDTO> updatePassword(
            @PathVariable Long id,
            @Valid @RequestBody PasswordDTO passwordDTO
    ) {
        return ResponseEntity.ok(passwordService.updatePassword(id, passwordDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassword(@PathVariable Long id) {
        passwordService.deletePassword(id);
        return ResponseEntity.noContent().build();
    }
}