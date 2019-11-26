package com.trcklst.authentication.configurations;

import com.trcklst.authentication.mock.AccountMock;
import com.trcklst.authentication.core.Account;
import com.trcklst.authentication.core.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@TestConfiguration
@RequiredArgsConstructor
public class DatabaseConfiguration {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner createAccounts() {
        return args -> {
            Account account = AccountMock.ACCOUNT_VALID_USER.toBuilder()
                    .password(passwordEncoder.encode(AccountMock.ACCOUNT_VALID_USER.getPassword()))
                    .build();
            accountRepository.save(account);
        };
    }
}
