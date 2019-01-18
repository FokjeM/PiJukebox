package com.pijukebox.controller;

import com.pijukebox.model.track.Track;
import com.pijukebox.service.ITrackService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/v1/player")
public class PlayerController {

    private final ITrackService trackService;

    @Autowired
    public PlayerController(ITrackService trackService) {
        this.trackService = trackService;
    }

    @PostMapping(value = "/current", produces = "application/json")
    @ApiOperation(value = "Returns current track.")
    public ResponseEntity<Track> current() {
        try {
            if (!trackService.findTrackDetailsById(1L).isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(trackService.findTrackDetailsById(1L).get(), HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No current track", ex);
        }
    }

    @PostMapping(value = "/play", produces = "application/json")
    @ApiOperation(value = "Play/pause the current song.")
    public String play() {
        return "play";
    }

    @PostMapping(value = "/next", produces = "application/json")
    @ApiOperation(value = "Play the next song in the queue.")
    public String next() {
        return "next";
    }

    @PostMapping(value = "/previous", produces = "application/json")
    @ApiOperation(value = "Play the previous song in the queue.")
    public String previous() {
        return "previous";
    }

    @PostMapping(value = "/shuffle", produces = "application/json")
    @ApiOperation(value = "Toggle shuffle options.")
    public String shuffle() {
        return "shuffle";
    }

    @PostMapping(value = "/repeat", produces = "application/json")
    @ApiOperation(value = "Toggle repeat.")
    public String repeat() {
        return "repeat";
    }

    @GetMapping(value = "/status", produces = "application/json")
    @ApiOperation(value = "Get status")
    public String status() {
        return "{\"playPauseState\": false, \"repeatState\": 2, \"volumeLevel\": 5}";
    }
}
