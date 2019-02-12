package com.pijukebox.controller;

import com.pijukebox.model.artist.Artist;
import com.pijukebox.model.simple.SimpleArtist;
import com.pijukebox.service.IArtistService;
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
        try {
            if (name != null && !name.isEmpty()) {
                if (!artistService.findSimpleArtistsByNameContaining(name).isPresent()) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<>(artistService.findSimpleArtistsByNameContaining(name).get(), HttpStatus.OK);
            }
            return new ResponseEntity<>(artistService.findAllSimpleArtists(), HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Artists with name %s Not Found", name), ex);
        }
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
        try {
            if (!artistService.findSimpleArtistById(id).isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(artistService.findSimpleArtistById(id).get(), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Artist with ID %s Not Found", id), ex);
        }
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
        try {
            if (name != null && !name.isEmpty()) {
                if (!artistService.findExtendedArtistsByNameContaining(name).isPresent()) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<>(artistService.findExtendedArtistsByNameContaining(name).get(), HttpStatus.OK);
            }
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Album with name %s Not Found", name), ex);
        }
        return new ResponseEntity<>(artistService.findAllExtendedArtists(), HttpStatus.OK);
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
        try {
            if (!artistService.findExtendedArtistById(id).isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(artistService.findExtendedArtistById(id).get(), HttpStatus.OK);
            }
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Album with ID %s Not Found", id), ex);
        }
    }
}
