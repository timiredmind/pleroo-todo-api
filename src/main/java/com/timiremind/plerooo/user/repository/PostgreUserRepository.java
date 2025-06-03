package com.timiremind.plerooo.user.repository;

import com.timiremind.plerooo.user.entity.DatabaseUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostgreUserRepository extends JpaRepository<DatabaseUser, String> {

    Optional<DatabaseUser> findByUsername(String username);

    boolean existsByUsername(String username);
}
