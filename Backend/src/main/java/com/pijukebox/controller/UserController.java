package com.pijukebox.controller;

import com.pijukebox.model.playlist.PlaylistTrack;
import com.pijukebox.model.simple.SimplePlaylist;
import com.pijukebox.model.user.User;
import com.pijukebox.service.IUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with ID {id} Not Found", ex);
        }
    }

    @GetMapping("/users/{userID}/details/playlists")
    @ApiOperation(value = "Retrieve playlists from the logged in user.")
    public ResponseEntity<List<PlaylistTrack>> playlistsByUser(@PathVariable Long userID) {
        try {
            if (!userService.findById(userID).isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            if (!userService.findPlaylistsByUser(userID).isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(userService.findPlaylistsByUser(userID).get(), HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No playlists found for user " + userID, ex);
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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No playlists found for user " + userID, ex);
        }
    }

    /*
        @GetMapping("/playlists")
        @ApiOperation(value = "Retrieve all playlists")
        public ResponseEntity<List<Playlist>> playlists() {
            try {
                return new ResponseEntity<>(playlistService.findAll(), HttpStatus.OK);
            } catch (Exception ex) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No playlists found", ex);
            }
        }
    */
}