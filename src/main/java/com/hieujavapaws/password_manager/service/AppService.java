package com.hieujavapaws.password_manager.service;

import com.hieujavapaws.password_manager.dto.AppDTO;
import com.hieujavapaws.password_manager.entity.App;
import com.hieujavapaws.password_manager.exception.DuplicateResourceException;
import com.hieujavapaws.password_manager.exception.ResourceNotFoundException;
import com.hieujavapaws.password_manager.repository.AppRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppService {
    private final AppRepository appRepository;

    public AppDTO createApp(AppDTO appDTO) {
        if (appRepository.existsByUrl(appDTO.getUrl())) {
            throw new DuplicateResourceException("URL already exists");
        }
        App app = new App();
        app.setName(appDTO.getName());
        app.setUrl(appDTO.getUrl());
        App savedApp = appRepository.save(app);

        appDTO.setId(savedApp.getId());
        return appDTO;
    }

    public List<AppDTO> getAllApps() {
        return appRepository.findAll().stream()
                .map(app -> {
                    AppDTO dto = new AppDTO();
                    dto.setId(app.getId());
                    dto.setName(app.getName());
                    dto.setUrl(app.getUrl());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public AppDTO updateApp(Long id, AppDTO appDTO) {
        App existingApp = appRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("App not found"));

        if (!existingApp.getUrl().equals(appDTO.getUrl()) &&
                appRepository.existsByUrl(appDTO.getUrl())) {
            throw new DuplicateResourceException("URL already exists");
        }

        existingApp.setName(appDTO.getName());
        existingApp.setUrl(appDTO.getUrl());
        App updatedApp = appRepository.save(existingApp);

        appDTO.setId(updatedApp.getId());
        return appDTO;
    }

    public void deleteApp(Long id) {
        appRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("App not found"));
        appRepository.deleteById(id);
    }
}