package com.pijukebox.controller;

import com.pijukebox.model.Track;
import com.pijukebox.service.ITrackService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return trackService.findAll();
    }

    @GetMapping("/tracks/{id}")
    @ApiOperation(value = "Get all information pertaining to a track")
    public Track trackDetails(@PathVariable Long id) {
        if (!trackService.findById(id).isPresent()) {
            return null;
        }
        return trackService.findById(id).get();
    }
}
