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

    /**
     * Get all users from the database
     * <p>
     * With relations
     *
     * @return All users
     */
    @GetMapping("/users")
    @ApiOperation(value = "Get all information pertaining to users (with relations)")
    public ResponseEntity<List<User>> users() {
        return new ResponseEntity<>(userService.findAllUsers().getBody(), HttpStatus.OK);
    }

    /**
     * Get a user by user ID
     * <p>
     * With relations
     *
     * @param id The user ID
     * @return Zero or one user
     */
    @GetMapping("/users/{id}")
    @ApiOperation(value = "Get all information pertaining to a certain user (with relations) by its ID")
    public ResponseEntity<User> users(@PathVariable Long id) {
        return new ResponseEntity<>(userService.findById(id).getBody(), HttpStatus.OK);
    }

    /**
     * Get all playlist from a user by user ID
     * <p>
     * With relations
     *
     * @param userID The user ID
     * @return Zero or more playlists
     */
    @GetMapping("/users/{userID}/details/playlists")
    @ApiOperation(value = "Get all information pertaining to play" + "lists (with relations) from a user.")
    public ResponseEntity<List<PlaylistWithTracks>> playlistsByUser(@PathVariable Long userID) {
        return new ResponseEntity<>(userService.findPlaylistsByUserId(userID).getBody(), HttpStatus.OK);
    }

    /**
     * Get all playlist from a user by user ID
     * <p>
     * Without relations
     *
     * @param userID The user ID
     * @return Zero or more playlists
     */
    @GetMapping("/users/{userID}/playlists")
    @ApiOperation(value = "Get all information pertaining to a playlist from a user (without relations).")
    public ResponseEntity<List<SimplePlaylist>> simplePlaylistsByUser(@PathVariable Long userID) {
        return new ResponseEntity<>(userService.findSimplePlaylistsByUserId(userID).getBody(), HttpStatus.OK);
    }

    /**
     * Login into frontend
     *
     * @param loginForm A {@link LoginForm LoginForm} object
     * @param response  A {@link HttpServletResponse HttpServletResponse} object
     * @return A login token
     */
    @PostMapping(value = "/login", produces = "application/json")
    @ApiOperation(value = "Login by username and password.")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginForm loginForm, HttpServletResponse response) {
        User user = userService.findByEmailAndPassword(loginForm.getEmail(), loginForm.getPassword()).getBody();
        if (user.getToken() == null || user.getToken().isEmpty()) {
            String token = generateRandomChars("ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890", 17);

            user.setToken(token);
            userService.saveUser(user);
        }
        Map<String, String> tokenResponse = new HashMap<>();
        tokenResponse.put("token", user.getToken());
        return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
    }
}