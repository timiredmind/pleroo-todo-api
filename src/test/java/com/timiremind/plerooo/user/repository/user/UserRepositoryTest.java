package com.timiremind.plerooo.user.repository.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.timiremind.plerooo.user.RepositoryTest;
import com.timiremind.plerooo.user.entity.DatabaseUser;
import com.timiremind.plerooo.user.repository.PostgreUserRepository;
import com.timiremind.plerooo.user.repository.UserRepository;
import java.time.Clock;
import java.util.Optional;
import net.datafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
public class UserRepositoryTest {

    @Autowired private PostgreUserRepository postgreUserRepository;

    @Autowired private UserRepository repository;

    @Autowired private Clock clock;

    private final Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        final DatabaseUser newUser =
                DatabaseUser.builder()
                        .username("test-username")
                        .password(faker.internet().password())
                        .build();
        repository.save(newUser);
    }

    @AfterEach
    void tearDown() {
        postgreUserRepository.deleteAll();
    }

    @Test
    void should_return_database_user_when_finding_with_username() {
        Optional<DatabaseUser> optionalDatabaseUser = repository.findByUsername("test-username");
        assertTrue(optionalDatabaseUser.isPresent());
        assertEquals(optionalDatabaseUser.get().getTimeCreated(), clock.millis());
    }

    @Test
    void should_return_empty_optional_when_finding_with_invalid_username() {
        Optional<DatabaseUser> optionalDatabaseUser =
                repository.findByUsername("invalid-test-username");
        assertTrue(optionalDatabaseUser.isEmpty());
    }

    @Test
    void should_return_true_when_username_exists() {
        final boolean exists = repository.existsByUsername("test-username");
        assertTrue(exists);
    }

    @Test
    void should_return_false_when_username_doesnot_exist() {
        final boolean exists = repository.existsByUsername("invalid-username");
        assertFalse(exists);
    }

    @Test
    void should_save_database_user() {
        final DatabaseUser newUser =
                DatabaseUser.builder()
                        .username(faker.internet().username())
                        .password(faker.internet().password())
                        .build();
        final DatabaseUser savedUser = repository.save(newUser);

        assertNotNull(savedUser.getId());
        assertEquals(newUser.getUsername(), savedUser.getUsername());
        assertEquals(newUser.getPassword(), savedUser.getPassword());
        assertEquals(savedUser.getTimeCreated(), clock.millis());
        assertEquals(savedUser.getTimeUpdated(), clock.millis());
    }
}
