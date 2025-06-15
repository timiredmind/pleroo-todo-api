package com.timiremind.plerooo.task.repository;

import com.timiremind.plerooo.task.entity.DatabaseTask;
import com.timiremind.plerooo.user.entity.DatabaseUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostgresTaskRepository extends JpaRepository<DatabaseTask, String> {

    @Query(
            "SELECT t FROM DatabaseTask t "
                    + "WHERE t.user = :user "
                    + "AND t.timeCreated BETWEEN :startTime AND :endTime")
    Page<DatabaseTask> findAllByUserAndTimeCreatedBetween(
            DatabaseUser user, long startTime, long endTime, Pageable pageable);
}
