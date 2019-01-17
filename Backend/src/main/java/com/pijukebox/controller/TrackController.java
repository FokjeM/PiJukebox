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

    @GetMapping("/simple/tracks")
    @ApiOperation(value = "Get all tracks in the application")
    public ResponseEntity<List<SimpleTrack>> simpleTracks(@RequestParam(name = "name", required = false) String name) {
        try {
            if (name != null && !name.isEmpty()) {
                if (!trackService.findAllSimpleTrackByName(name).isPresent()) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<>(trackService.findAllSimpleTrackByName(name).get(), HttpStatus.OK);
            }
            if (!trackService.findAllSimpleTrack().isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(trackService.findAllSimpleTrack().get(), HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No tracks name %s found", name), ex);
        }
    }

    @GetMapping("/extended/tracks")
    @ApiOperation(value = "Get all tracks in the application")
    public ResponseEntity<List<Track>> detailTracks(@RequestParam(name = "name", required = false) String name) {
        try {
            if (name != null && !name.isEmpty()) {
                if (!trackService.findAllTrackByName(name).isPresent()) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<>(trackService.findAllTrackByName(name).get(), HttpStatus.OK);
            }
            if (!trackService.findAllSimpleTrack().isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(trackService.findAllTracksWithDetails(), HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No tracks name %s found", name), ex);
        }
    }


    @GetMapping("/extended/tracks/{id}")
    @ApiOperation(value = "Get all information pertaining to a track")
    public ResponseEntity<Track> trackDetails(@PathVariable Long id) {
        try {
            if (!trackService.findTrackDetailsById(id).isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(trackService.findTrackDetailsById(id).get(), HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No track with ID %s found", id), ex);
        }
    }

    @GetMapping("/simple/tracks/{id}")
    @ApiOperation(value = "Get all information pertaining to a track")
    public ResponseEntity<SimpleTrack> simpleTrack(@PathVariable Long id) {
        try {
            if (!trackService.findTrackDetailsById(id).isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(trackService.findSimpleTrackById(id).get(), HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No track with ID %s found", id), ex);
        }
    }

    @GetMapping("/tracks/byGenre")
    public ResponseEntity<List<GenreWithTracks>> getTracksByGenreName(@RequestParam(name = "name") String name) {
        try {

            if (name != null && !name.isEmpty()) {
                if (!trackService.findAllTracksByGenreName(name).isPresent()) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<>(trackService.findAllTracksByGenreName(name).get(), HttpStatus.OK);
            }
            return null;
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No genre with name %s found", name), ex);
        }
    }

    @GetMapping("/tracks/byArtist")
    public ResponseEntity<List<ArtistWithTracks>> getTracksByArtistName(@RequestParam(name = "name") String name) {
        try {

            if (name != null && !name.isEmpty()) {
                if (!trackService.findAllTracksByArtistName(name).isPresent()) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<>(trackService.findAllTracksByArtistName(name).get(), HttpStatus.OK);
            }
            return null;
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No artist with name %s found", name), ex);
        }
    }

    @PostMapping("/simple/tracks")
    public ResponseEntity<SimpleTrack> addSimpleTrack(@RequestBody SimpleTrack simpleTrack) {
        try {
            return new ResponseEntity<>(trackService.addSimpleTrack(simpleTrack), HttpStatus.CREATED);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Something went wrong!", ex);
        }
    }

    @PatchMapping("/simple/tracks")
    public ResponseEntity<SimpleTrack> updateSimpleTrack(@RequestBody SimpleTrack simpleTrack) {
        try {
            if (simpleTrack.getId() != null && !simpleTrack.getId().toString().isEmpty()) {
                if (!trackService.findTrackDetailsById(simpleTrack.getId()).isPresent()) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }

                SimpleTrack simpleTrack1 = trackService.findSimpleTrackById(simpleTrack.getId()).get();
                simpleTrack1.setName(simpleTrack.getName());
                simpleTrack1.setDescription(simpleTrack.getDescription());
                simpleTrack1.setFilename(simpleTrack.getFilename());
                return new ResponseEntity<>(trackService.addSimpleTrack(simpleTrack1), HttpStatus.CREATED);
            }
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Something went wrong!", ex);
        }
    }

    @PatchMapping("/extended/tracks/{id}/genres/{genreId}")
    public ResponseEntity<GenreWithTracks> addGenreToTrack(@PathVariable Long id, @PathVariable Long genreId) {

        try {
            if (!trackService.findSimpleTrackById(id).isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            if (!trackService.findTrackByGenreId(genreId).isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            GenreWithTracks genreWithTracks = trackService.findTrackByGenreId(genreId).get();
            SimpleTrack simpleTrack = trackService.findSimpleTrackById(id).get();
            genreWithTracks.getTracks().add(simpleTrack);
            return new ResponseEntity<>(trackService.addGenreToTrack(genreWithTracks), HttpStatus.CREATED);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No track with ID %s and genre with ID %s found", id, genreId), ex);

        }
    }

    @PatchMapping("/extended/tracks/{id}/artists/{artistId}")
    public ResponseEntity<ArtistWithTracks> addArtistToTrack(@PathVariable Long id, @PathVariable Long artistId) {

        try {
            if (!trackService.findSimpleTrackById(id).isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            if (!trackService.findTrackByArtistId(artistId).isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            ArtistWithTracks genreTrack = trackService.findTrackByArtistId(artistId).get();
            SimpleTrack simpleTrack = trackService.findSimpleTrackById(id).get();
            genreTrack.getTracks().add(simpleTrack);
            return new ResponseEntity<>(trackService.addArtistToTrack(genreTrack), HttpStatus.CREATED);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No track with ID %s and artist with ID %s found", id, artistId), ex);
        }
    }
}
