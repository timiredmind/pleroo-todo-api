package com.timiremind.plerooo.user.repository.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.timiremind.plerooo.user.TestConfig;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@ActiveProfiles(profiles = {"test"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
@Import(TestConfig.class)
public class UserRepositoryTest {

    @Autowired private PostgreUserRepository postgreUserRepository;

    @Autowired private UserRepository repository;

    @Autowired private Clock clock;

    private final Faker faker = new Faker();

    @BeforeEach
    public void setUp() {
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
    public void should_return_database_user_when_finding_with_username() {
        Optional<DatabaseUser> optionalDatabaseUser = repository.findByUsername("test-username");
        assertTrue(optionalDatabaseUser.isPresent());
        assertEquals(optionalDatabaseUser.get().getTimeCreated(), clock.millis());
    }

    //    @Test
    //    public void should_save_user() {
    //        //        repository.fin
    //    }
}
