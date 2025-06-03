package com.timiremind.plerooo.user.repository;

import com.timiremind.plerooo.user.entity.DatabaseUser;
import java.time.Clock;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserRepositoryImpl implements UserRepository {

    private final PostgreUserRepository postgreUserRepository;
    private final Clock clock = Clock.systemUTC();

    @Override
    public Optional<DatabaseUser> findByUsername(String username) {
        return postgreUserRepository.findByUsername(username);
    }

    @Override
    public boolean existsByUsername(String username) {
        return postgreUserRepository.existsByUsername(username);
    }

    @Override
    public DatabaseUser save(DatabaseUser user) {
        long now = clock.millis();
        user.setTimeCreated(now);
        user.setTimeUpdated(now);
        return postgreUserRepository.save(user);
    }
}
