package com.pijukebox.configuration;

import com.pijukebox.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class Interceptor extends HandlerInterceptorAdapter {
    private IUserService userService;

    @Autowired
    public Interceptor(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getParameter("Authorization");
        if (!userService.findByToken(token).hasBody()) {
            response.setStatus(403);
            return false;
        }
        return true;
    }
}

