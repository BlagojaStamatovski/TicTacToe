package com.netcetera.codingchallenge.users;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserAuthorityRepository userAuthorityRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
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

    @Override
    public User getUserByUsername(final String username) {
        return this.userRepository.getOne(username);
    }

    @Override
    public boolean addAuthorityToUser(final String userAuthorityId, final String userId) {
        final User user;
        final UserAuthority userAuthority;

        if (!this.userRepository.existsById(userId)) {
            return false;
        } else {
            user = this.userRepository.getOne(userId);
        }

        if (!this.userAuthorityRepository.existsById(userAuthorityId)) {
            this.userAuthorityRepository.save(new UserAuthority(userAuthorityId));
        }

        userAuthority = this.userAuthorityRepository.getOne(userAuthorityId);
        user.getUserAuthorities().add(userAuthority);

        return (this.userRepository.save(user) != null);
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final UserDetails userDetails = this.userRepository.getOne(username);
        if (userDetails == null) {
            throw new UsernameNotFoundException(String.format("User with username %s could not be found", username));
        } else {
            return userDetails;
        }
    }
}
