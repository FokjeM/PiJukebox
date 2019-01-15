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

    @GetMapping("/playlists")
    @ApiOperation(value = "Retrieve all playlists")
    public ResponseEntity<List<Playlist>> playlists() {
        try {
            return new ResponseEntity<>(playlistService.findAll(), HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No playlists found", ex);
        }
    }

//    @ApiOperation(value = "Retrieve all playlists of a user.")
//    public List<Playlist> getPlaylists(@RequestParam("userID") Long userID) {
//        return playlistService.findAllByUserID(userID);
//    }

}
