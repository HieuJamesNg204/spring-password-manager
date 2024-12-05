package com.hieujavapaws.password_manager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class App {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "url", nullable = false, unique = true, length = 500)
    private String url;

    @OneToMany(mappedBy = "app", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Password> passwords;
}