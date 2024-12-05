package com.hieujavapaws.password_manager.repository;

import com.hieujavapaws.password_manager.entity.Password;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PasswordRepository extends JpaRepository<Password, Long> {
    List<Password> findByAppId(Long appId);
    void deleteByAppId(Long appId);
}