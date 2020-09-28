package com.netcetera.codingchallenge.users;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void addNewUser(String username, String password);

    User getUserByUsername(String username);

    boolean addAuthorityToUser(String userAuthorityId, String userId);
}
