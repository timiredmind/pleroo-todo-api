package com.timiremind.plerooo.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.timiremind.plerooo.core.exception.InvalidAuthenticationException;
import com.timiremind.plerooo.core.exception.RegistrationException;
import com.timiremind.plerooo.core.jwt.JwtService;
import com.timiremind.plerooo.user.dto.CreateUserRequestDto;
import com.timiremind.plerooo.user.dto.LoginDto;
import com.timiremind.plerooo.user.dto.LoginResponseDto;
import com.timiremind.plerooo.user.entity.DatabaseUser;
import com.timiremind.plerooo.user.repository.UserRepository;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Optional;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserServiceTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private final PasswordEncoder passwordEncoder = spy(BCryptPasswordEncoder.class);
    private final JwtService jwtService = mock(JwtService.class);

    private final UserService userService =
            new UserServiceImpl(userRepository, passwordEncoder, jwtService);

    private final Faker faker = new Faker();

    private final String FIXED_TIME = "2025-06-03T19:54:00Z";
    private final Clock clock = Clock.fixed(Instant.parse(FIXED_TIME), ZoneId.systemDefault());

    @Test
    public void successfullyRegisterUser() {
        final CreateUserRequestDto dto =
                CreateUserRequestDto.builder()
                        .username(faker.internet().username())
                        .password(faker.internet().password())
                        .build();

        final String encryptedPassword = passwordEncoder.encode(dto.password());

        final DatabaseUser savedUser =
                DatabaseUser.builder()
                        .id(faker.internet().uuid())
                        .username(dto.username())
                        .password(encryptedPassword)
                        .timeCreated(clock.millis())
                        .timeUpdated(clock.millis())
                        .build();

        final DatabaseUser newUser =
                DatabaseUser.builder().username(dto.username()).password(encryptedPassword).build();

        when(userRepository.existsByUsername(dto.username())).thenReturn(false);
        when(userRepository.save(newUser)).thenReturn(savedUser);
        when(passwordEncoder.encode(dto.password())).thenReturn(encryptedPassword);

        final String response = userService.register(dto);

        verify(userRepository, times(1)).existsByUsername(dto.username());
        verify(userRepository, times(1)).save(newUser);

        assertEquals("User registered successfully.", response);
    }

    @Test
    public void shouldThrowRegistrationException() {
        final CreateUserRequestDto dto =
                CreateUserRequestDto.builder()
                        .username(faker.internet().username())
                        .password(faker.internet().password())
                        .build();

        when(userRepository.existsByUsername(dto.username())).thenReturn(true);
        assertThrows(
                RegistrationException.class,
                () -> userService.register(dto),
                "User registration failed. Try a different username.");

        verify(userRepository, times(1)).existsByUsername(dto.username());
    }

    @Test
    public void should_successfully_login_user() {
        final String username = faker.internet().username();
        final String password = faker.internet().password();
        final String token =
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"
                        + ".eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUs"
                        + "ImlhdCI6MTUxNjIzOTAyMn0.KMUFsIDTnFmyG3nMiGM6H9FNFUROf3wh7SmqJp-QV30";

        final LoginDto dto = LoginDto.builder().username(username).password(password).build();

        final DatabaseUser user =
                DatabaseUser.builder()
                        .username(username)
                        .password(passwordEncoder.encode(password))
                        .timeUpdated(clock.millis())
                        .timeCreated(clock.millis())
                        .build();

        when(userRepository.findByUsername(dto.username())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(username)).thenReturn(token);

        final LoginResponseDto response = userService.login(dto);

        assertEquals(token, response.token());
    }

    @Test
    public void should_throw_invalid_authentication_for_invalid_username() {
        final String username = faker.internet().username();
        final String password = faker.internet().password();

        final LoginDto dto = LoginDto.builder().username(username).password(password).build();

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrowsExactly(
                InvalidAuthenticationException.class,
                () -> userService.login(dto),
                "Invalid username and password.");
    }

    @Test
    public void should_throw_invalid_authentication_for_incorrect_password() {
        final String username = faker.internet().username();
        final String password = faker.internet().password();
        final String incorrectPassword = faker.internet().password();

        final LoginDto dto =
                LoginDto.builder().username(username).password(incorrectPassword).build();

        final DatabaseUser user =
                DatabaseUser.builder()
                        .username(username)
                        .password(passwordEncoder.encode(password))
                        .timeUpdated(clock.millis())
                        .timeCreated(clock.millis())
                        .build();

        when(userRepository.findByUsername(dto.username())).thenReturn(Optional.of(user));

        assertThrowsExactly(
                InvalidAuthenticationException.class,
                () -> userService.login(dto),
                "Invalid username and password.");
    }
}
