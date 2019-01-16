package com.pijukebox.controller;

import com.pijukebox.model.album.Album;
import com.pijukebox.model.album.AlbumTrack;
import com.pijukebox.model.artist.ArtistAlbum;
import com.pijukebox.model.genre.GenreAlbum;
import com.pijukebox.model.simple.SimpleAlbum;
import com.pijukebox.model.simple.SimpleTrack;
import com.pijukebox.service.IAlbumService;
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
public class AlbumController {

    private final IAlbumService albumService;

    @Autowired
    public AlbumController(IAlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping("/simple/albums")
    @ApiOperation(value = "Get all information pertaining to an album (without relations)")
    public ResponseEntity<List<SimpleAlbum>> getSimpleAlbums(@RequestParam(name = "name", required = false) String name) {
        try {
            if (name != null && !name.isEmpty()) {
                if (!albumService.findSimpleAlbumsByNameContaining(name).isPresent()) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<>(albumService.findSimpleAlbumsByNameContaining(name).get(), HttpStatus.OK);
            }
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Albums with name %s Not Found", name), ex);
        }
        return new ResponseEntity<>(albumService.findAllSimpleAlbums(), HttpStatus.OK);
    }

    @GetMapping("/albums/byGenre")
    public ResponseEntity<List<GenreAlbum>> getAlbumsByGenreName(@RequestParam(name = "name") String name) {
        try {

            if (name != null && !name.isEmpty()) {
                if (!albumService.findSimpleAlbumsByGenreName(name).isPresent()) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<>(albumService.findSimpleAlbumsByGenreName(name).get(), HttpStatus.OK);
            }
            return null;
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Albums with genre name %s Not Found", name), ex);
        }
    }

    @GetMapping("/albums/byArtist")
    public ResponseEntity<List<ArtistAlbum>> getAlbumsByArtistName(@RequestParam(name = "name") String name) {
        try {

            if (name != null && !name.isEmpty()) {
                if (!albumService.findSimpleAlbumsByArtistName(name).isPresent()) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<>(albumService.findSimpleAlbumsByArtistName(name).get(), HttpStatus.OK);
            }
            return null;
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Albums with artist name %s Not Found", name), ex);
        }
    }

    @GetMapping("/simple/albums/{id}")
    @ApiOperation(value = "Get all information pertaining to an album")
    public ResponseEntity<SimpleAlbum> getSimpleAlbum(@PathVariable Long id) {
        try {
            if (!albumService.findSimpleAlbumById(id).isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(albumService.findSimpleAlbumById(id).get(), HttpStatus.OK);
            }
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Album with ID %s Not Found", id), ex);
        }
    }

    @GetMapping("/extended/albums")
    @ApiOperation(value = "Get all information pertaining to an album (with relations)")
    public ResponseEntity<List<Album>> getExtendedAlbums(@RequestParam(name = "name", required = false) String name) {
        try {
            if (name != null && !name.isEmpty()) {
                if (!albumService.findAlbumsByNameContaining(name).isPresent()) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<>(albumService.findAlbumsByNameContaining(name).get(), HttpStatus.OK);
            }
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Album with name %s Not Found", name), ex);
        }
        return new ResponseEntity<>(albumService.findAllExtendedAlbums(), HttpStatus.OK);
    }

    @GetMapping("/extended/albums/{id}")
    @ApiOperation(value = "Get all information pertaining to an album")
    public ResponseEntity<Album> getExtendedAlbum(@PathVariable Long id) {
        try {
            if (!albumService.findExtendedAlbumById(id).isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(albumService.findExtendedAlbumById(id).get(), HttpStatus.OK);
            }
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Album with ID %s Not Found", id), ex);
        }
    }

    @PostMapping("/extended/albums/{id}/tracks/{trackId}")
    public ResponseEntity<AlbumTrack> addTrackToAlbum(@PathVariable Long id, @PathVariable Long trackId) {

        try {
            if (!albumService.findAlbumTrackById(id).isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            if (!albumService.findTrackById(trackId).isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            AlbumTrack albumWithTracks = albumService.findAlbumTrackById(id).get();
            SimpleTrack track = albumService.findTrackById(trackId).get();

            boolean trackExistsInAlbum = false;
            for (SimpleTrack albumTrack : albumWithTracks.getTracks()) {
                if (albumTrack.getId().equals(trackId)) {
                    trackExistsInAlbum = true;
                }
            }
            if (!trackExistsInAlbum) {
                albumWithTracks.getTracks().add(track);
                return new ResponseEntity<>(albumService.addTrackToAlbum(albumWithTracks), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Track with ID {id} Not Found", ex);
        }
    }
}
