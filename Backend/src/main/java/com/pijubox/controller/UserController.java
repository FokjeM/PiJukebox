package com.pijubox.controller;

import com.pijubox.model.User;
import com.pijubox.service.RoleService;
import com.pijubox.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    @ApiOperation(value = "Get all users in the application including their role.", notes = "Only works as admin")
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
