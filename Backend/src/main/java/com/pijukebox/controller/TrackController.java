package com.pijukebox.controller;

import com.pijukebox.model.Track;
import com.pijukebox.service.ITrackService;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/v1/tracks")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TrackController {

    private ITrackService trackService;

    public TrackController(ITrackService trackService) {
        this.trackService = trackService;
    }

    @GetMapping()
    @ApiOperation(value = "Get all tracks in the application or limit results when the 'amount' parameter is specified")
    public List<Track> tracks(@RequestParam Long amount) {
        if (amount == null || amount <= 0) {
            return trackService.findAll(amount);
        }
        return trackService.findAll();
    }
}
