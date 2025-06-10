package com.timiremind.plerooo.task.entity;

import com.timiremind.plerooo.user.entity.DatabaseUser;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
@AllArgsConstructor
@Table(name = "tasks")
public class DatabaseTask {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private DatabaseUser user;

    @Column(nullable = false)
    private String title;

    @Column private String description;

    @Column(name = "due_date")
    private Long dueDate;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Priority priority;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Status status;

    @Column(name = "time_created", nullable = false)
    private Long timeCreated;

    @Column(name = "time_updated", nullable = false)
    private Long timeUpdated;

    public DatabaseTask() {}
}
