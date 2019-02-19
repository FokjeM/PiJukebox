package com.pijukebox.controller;

import com.pijukebox.model.artist.Artist;
import com.pijukebox.model.simple.SimpleArtist;
import com.pijukebox.service.IArtistService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class ArtistController {

    private final IArtistService artistService;

    @Autowired
    public ArtistController(IArtistService artistService) {
        this.artistService = artistService;
    }

    /**
     * Get artists by artist name
     * <p>
     * Without relations
     *
     * @param name Name of the artist
     * @return Zero or more artists
     */
    @GetMapping("/simple/artists")
    @ApiOperation(value = "Get all information pertaining artists (without relations)", notes = "Filter the returned items using the name parameter")
    public ResponseEntity<List<SimpleArtist>> getSimpleAlbums(@RequestParam(name = "name", required = false) String name) {
        if (name != null && !name.isEmpty()) {
            return new ResponseEntity<>(artistService.findSimpleArtistsByNameContaining(name).getBody(), HttpStatus.OK);
        }
        return new ResponseEntity<>(artistService.findAllSimpleArtists().getBody(), HttpStatus.OK);
    }

    /**
     * Get artist by artist ID
     * <p>
     * Without relations
     *
     * @param id ID of the artist
     * @return Zero or one artist
     */
    @GetMapping("/simple/artists/{id}")
    @ApiOperation(value = "Get all information pertaining to a certain artist (without relations) by its ID")
    public ResponseEntity<SimpleArtist> artistDetails(@PathVariable Long id) {
        return new ResponseEntity<>(artistService.findSimpleArtistById(id).getBody(), HttpStatus.OK);
    }

    /**
     * Get artists by artist name
     * <p>
     * With relations
     *
     * @param name Name of the artist
     * @return Zero or more artists
     */
    @GetMapping("/extended/artists")
    @ApiOperation(value = "Get all information pertaining to an album (with relations)")
    public ResponseEntity<List<Artist>> getExtendedAlbums(@RequestParam(name = "name", required = false) String name) {
        if (name != null && !name.isEmpty()) {
            return new ResponseEntity<>(artistService.findExtendedArtistsByNameContaining(name).getBody(), HttpStatus.OK);
        }
        return new ResponseEntity<>(artistService.findAllExtendedArtists().getBody(), HttpStatus.OK);
    }

    /**
     * Get artist by artist ID
     * <p>
     * With relations
     *
     * @param id ID of the artist
     * @return Zero or one artist
     */
    @GetMapping("/extended/artists/{id}")
    @ApiOperation(value = "Get all information pertaining to an album (with relations) by its ID")
    public ResponseEntity<Artist> getExtendedAlbum(@PathVariable Long id) {
        return new ResponseEntity<>(artistService.findExtendedArtistById(id).getBody(), HttpStatus.OK);
    }
}
