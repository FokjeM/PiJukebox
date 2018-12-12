package com.pijukebox.controller;

import com.pijukebox.model.User;
import com.pijukebox.service.RoleService;
import com.pijukebox.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserController {

    private UserService userService;

    private RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    @ApiOperation(value = "Get all users in the application including their role.")
    public List<User> users(@RequestParam(required = false) String role) {
        if (role != null) {
            return userService.findByRole(roleService.findByName(role));
        }
        return userService.findAll();
    }

    @GetMapping("/users/{userId}")
    @ApiOperation(value = "Retrieve a single user and it's role.")
    public User user(@PathVariable Long userId) {
        return userService.findById(userId);
    }
}
