package com.pijukebox.configuration;

import com.pijukebox.model.User;
import com.pijukebox.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class Interceptor extends HandlerInterceptorAdapter {
    private static Logger log = LoggerFactory.getLogger(Interceptor.class);


    private IUserService userService;

    @Autowired
    public Interceptor(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception
    {
        //Check if token exists
        String token = request.getHeader("Authorization");
        User user = userService.findByToken(token).get();
        if(user == null){
            response.setStatus(403);
            return false;
        }
        else{
            //Add user?
            //https://stackoverflow.com/questions/3806255/spring-web-mvc-pass-an-object-from-handler-interceptor-to-controller
            return true;
        }
    }
}

