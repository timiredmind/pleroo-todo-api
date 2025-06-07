package com.timiremind.plerooo.user.repository.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.timiremind.plerooo.user.entity.DatabaseUser;
import com.timiremind.plerooo.user.repository.PostgreUserRepository;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Optional;
import net.datafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest()
public class PostgreRepositoryTest {

    @Autowired private PostgreUserRepository postgreUserRepository;

    private static final String FIXED_TIME = "2025-06-03T19:54:00Z";
    private final Clock clock = Clock.fixed(Instant.parse(FIXED_TIME), ZoneId.systemDefault());

    private final Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        final DatabaseUser newUser =
                DatabaseUser.builder()
                        .username("test-username")
                        .password(faker.internet().password())
                        .timeCreated(clock.millis())
                        .timeUpdated(clock.millis())
                        .build();

        postgreUserRepository.save(newUser);
    }

    @AfterEach
    void tearDown() {
        postgreUserRepository.deleteAll();
    }

    @Test
    public void should_return_database_user_when_finding_with_username() {
        Optional<DatabaseUser> optionalDatabaseUser =
                postgreUserRepository.findByUsername("test-username");
        assertTrue(optionalDatabaseUser.isPresent());
    }

    @Test
    public void should_return_null_when_finding_by_username() {
        Optional<DatabaseUser> optionalDatabaseUser =
                postgreUserRepository.findByUsername("random-username");
        assertTrue(optionalDatabaseUser.isEmpty());
    }

    @Test
    public void should_return_true_when_username_exists() {
        final boolean existsByUsername = postgreUserRepository.existsByUsername("test-username");
        assertTrue(existsByUsername);
    }

    @Test
    public void should_return_false_username_doesnt_exists() {
        final boolean existsByUsername =
                postgreUserRepository.existsByUsername("random-test-username");
        assertFalse(existsByUsername);
    }

    @Test
    public void should_save_user() {
        final DatabaseUser user =
                DatabaseUser.builder()
                        .username(faker.internet().username())
                        .password(faker.internet().password())
                        .timeCreated(clock.millis())
                        .timeUpdated(clock.millis())
                        .build();

        final DatabaseUser savedUser = postgreUserRepository.save(user);

        assertNotNull(savedUser.getId());
        assertEquals(savedUser.getUsername(), user.getUsername());
        assertEquals(savedUser.getPassword(), user.getPassword());
        assertEquals(savedUser.getTimeCreated(), user.getTimeCreated());
        assertEquals(savedUser.getTimeUpdated(), user.getTimeUpdated());
    }
}
