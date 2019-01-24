package com.pijukebox.controller;

import com.pijukebox.model.playlist.PlaylistWithTracks;
import com.pijukebox.model.simple.SimplePlaylist;
import com.pijukebox.model.simple.SimpleTrack;
import com.pijukebox.service.IPlaylistService;
import com.pijukebox.service.ITrackService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class PlaylistController {

    private final IPlaylistService playlistService;
    private final ITrackService trackService;

    @Autowired
    public PlaylistController(IPlaylistService playlistService, ITrackService trackService) {
        this.playlistService = playlistService;
        this.trackService = trackService;
    }

    @GetMapping("/playlists")
    @ApiOperation(value = "Retrieve all simple playlists")
    public ResponseEntity<List<SimplePlaylist>> playlists(@RequestParam(name="name", required = false) String name) {
        try {
            if(name != null && !name.isEmpty())
            {
                if(!playlistService.findSimplePlaylistsByName(name).isPresent())
                {
                   return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<>(playlistService.findSimplePlaylistsByName(name).get(), HttpStatus.OK);
            }
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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Playlist with ID %s not found", id), ex);
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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Playlist with ID %s not found", id), ex);
        }
    }

    @PatchMapping("/details/playlists/{playlistID}/tracks/{trackId}")
    @ApiOperation(value = "Add a track to a playlist")
    public ResponseEntity<PlaylistWithTracks> addTrackToPlaylist(@PathVariable Long playlistID, @PathVariable Long trackId) {
        try {
            if (!playlistService.findById(playlistID).isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            if (!trackService.findSimpleTrackById(trackId).isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            PlaylistWithTracks playlistTrack = playlistService.findById(playlistID).get();
            Set<SimpleTrack> trackSet = playlistTrack.getTracks();
            trackSet.add(trackService.findSimpleTrackById(trackId).get());
            playlistTrack.setTracks(trackSet);
            playlistService.addTrackToPlaylist(playlistTrack);
            return new ResponseEntity<>(playlistTrack, HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Playlist ID %s or/and track ID %s not found", playlistID, trackId), ex);
        }
    }

    @PatchMapping("/details/playlists/remove/{playlistID}/tracks/{trackId}")
    @ApiOperation(value = "Remove a track from a playlist")
    public ResponseEntity<PlaylistWithTracks> removeTrackFromPlaylist(@PathVariable Long playlistID, @PathVariable Long trackId) {
        try {
            if (!playlistService.findById(playlistID).isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            if (!trackService.findSimpleTrackById(trackId).isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            PlaylistWithTracks playlistTrack = playlistService.findById(playlistID).get();
            Set<SimpleTrack> trackSet = playlistTrack.getTracks();
            trackSet.remove(trackService.findSimpleTrackById(trackId).get());
            playlistTrack.setTracks(trackSet);
            playlistService.deleteTrackFromPlaylist(playlistTrack);
            return new ResponseEntity<>(playlistTrack, HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Playlist ID %s or/and track ID %s not found", playlistID, trackId), ex);
        }
    }
}