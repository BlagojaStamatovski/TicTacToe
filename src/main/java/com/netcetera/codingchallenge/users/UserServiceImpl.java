package com.netcetera.codingchallenge.users;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void addNewUser(final String username, final String password) {
        if (this.userRepository.existsById(username)) {

        } else {
            final String encodedPassword = this.passwordEncoder.encode(password);
            this.userRepository.save(
                    User.builder()
                            .username(username)
                            .password(encodedPassword)
                            .build());
        }
    }

    public User getUserByUsername(final String username) {
        return this.userRepository.getOne(username);
    }
}
