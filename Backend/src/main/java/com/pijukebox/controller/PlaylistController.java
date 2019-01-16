package com.pijukebox.controller;

import com.pijukebox.model.playlist.Playlist;
import com.pijukebox.service.IPlaylistService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class PlaylistController {

    private IPlaylistService playlistService;

    public PlaylistController(IPlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @GetMapping()
    @ApiOperation(value = "Retrieve all playlists of a user.")
    public ResponseEntity<List<Playlist>> getPlaylists(@RequestParam("userId") Long userId) {
        return new ResponseEntity<>(playlistService.findAllByUserId(userId), HttpStatus.OK);
    }

    @PostMapping()
    @ApiOperation(value = "Save a new playlist.")
    public ResponseEntity<Playlist> savePlaylist(@PathVariable Long userId, @RequestBody Playlist playlist) {
        return new ResponseEntity<>(playlistService.save(playlist), HttpStatus.OK);
    }
}
