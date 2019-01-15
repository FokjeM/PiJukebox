package com.pijukebox.controller;

import com.pijukebox.model.track.Track;
import com.pijukebox.service.ITrackService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class TrackController {

    private final ITrackService trackService;

    @Autowired
    public TrackController(ITrackService trackService) {
        this.trackService = trackService;
    }

    @GetMapping("/tracks")
    @ApiOperation(value = "Get all tracks in the application")
    public List<Track> tracks() {
        try {
            return trackService.findAll();
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No tracks found", ex);
        }
    }

    @GetMapping("/tracks/{id}")
    @ApiOperation(value = "Get all information pertaining to a track")
    public Track trackDetails(@PathVariable Long id) {
        try {
            if (!trackService.findById(id).isPresent()) {
                return null;
            }
            return trackService.findById(id).get();
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Track with ID {id} Not Found", ex);
        }
    }
}
