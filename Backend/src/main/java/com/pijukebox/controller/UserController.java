package com.pijukebox.controller;

import com.pijukebox.model.User;
import com.pijukebox.service.IUserService;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Transactional
public class UserController {

    private IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    @ApiOperation(value = "Get all users in the application including their role.")
    public List<User> users() {
        return userService.findAll();
    }

    @GetMapping("/users/{userId}")
    @ApiOperation(value = "Retrieve the currently logged in user.")
    public Optional<User> users(@PathVariable Long userId) {
        return userService.findById(userId);
    }

}