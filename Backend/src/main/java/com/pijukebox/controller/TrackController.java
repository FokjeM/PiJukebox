package com.pijukebox.controller;

import com.pijukebox.model.Track;
import com.pijukebox.service.TrackService;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TrackController {

    private TrackService trackService;

    public TrackController(TrackService trackService) {
        this.trackService = trackService;
    }

    @GetMapping("/tracks?amount={amount}")
    @ApiOperation(value = "Get all tracks in the application or limit results when the 'amount' parameter is specified")
    public List<Track> tracks(@PathVariable Long amount) {
        if (amount == null || amount <= 0) {
            return trackService.findMany(amount);
        }
        return trackService.findAll();
    }
}
