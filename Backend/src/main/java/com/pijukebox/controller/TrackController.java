package com.pijukebox.controller;

import com.pijukebox.model.artist.ArtistWithTracks;
import com.pijukebox.model.genre.GenreWithTracks;
import com.pijukebox.model.simple.SimpleTrack;
import com.pijukebox.model.track.Track;
import com.pijukebox.service.ITrackService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    /**
     * Get tracks by track name
     * <p>
     * Without relations
     *
     * @param name Name of the track
     * @return Zero or more tracks
     */
    @GetMapping("/simple/tracks")
    @ApiOperation(value = "Get all pertaining to tracks (without relations)", notes = "Filter the returned items using the name parameter")
    public ResponseEntity<List<SimpleTrack>> simpleTracks(@RequestParam(name = "name", required = false) String name) {
        if (name != null && !name.isEmpty()) {
            return new ResponseEntity<>(trackService.findAllSimpleTrackByName(name).getBody(), HttpStatus.OK);
        }
        return new ResponseEntity<>(trackService.findAllSimpleTrack().getBody(), HttpStatus.OK);
    }

    /**
     * Get tracks by track, genre or artist name
     * <p>
     * With relations
     *
     * @param name     name of the track when 'searchBy' is not set, otherwise name of the genre or artist
     * @param searchBy find a track by genre or artist
     * @return Zero or more tracks
     */
    @GetMapping("/extended/tracks")
    @ApiOperation(value = "Get all information pertaining to tracks (with relations)", notes = "Filter the returned items using the name parameter")
    public ResponseEntity<?> getExtendedTracks(@RequestParam(name = "name", required = false) String name, @RequestParam(name = "searchBy", required = false) String searchBy) {
        if (name != null && !name.isEmpty()) {
            if (searchBy != null && !searchBy.isEmpty()) {
                switch (searchBy.toLowerCase()) {
                    case "genre":
                        return new ResponseEntity<>(trackService.findAllTracksByGenreName(name).getBody(), HttpStatus.OK);
                    case "artist":
                        return new ResponseEntity<>(trackService.findAllTracksByArtistName(name).getBody(), HttpStatus.OK);
                    default:
                        return new ResponseEntity<>("No valid search value. Use 'genre' or 'artist'", HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>(trackService.findAllTracksByName(name).getBody(), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(trackService.findAllTracksWithDetails().getBody(), HttpStatus.OK);
        }
    }

    /**
     * Get track by track ID
     * <p>
     * With relations
     *
     * @param id ID of the track
     * @return Zero or one track
     */
    @GetMapping("/extended/tracks/{id}")
    @ApiOperation(value = "Get all information pertaining to a certain track (with relations) by its ID")
    public ResponseEntity<Track> trackDetails(@PathVariable Long id) {
        return new ResponseEntity<>(trackService.findTrackDetailsById(id).getBody(), HttpStatus.OK);
    }

    /**
     * Get track by track ID
     * <p>
     * Without relations
     *
     * @param id ID of the track
     * @return Zero or one track
     */
    @GetMapping("/simple/tracks/{id}")
    @ApiOperation(value = "Get all information pertaining to a certain track (without relations) by its ID")
    public ResponseEntity<SimpleTrack> simpleTrack(@PathVariable Long id) {
        return new ResponseEntity<>(trackService.findSimpleTrackById(id).getBody(), HttpStatus.OK);
    }

    /**
     * Add a new track to the database
     *
     * @param simpleTrack A {@link SimpleTrack SimpleTrack} object
     * @return The newly added track
     */
    @PostMapping("/simple/tracks")
    @ApiOperation(value = "Add a new track")
    public ResponseEntity<SimpleTrack> addSimpleTrack(@RequestBody SimpleTrack simpleTrack) {
        return new ResponseEntity<>(trackService.addSimpleTrack(simpleTrack).getBody(), HttpStatus.CREATED);
    }

    /**
     * Update an existing track to the database
     *
     * @param simpleTrack A {@link SimpleTrack SimpleTrack} object
     * @return The updated track
     */
    @PatchMapping("/simple/tracks")
    @ApiOperation(value = "Update an existing a track")
    public ResponseEntity<SimpleTrack> updateSimpleTrack(@RequestBody SimpleTrack simpleTrack) {
        if (simpleTrack.getId() != null && !simpleTrack.getId().toString().isEmpty()) {
            SimpleTrack simpleTrack1 = trackService.findSimpleTrackById(simpleTrack.getId()).getBody();
            simpleTrack1.setName(simpleTrack.getName());
            simpleTrack1.setDescription(simpleTrack.getDescription());
            simpleTrack1.setFilename(simpleTrack.getFilename());
            return new ResponseEntity<>(trackService.addSimpleTrack(simpleTrack1).getBody(), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Update an existing track to the database
     *
     * @param id      The track ID
     * @param genreId The genre ID
     * @return The updated track
     */
    @PatchMapping("/extended/tracks/{id}/genres/{genreId}")
    @ApiOperation(value = "Add a new genre to an existing track")
    public ResponseEntity<GenreWithTracks> addGenreToTrack(@PathVariable Long id, @PathVariable Long genreId) {
        GenreWithTracks genreWithTracks = trackService.findTrackByGenreId(genreId).getBody();
        SimpleTrack simpleTrack = trackService.findSimpleTrackById(id).getBody();
        genreWithTracks.getTracks().add(simpleTrack);
        return new ResponseEntity<>(trackService.addGenreToTrack(genreWithTracks).getBody(), HttpStatus.CREATED);
    }

    @PatchMapping("/extended/tracks/{id}/artists/{artistId}")
    @ApiOperation(value = "Add a new artist to an existing track")
    public ResponseEntity<ArtistWithTracks> addArtistToTrack(@PathVariable Long id, @PathVariable Long artistId) {
        ArtistWithTracks genreTrack = trackService.findTrackByArtistId(artistId).getBody();
        SimpleTrack simpleTrack = trackService.findSimpleTrackById(id).getBody();
        genreTrack.getTracks().add(simpleTrack);
        return new ResponseEntity<>(trackService.addArtistToTrack(genreTrack).getBody(), HttpStatus.CREATED);
    }

    @GetMapping("/tracks/search/{searchTerm}")
    @ApiOperation(value = "Search Tracks")
    public ResponseEntity<List<Track>> searchTracks(@PathVariable String searchTerm) {
        if (searchTerm != null && !searchTerm.isEmpty()) {
            return new ResponseEntity<>(trackService.findAllTracksByName(searchTerm).getBody(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
