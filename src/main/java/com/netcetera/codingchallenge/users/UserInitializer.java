package com.netcetera.codingchallenge.users;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserInitializer implements InitializingBean {

    final UserServiceImpl userService;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.userService.addNewUser("X", "passwordX");
        this.userService.addAuthorityToUser("start_game", "X");
        this.userService.addNewUser("O", "passwordO");
    }
}               
