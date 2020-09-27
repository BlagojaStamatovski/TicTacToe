package com.netcetera.codingchallenge.users;

public interface UserService {
    void addNewUser(String username, String password);

    User getUserByUsername(String username);

    void addAuthorityToUser(String userAuthorityId, String userId);
}
