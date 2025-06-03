package com.timiremind.plerooo.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Data
@Table(name = "users")
@Entity
@Builder
public class DatabaseUser {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "time_created", nullable = false)
    private Long timeCreated;

    @Column(name = "time_updated", nullable = false)
    private Long timeUpdated;

    public DatabaseUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public DatabaseUser(
            String id, String username, String password, Long timeCreated, Long timeUpdated) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
    }

    public DatabaseUser() {}
}
