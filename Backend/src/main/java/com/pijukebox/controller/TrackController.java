package com.pijukebox.controller;

import com.pijukebox.model.SqlElement;
import com.pijukebox.model.artist.ArtistTrack;
import com.pijukebox.model.genre.GenreTrack;
import com.pijukebox.model.simple.SimpleTrack;
import com.pijukebox.model.track.Track;
import com.pijukebox.service.ITrackService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public List<SimpleTrack> simpleTracks(@RequestParam(name="name", required = false) String name) {
        try {
            if(name != null && !name.isEmpty())
            {
                if(!trackService.findAllSimpleTrackByName(name).isPresent())
                {
                    return null;
                }
                return trackService.findAllSimpleTrackByName(name).get();
            }
            if (!trackService.findAllSimpleTrack().isPresent()) {
                return null;
            }
            return trackService.findAllSimpleTrack().get();
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No tracks found", ex);
        }
    }

    @GetMapping("/details/tracks")
    @ApiOperation(value = "Get all tracks in the application")
    public List<Track> detailTracks(@RequestParam(name="name", required = false) String name) {
        try {if(name != null && !name.isEmpty())
        {
            if(!trackService.findAllTrackByName(name).isPresent())
            {
                return null;
            }
            return trackService.findAllTrackByName(name).get();
        }
            if (!trackService.findAllSimpleTrack().isPresent()) {
                return null;
            }
            return trackService.findAllTracksWithDetails();
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No tracks found", ex);
        }
    }


    @GetMapping("/details/tracks/{id}")
    @ApiOperation(value = "Get all information pertaining to a track")
    public Track trackDetails(@PathVariable Long id) {
        try {
            if (!trackService.findTrackDetailsById(id).isPresent()) {
                return null;
            }
            return trackService.findTrackDetailsById(id).get();
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Track with ID {id} Not Found", ex);
        }
    }

    @GetMapping("/simple/tracks/{id}")
    @ApiOperation(value = "Get all information pertaining to a track")
    public SimpleTrack simpleTrack(@PathVariable Long id) {
        try {
            if (!trackService.findTrackDetailsById(id).isPresent()) {
                return null;
            }
            return trackService.findSimpleTrackById(id).get();
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Track with ID {id} Not Found", ex);
        }
    }

    @GetMapping("/tracks/byGenre")
    public List<GenreTrack> getTracksByGenreName(@RequestParam(name="name") String name)
    {
        try{

            if(name != null && !name.isEmpty()) {
                if (!trackService.findAllTracksByGenreName(name).isPresent()) {
                    return null;
                }
                return trackService.findAllTracksByGenreName(name).get();
            }
            return null;
        }catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Track with ID {id} Not Found", ex);
        }
    }

    @GetMapping("/tracks/byArtist")
    public List<ArtistTrack> getTracksByArtistName(@RequestParam(name="name") String name)
    {
        try{

            if(name != null && !name.isEmpty()) {
                if (!trackService.findAllTracksByArtistName(name).isPresent()) {
                    return null;
                }
                return trackService.findAllTracksByArtistName(name).get();
            }
            return null;
        }catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Track with ID {id} Not Found", ex);
        }
    }

    @PostMapping("/simple/tracks")
    public SimpleTrack addSimpleTrack(@RequestBody SimpleTrack simpleTrack)
    {
        try{
            return trackService.addSimpleTrack(simpleTrack);
        }catch (Exception ex) {
            ex.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Track with ID {id} Not Found", ex);
        }
    }
    @PatchMapping("/simple/tracks")
    public SimpleTrack updateSimpleTrack(@RequestBody SimpleTrack simpleTrack)
    {
        try{
            if(simpleTrack.getId()!= null && !simpleTrack.getId().toString().isEmpty())
            {
                if (!trackService.findTrackDetailsById(simpleTrack.getId()).isPresent()) {
                    return null;
                }
                SimpleTrack simpleTrack1 = trackService.findSimpleTrackById(simpleTrack.getId()).get();
                simpleTrack1.setName(simpleTrack.getName());
                simpleTrack1.setDescription(simpleTrack.getDescription());
                simpleTrack1.setFilename(simpleTrack.getFilename());
                return trackService.addSimpleTrack(simpleTrack1);
            }
            return null;
        }catch (Exception ex) {
            ex.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Track with ID {id} Not Found", ex);
        }
    }
}
