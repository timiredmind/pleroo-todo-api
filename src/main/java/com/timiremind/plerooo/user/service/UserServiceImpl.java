package com.timiremind.plerooo.user.service;

import static com.timiremind.plerooo.core.util.StringUtil.maskString;

import com.timiremind.plerooo.core.exception.RegistrationException;
import com.timiremind.plerooo.user.dto.CreateUserRequestDto;
import com.timiremind.plerooo.user.entity.DatabaseUser;
import com.timiremind.plerooo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public String register(CreateUserRequestDto dto) {
        if (userRepository.existsByUsername(dto.username())) {
            throw new RegistrationException("User registration failed. Try a different username.");
        }
        final String encryptedPassword = passwordEncoder.encode(dto.password());
        final DatabaseUser newUser = new DatabaseUser(dto.username(), encryptedPassword);
        final DatabaseUser savedUser = userRepository.save(newUser);
        logger.info(
                "Created new user with id {} and username {}",
                savedUser.getId(),
                maskString(savedUser.getUsername(), 4));

        return "User registered successfully.";
    }
}
