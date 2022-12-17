package io.github.maxixcom.otus.booklib.service.security;

import io.github.maxixcom.otus.booklib.domain.User;
import io.github.maxixcom.otus.booklib.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.findUserByUsername(username)
                .map(user ->
                        org.springframework.security.core.userdetails.User
                                .withUsername(user.getUsername())
                                .password(user.getPassword())
                                .roles(user.getRole())
                                .build()
                )
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));
    }

    @Transactional(readOnly = true)
    Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
