package com.pijukebox.controller;

import com.pijukebox.model.LoginForm;
import com.pijukebox.model.User;
import com.pijukebox.repository.IUserRepository;
import com.pijukebox.service.IUserService;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.security.SecureRandom;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

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
        try {
            return userService.findAll();
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No users found", ex);
        }
    }

    @GetMapping("/users/{id}")
    @ApiOperation(value = "Retrieve the currently logged in user.")
    public Optional<User> users(@PathVariable Long id) {
        try {
            if (!userService.findById(id).isPresent()) {
                return Optional.empty();
            }
            return Optional.of(userService.findById(id).get());
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with ID {id} Not Found", ex);
        }
    }

    @PostMapping(value = "/login", produces = "application/json")
    @ApiOperation(value = "Login by username and password.")
    public String login(@RequestBody LoginForm loginForm, HttpServletResponse response){

        try {
            if (!userService.findByEmailAndPassword(loginForm.getEmail(), loginForm.getPassword()).isPresent()) {
                response.setStatus(403);
                return Optional.empty().toString();
            }

            //Generate random token
            SecureRandom random = new SecureRandom();
            byte bytes[] = new byte[20];
            random.nextBytes(bytes);
            String token = bytes.toString();

            //Save token
            User user = userService.findByEmailAndPassword(loginForm.getEmail(), loginForm.getPassword()).get();
            user.setToken(token);
            userService.saveUser(user);
            return "{\"token\":\"" + token + "\"}";
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with ID {id} Not Found", ex);
        }
    }
}