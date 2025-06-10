package com.timiremind.plerooo.task.repository;

import com.timiremind.plerooo.task.entity.DatabaseTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostgresTaskRepository extends JpaRepository<DatabaseTask, String> {}
