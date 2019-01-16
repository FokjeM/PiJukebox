package com.pijukebox.controller;

import com.pijukebox.model.playlist.PlaylistWithTracks;
import com.pijukebox.model.simple.SimplePlaylist;
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
    @ApiOperation(value = "Retrieve all simple playlists")
    public ResponseEntity<List<SimplePlaylist>> playlists() {
        try {
            return new ResponseEntity<>(playlistService.findAllSimplePlaylists(), HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No simple playlists found", ex);
        }
    }

    @GetMapping("/playlists/{id}")
    @ApiOperation(value = "Get a single simple playlist by its ID")
    public ResponseEntity<SimplePlaylist> simplePlaylistDetails(@PathVariable Long id) {
        try {
            if (!playlistService.findSimplePlaylistById(id).isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(playlistService.findSimplePlaylistById(id).get(), HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Playlist with ID {id} not found", ex);
        }
    }

    @PostMapping("/playlists")
    @ApiOperation(value = "Create a new empty Playlist")
    public ResponseEntity<SimplePlaylist> addSimplePlaylist(@RequestBody SimplePlaylist simplePlaylist) {
        try {
            return new ResponseEntity<>(playlistService.addNewPlaylist(simplePlaylist), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Playlist not created", ex);
        }
    }

    @GetMapping("/details/playlists")
    @ApiOperation(value = "Retrieve all playlists in full detail, with full track info")
    public ResponseEntity<List<PlaylistWithTracks>> detailedPlaylists() {
        try {
            return new ResponseEntity<>(playlistService.findAll(), HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No playlists found", ex);
        }
    }

    @GetMapping("/details/playlists/{id}")
    @ApiOperation(value = "Get all from a single playlist by its ID")
    public ResponseEntity<PlaylistWithTracks> playlistDetails(@PathVariable Long id) {
        try {
            if (!playlistService.findById(id).isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(playlistService.findById(id).get(), HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Playlist with ID {id} not found", ex);
        }
    }

    @PatchMapping("/details/playlists/{id}/{trackId}")
    @ApiOperation(value = "Add a track to a playlist")
    public ResponseEntity<PlaylistWithTracks> addTrackToPlaylist(@PathVariable Long id, @PathVariable Long trackId) {
        try {
            if (!playlistService.findById(id).isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            //check for the track id get
            //add the track to the playlist
            PlaylistWithTracks playlistWithTracks = playlistService.findById(id).get();
            //playlistWithTracks.getTracks().add(track)
            playlistService.addTrackToPlaylist(playlistWithTracks);
            return new ResponseEntity<>(playlistService.findById(id).get(), HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Playlist with ID {id} not found", ex);
        }
    }

    @PatchMapping("/details/playlists/remove/{id}/{trackId}")
    @ApiOperation(value = "Remove a track from a playlist")
    public ResponseEntity<PlaylistWithTracks> removeTrackFromPlaylist(@PathVariable Long id, @PathVariable Long trackId) {
        try {
            if (!playlistService.findById(id).isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            // check for the track id get
            // add the track to the playlist
            PlaylistWithTracks playlistWithTracks = playlistService.findById(id).get();
            //playlistWithTracks.getTracks().remove(track);
            playlistService.deleteTrackFromPlaylist(playlistWithTracks);
            return new ResponseEntity<>(playlistService.findById(id).get(), HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Playlist with ID {id} not found", ex);
        }
    }
}
