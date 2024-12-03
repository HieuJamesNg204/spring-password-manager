package com.hieujavapaws.password_manager.service;

import com.hieujavapaws.password_manager.dto.PasswordDTO;
import com.hieujavapaws.password_manager.entity.App;
import com.hieujavapaws.password_manager.entity.Password;
import com.hieujavapaws.password_manager.exception.ResourceNotFoundException;
import com.hieujavapaws.password_manager.repository.AppRepository;
import com.hieujavapaws.password_manager.repository.PasswordRepository;
import com.hieujavapaws.password_manager.util.EncryptionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PasswordService {
    private final PasswordRepository passwordRepository;
    private final AppRepository appRepository;

    public PasswordDTO createPassword(PasswordDTO passwordDTO) {
        App app = appRepository.findById(passwordDTO.getAppId())
                .orElseThrow(() -> new ResourceNotFoundException("App not found"));

        Password password = new Password();

        password.setUsername(passwordDTO.getUsername());
        try {
            password.setPassword(EncryptionUtil.encrypt(passwordDTO.getPassword()));
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
        password.setNote(passwordDTO.getNote());
        password.setApp(app);

        Password savedPassword = passwordRepository.save(password);

        passwordDTO.setId(savedPassword.getId());
        return passwordDTO;
    }

    public List<PasswordDTO> getPasswordsByApp(Long appId) {
        appRepository.findById(appId)
                .orElseThrow(() -> new ResourceNotFoundException("App not found"));

        return passwordRepository.findByAppId(appId).stream()
                .map(password -> {
                    PasswordDTO dto = new PasswordDTO();
                    dto.setId(password.getId());
                    dto.setUsername(password.getUsername());
                    try {
                        dto.setPassword(EncryptionUtil.decrypt(password.getPassword()));
                    } catch (Exception e) {
                        throw new RuntimeException("Error: " + e.getMessage());
                    }
                    dto.setNote(password.getNote());
                    dto.setAppId(password.getApp().getId());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public PasswordDTO getPasswordById(Long id) {
        Password password = passwordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Password not found"));

        PasswordDTO dto = new PasswordDTO();
        dto.setId(password.getId());
        dto.setUsername(password.getUsername());
        try {
            dto.setPassword(EncryptionUtil.decrypt(password.getPassword()));
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
        dto.setNote(password.getNote());
        dto.setAppId(password.getApp().getId());
        return dto;
    }

    public PasswordDTO updatePassword(Long id, PasswordDTO passwordDTO) {
        Password existingPassword = passwordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Password not found"));

        if (!existingPassword.getApp().getId().equals(passwordDTO.getAppId())) {
            App newApp = appRepository.findById(passwordDTO.getAppId())
                    .orElseThrow(() -> new ResourceNotFoundException("App not found"));
            existingPassword.setApp(newApp);
        }

        existingPassword.setUsername(passwordDTO.getUsername());
        try {
            existingPassword.setPassword(EncryptionUtil.encrypt(passwordDTO.getPassword()));
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
        existingPassword.setNote(passwordDTO.getNote());

        Password updatedPassword = passwordRepository.save(existingPassword);

        passwordDTO.setId(updatedPassword.getId());
        passwordDTO.setAppId(updatedPassword.getApp().getId());
        return passwordDTO;
    }

    public void deletePassword(Long id) {
        passwordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Password not found"));
        passwordRepository.deleteById(id);
    }
}