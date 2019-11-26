package com.trcklst.authentication.core;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultUserDetailService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> accountDb = accountRepository.findByUsername(username);
        return accountDb.map(this::createUser)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found"));
    }

    private UserDetails createUser(Account account) {
        return User.builder()
                .authorities(account.getAuthority().name())
                .username(account.getUsername())
                .password(account.getPassword())
                .accountExpired(!account.isActive())
                .accountExpired(!account.isActive())
                .disabled(!account.isActive())
                .build();
    }
}
