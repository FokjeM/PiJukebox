package com.pijukebox.controller;

import com.pijukebox.model.User;
import com.pijukebox.service.IRoleService;
import com.pijukebox.service.IUserService;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserController {

    // https://spring.io/guides/gs/accessing-data-mysql/

    private IUserService userService;

    private IRoleService roleService;

    @Autowired
    public UserController(IUserService userService, IRoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    @ApiOperation(value = "Get all users in the application including their role.")
//    @PreAuthorize("hasRole('ROLE_USER')")
    public List<User> users(@RequestParam(required = false) String role) {

        System.out.println("TEST");

        if (role != null) {
            return userService.findByRole(roleService.findByName(role));
        }
        return userService.findAll();
    }

    @GetMapping("/users/{userId}")
    @ApiOperation(value = "Retrieve the currently logged in user.")
//    @PreAuthorize("hasRole('ROLE_USER')")
    public User users(@PathVariable Long userId) {
        return userService.findById(userId);
    }

    @GetMapping("/users/me")
    @ApiOperation(value = "Retrieve the currently logged in user.")
//    @PreAuthorize("hasRole('ROLE_USER')")
    public User currentUser(Authentication authentication) {
        return ((UserDetails) authentication.getPrincipal()).getUser();
    }
}