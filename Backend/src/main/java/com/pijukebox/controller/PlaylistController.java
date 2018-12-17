package com.pijukebox.controller;

import com.pijukebox.model.Playlist;
import com.pijukebox.service.IPlaylistService;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlaylistController {

    // https://spring.io/guides/gs/accessing-data-mysql/

    private IPlaylistService playlistService;

    public PlaylistController(IPlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @GetMapping("/users/{userId}/playlists")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ApiOperation(value = "Retrieve all playlists of a user.")
    public List<Playlist> getPlaylists(@PathVariable Long userId) {
        return playlistService.findAllByUserId(userId);
    }

    @PostMapping("/users/{userId}/playlists")
    @ApiOperation(value = "Save a new playlist.")
    @PreAuthorize("isCurrentUser(userId)")
    public Playlist savePlaylist(@PathVariable Long userId, @RequestBody Playlist playlist) {
        return playlistService.save(playlist);
    }

    @PatchMapping("/users/{userId}/playlists/{playlistId}")
    @ApiOperation(value = "Update a playlist.")
    @PreAuthorize("isCurrentUser(userId)")
    public Playlist updatePlaylist(@PathVariable Long userId, long playlistId, @RequestBody Playlist playlist) throws Exception {
        Playlist existing = playlistService.findById(playlistId);
        playlist.CopyNonNullProperties(existing);
        return playlistService.save(existing);
    }

    @DeleteMapping("/users/{userId}/playlists/{playlistId}")
    @PreAuthorize("isCurrentUser(userId)")
    @ApiOperation(value = "Delete a playlists of a user.")
    public Playlist deletePlaylist(@PathVariable Long userId, long playlistId) {
        return playlistService.delete(userId, playlistId);
    }
}
