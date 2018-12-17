package com.pijukebox.controller;

import com.pijukebox.model.Artist;
import com.pijukebox.service.ArtistService;
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

    private ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping("/artist?limit={limit}")
    @ApiOperation(value = "Get all artists in the application or limit results when the 'limit' parameter is specified")
    public List<Artist> artists(@PathVariable("limit") Long limit) {
        if (limit == null || limit <= 0) {
            return artistService.findMany(limit);
        }
        return artistService.findAll();
    }

    @PostMapping("/artist")
    public Artist addArtist(Artist artist)
    {
        return artistService.addArtist(artist);
    }

    @DeleteMapping("/artist/{id}")
    public Artist deleteArtist(@PathVariable("id") Long id)
    {
        return artistService.deleteArtist(id);
    }
}

