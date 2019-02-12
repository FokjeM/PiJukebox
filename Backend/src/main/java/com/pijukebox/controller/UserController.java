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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class UserController {

    private IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    private static String generateRandomChars(String candidateChars, int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(candidateChars.charAt(random.nextInt(candidateChars.length())));
        }

        return sb.toString();
    }

    @GetMapping("/users")
    @ApiOperation(value = "Get all information pertaining to users (with relations)")
    public ResponseEntity<List<User>> users() {
        try {
            return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No users found", ex);
        }
    }

    @GetMapping("/users/{id}")
    @ApiOperation(value = "Get all information pertaining to a certain user (with relations) by its ID")
    public ResponseEntity<User> users(@PathVariable Long id) {
        try {
            if (!userService.findById(id).isPresent()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(userService.findById(id).get(), HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No user with ID %s found", id), ex);
        }
    }

    @GetMapping("/users/{userID}/details/playlists")
    @ApiOperation(value = "Get all information pertaining to playlists (with relations) from a user.")
    public ResponseEntity<List<PlaylistWithTracks>> playlistsByUser(@PathVariable Long userID) {
        try {
            if (!userService.findById(userID).isPresent()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            if (!userService.findPlaylistsByUserId(userID).isPresent()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(userService.findPlaylistsByUserId(userID).get(), HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No user with ID %s found", userID), ex);
        }
    }

    @GetMapping("/users/{userID}/playlists")
    @ApiOperation(value = "Get all information pertaining to a playlist from a user.")
    public ResponseEntity<List<SimplePlaylist>> simplePlaylistsByUser(@PathVariable Long userID) {
        try {
            if (!userService.findById(userID).isPresent()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            if (!userService.findSimplePlaylistsByUserId(userID).isPresent()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(userService.findSimplePlaylistsByUserId(userID).get(), HttpStatus.OK);
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
            if (user.getToken() == null || user.getToken().isEmpty()) {
                String token = generateRandomChars("ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890", 17);

                // Save token
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