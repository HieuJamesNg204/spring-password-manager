package com.hieujavapaws.password_manager.repository;

import com.hieujavapaws.password_manager.entity.App;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppRepository extends JpaRepository<App, Long> {
    boolean existsByUrl(String url);
    List<App> searchByNameContainingIgnoreCase(String name);
}