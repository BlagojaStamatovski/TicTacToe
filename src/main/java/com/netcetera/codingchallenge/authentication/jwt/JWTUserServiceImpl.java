package com.netcetera.codingchallenge.authentication.jwt;

import com.netcetera.codingchallenge.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JWTUserServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final UserDetails userDetails = this.userService.getUserByUsername(username);
        if (userDetails == null) {
            throw new UsernameNotFoundException(String.format("User with username %s could not be found", username));
        } else {
            return userDetails;
        }
    }
}
