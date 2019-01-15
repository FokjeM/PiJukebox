package com.pijukebox.controller;

import com.pijukebox.model.Playlist;
import com.pijukebox.service.IPlaylistService;
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
public class PlaylistController {

    private final IPlaylistService playlistService;

    @Autowired
    public PlaylistController(IPlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @GetMapping("/playlists/details")
    @ApiOperation(value = "Retrieve all playlists in full detail, with full track info")
    public ResponseEntity<List<Playlist>> playlists() {
        try {
            return new ResponseEntity<>(playlistService.findAll(), HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No playlists found", ex);
        }
    }

    @GetMapping("/playlists/{id}")
    @ApiOperation(value = "Get all from a single playlist by its ID")
    public ResponseEntity<Playlist> playlistDetails(@PathVariable Long id) {
        try {
            if (!playlistService.findById(id).isPresent()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(playlistService.findById(id).get(), HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Playlist with ID {id} not found", ex);
        }
    }

//    @GetMapping("/playlists")
}
