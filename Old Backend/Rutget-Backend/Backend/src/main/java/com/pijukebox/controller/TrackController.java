package com.pijukebox.controller;

import com.pijukebox.model.SqlElement;
import com.pijukebox.model.artist.ArtistTrack;
import com.pijukebox.model.genre.GenreTrack;
import com.pijukebox.model.simple.SimpleTrack;
import com.pijukebox.model.track.Track;
import com.pijukebox.service.IGenreService;
import com.pijukebox.service.ITrackService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<List<SimpleTrack>> simpleTracks(@RequestParam(name="name", required = false) String name) {
        try {
            if(name != null && !name.isEmpty())
            {
                if(!trackService.findAllSimpleTrackByName(name).isPresent())
                {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<>(trackService.findAllSimpleTrackByName(name).get(), HttpStatus.OK);
            }
            if (!trackService.findAllSimpleTrack().isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(trackService.findAllSimpleTrack().get(), HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No tracks found", ex);
        }
    }

    @GetMapping("/extended/tracks")
    @ApiOperation(value = "Get all tracks in the application")
    public ResponseEntity<List<Track>> detailTracks(@RequestParam(name="name", required = false) String name) {
        try {if(name != null && !name.isEmpty())
        {
            if(!trackService.findAllTrackByName(name).isPresent())
            {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(trackService.findAllTrackByName(name).get(), HttpStatus.OK);
        }
            if (!trackService.findAllSimpleTrack().isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(trackService.findAllTracksWithDetails(), HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No tracks found", ex);
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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Track with ID {id} Not Found", ex);
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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Track with ID {id} Not Found", ex);
        }
    }

    @GetMapping("/tracks/byGenre")
    public ResponseEntity<List<GenreTrack>> getTracksByGenreName(@RequestParam(name="name") String name)
    {
        try{

            if(name != null && !name.isEmpty()) {
                if (!trackService.findAllTracksByGenreName(name).isPresent()) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<>(trackService.findAllTracksByGenreName(name).get(), HttpStatus.OK);
            }
            return null;
        }catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Track with ID {id} Not Found", ex);
        }
    }

    @GetMapping("/tracks/byArtist")
    public ResponseEntity<List<ArtistTrack>> getTracksByArtistName(@RequestParam(name="name") String name)
    {
        try{

            if(name != null && !name.isEmpty()) {
                if (!trackService.findAllTracksByArtistName(name).isPresent()) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<>(trackService.findAllTracksByArtistName(name).get(), HttpStatus.OK);
            }
            return null;
        }catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Track with ID {id} Not Found", ex);
        }
    }

    @PostMapping("/simple/tracks")
    public ResponseEntity<SimpleTrack> addSimpleTrack(@RequestBody SimpleTrack simpleTrack)
    {
        try{
            return new ResponseEntity<>(trackService.addSimpleTrack(simpleTrack), HttpStatus.CREATED);
        }catch (Exception ex) {
            ex.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Track with ID {id} Not Found", ex);
        }
    }
    @PatchMapping("/simple/tracks")
    public ResponseEntity<SimpleTrack> updateSimpleTrack(@RequestBody SimpleTrack simpleTrack)
    {
        try{
            if(simpleTrack.getId()!= null && !simpleTrack.getId().toString().isEmpty())
            {
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
        }catch (Exception ex) {
            ex.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Track with ID {id} Not Found", ex);
        }
    }

    @PatchMapping("/extended/tracks/{id}/genres/{genreId}")
    public ResponseEntity<GenreTrack> addGenreToTrack(@PathVariable Long id, @PathVariable Long genreId)
    {

        try{
            if(!trackService.findSimpleTrackById(id).isPresent())
            {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            if(!trackService.findTrackByGenreId(genreId).isPresent())
            {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            GenreTrack genreTrack = trackService.findTrackByGenreId(genreId).get();
            SimpleTrack simpleTrack = trackService.findSimpleTrackById(id).get();
            genreTrack.getTracks().add(simpleTrack);
            return new ResponseEntity<>(trackService.addGenreToTrack(genreTrack), HttpStatus.CREATED);
        }catch (Exception ex) {
            ex.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Track with ID {id} Not Found", ex);
        }
    }

    @PatchMapping("/extended/tracks/{id}/artists/{artistId}")
    public ResponseEntity<ArtistTrack> addArtistToTrack(@PathVariable Long id, @PathVariable Long artistId)
    {

        try{
            if(!trackService.findSimpleTrackById(id).isPresent())
            {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            if(!trackService.findTrackByArtistId(artistId).isPresent())
            {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            ArtistTrack genreTrack = trackService.findTrackByArtistId(artistId).get();
            SimpleTrack simpleTrack = trackService.findSimpleTrackById(id).get();
            genreTrack.getTracks().add(simpleTrack);
            return new ResponseEntity<>(trackService.addArtistToTrack(genreTrack), HttpStatus.CREATED);
        }catch (Exception ex) {
            ex.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Track with ID {id} Not Found", ex);
        }
    }
}
