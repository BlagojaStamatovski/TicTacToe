package com.netcetera.codingchallenge.users;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserInitializer implements InitializingBean {

    final UserService userService;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.userService.addNewUser("X", "passwordX");
        this.userService.addAuthorityToUser("START_GAME", "X");
        this.userService.addNewUser("O", "passwordO");
    }
}               
