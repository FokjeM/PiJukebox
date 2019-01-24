package com.pijukebox.controller;

import com.pijukebox.model.LoginForm;
import com.pijukebox.model.playlist.PlaylistWithTracks;
import com.pijukebox.model.simple.SimplePlaylist;
import com.pijukebox.model.user.User;
import com.pijukebox.service.IUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class UserController {

    private IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    @ApiOperation(value = "Get all users in the application including their role.")
    public ResponseEntity<List<User>> users() {
        try {
            return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No users found", ex);
        }
    }

    @GetMapping("/users/{id}")
    @ApiOperation(value = "Retrieve the currently logged in user.")
    public ResponseEntity<User> users(@PathVariable Long id) {
        try {
            if (!userService.findById(id).isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(userService.findById(id).get(), HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No user with ID %s found", id), ex);
        }
    }

    @GetMapping("/users/{userID}/details/playlists")
    @ApiOperation(value = "Retrieve playlists from the logged in user.")
    public ResponseEntity<List<PlaylistWithTracks>> playlistsByUser(@PathVariable Long userID) {
        try {
            if (!userService.findById(userID).isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            if (!userService.findPlaylistsByUser(userID).isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(userService.findPlaylistsByUser(userID).get(), HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No user with ID %s found", userID), ex);
        }
    }

    @GetMapping("/users/{userID}/playlists")
    @ApiOperation(value = "Get all the simple playlists from the logged in user.")
    public ResponseEntity<List<SimplePlaylist>> simplePlaylistsByUser(@PathVariable Long userID) {
        try {
            if (!userService.findById(userID).isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            if (!userService.findSimplePlaylistsByUser(userID).isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(userService.findSimplePlaylistsByUser(userID).get(), HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No user with ID %s found", userID), ex);
        }
    }


    @PostMapping(value = "/login", produces = "application/json")
    @ApiOperation(value = "Login by username and password.")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginForm loginForm, HttpServletResponse response) {

        try {

            if (!userService.findByEmailAndPassword(loginForm.getEmail(), loginForm.getPassword()).isPresent()) {
                response.setStatus(403);
                return new ResponseEntity<>(new HashMap<>(), HttpStatus.BAD_REQUEST);
            }
            User user = userService.findByEmailAndPassword(loginForm.getEmail(), loginForm.getPassword()).get();
            if(user.getToken() == null || user.getToken().isEmpty()){
                //Generate random token
                SecureRandom random = new SecureRandom();
                byte[] bytes = new byte[20];
                random.nextBytes(bytes);
                String token = bytes.toString();

                //Save token
                user.setToken(token);
                userService.saveUser(user);
            }
            Map<String, String> tokenResponse = new HashMap<>();
            tokenResponse.put("token", user.getToken());
            return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", ex);
        }
    }
}