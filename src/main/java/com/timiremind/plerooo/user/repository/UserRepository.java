package com.timiremind.plerooo.user.repository;

import com.timiremind.plerooo.user.entity.DatabaseUser;
import java.util.Optional;

public interface UserRepository {
    Optional<DatabaseUser> findByUsername(String username);

    boolean existsByUsername(String username);

    DatabaseUser save(DatabaseUser user);
}
