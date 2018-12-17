package com.pijukebox.controller;

import com.pijukebox.model.Playlist;
import com.pijukebox.model.User;
import com.pijukebox.service.IPlaylistService;
import com.pijukebox.service.IRoleService;
import com.pijukebox.service.IUserService;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserController {

    private IUserService userService;

    private IRoleService roleService;

    private IPlaylistService playlistService;

    public UserController(IUserService userService, IRoleService roleService, IPlaylistService playlistService) {
        this.userService = userService;
        this.roleService = roleService;
        this.playlistService = playlistService;
    }

    @GetMapping("/users")
    @ApiOperation(value = "Get all users in the application including their role.")
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<User> users(@RequestParam(required = false) String role) {
        if (role != null) {
            return userService.findByRole(roleService.findByName(role));
        }
        return userService.findAll();
    }

    @GetMapping("/users/{userId}")
    @ApiOperation(value = "Retrieve a single user and it's role.")
    @PreAuthorize("hasRole('ROLE_USER')")
    public User user(@PathVariable Long userId) {
        return userService.findById(userId);
    }

    @GetMapping(value = "/users/me")
    @ApiOperation(value = "Retrieve the currently logged in user.")
    @PreAuthorize("hasRole('ROLE_USER')")
    public User currentUser(Authentication authentication) {
        return ((UserDetails) authentication.getPrincipal()).getUser();
    }

    @GetMapping("/users/{userId}/playlists")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ApiOperation(value = "Retrieve all playlists of a user.")
    public List<Playlist> userPlaylists(@PathVariable Long userId) {
        return playlistService.findAllByUserId(userId);
    }

    @PostMapping("/users/{userId}/playlists")
    @ApiOperation(value = "Save a new playlist.")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Playlist savePlaylist(@PathVariable Long userId) {
        return playlistService.save(userId);
    }

    @DeleteMapping("/users/{userId}/playlists/{playlistId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or isCurrentUser(userId)")
    @ApiOperation(value = "Delete a playlists of a user.", notes = "Only the current user can delete a playlist and someone with the Admin role.")
    public Playlist deletePlaylist(@PathVariable Long userId, long playlistId) {
        return playlistService.delete(userId, playlistId);
    }
}