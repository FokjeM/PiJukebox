package com.pijukebox.controller;

import com.pijukebox.model.User;
import com.pijukebox.service.IUserService;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationController {

    private IUserService userService;

    public AuthenticationController(IUserService userService) {
        this.userService = userService;
    }

    public boolean isCurrentUser(Long userId) {
        return getUser().getId().equals(userId);
    }

    private User getUser() {
        // https://docs.spring.io/spring-security/site/docs/5.1.2.RELEASE/reference/htmlsingle/#obtaining-information-about-the-current-user
        // https://www.baeldung.com/get-user-in-spring-security
        // https://www.baeldung.com/spring-security-method-security
        // https://www.baeldung.com/spring-security-expressions-basic

        // TODO Test if this code is correct.
        return userService.findById(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getId());
    }
}
