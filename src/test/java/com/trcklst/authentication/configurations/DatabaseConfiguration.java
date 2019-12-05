package com.trcklst.authentication.configurations;

import com.trcklst.authentication.core.db.User;
import com.trcklst.authentication.core.db.UserRepository;
import com.trcklst.authentication.mock.UserMock;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@TestConfiguration
@RequiredArgsConstructor
public class DatabaseConfiguration {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner createAccounts() {
        return args -> {
            User user = UserMock.VALID_USER.toBuilder()
                    .password(passwordEncoder.encode(UserMock.VALID_USER.getPassword()))
                    .build();
            userRepository.save(user);
        };
    }
}
