package com.pijukebox.controller;

import com.pijukebox.model.Artist;
import com.pijukebox.service.IArtistService;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ArtistController {

    private IArtistService IArtistService;

    public ArtistController(IArtistService IArtistService) {
        this.IArtistService = IArtistService;
    }

    @GetMapping("/tracks?amount={amount}")
    @ApiOperation(value = "Get all artists in the application or limit results when the 'amount' parameter is specified")
    public List<Artist> artists(@PathVariable Long amount) {
        if (amount == null || amount <= 0) {
            return IArtistService.findMany(amount);
        }
        return IArtistService.findAll();
    }
}
