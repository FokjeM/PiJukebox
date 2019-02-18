package com.pijukebox.controller;

import com.pijukebox.model.album.Album;
import com.pijukebox.model.album.AlbumWithArtists;
import com.pijukebox.model.album.AlbumWithGenres;
import com.pijukebox.model.album.AlbumWithTracks;
import com.pijukebox.model.artist.ArtistWithAlbums;
import com.pijukebox.model.genre.GenreWithAlbums;
import com.pijukebox.model.simple.SimpleAlbum;
import com.pijukebox.model.simple.SimpleArtist;
import com.pijukebox.model.simple.SimpleGenre;
import com.pijukebox.model.simple.SimpleTrack;
import com.pijukebox.service.IAlbumService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    /**
     * Get albums by album name
     * <p>
     * Without relations
     *
     * @param name Name of the album
     * @return Zero or more albums
     */
    @GetMapping("/simple/albums")
    @ApiOperation(value = "Get all pertaining to albums (without relations)", notes = "Filter the returned items using the name parameter")
    public ResponseEntity<List<SimpleAlbum>> getSimpleAlbums(@RequestParam(name = "name", required = false) String name) {
        if (name != null && !name.isEmpty()) {
            if (!albumService.findSimpleAlbumsByNameContaining(name).isPresent()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(albumService.findSimpleAlbumsByNameContaining(name).get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(albumService.findAllSimpleAlbums(), HttpStatus.OK);
    }

    /**
     * Get albums by genre name
     * <p>
     * Without relations
     *
     * @param name Genre of the album
     * @return Zero or more albums
     */
    @GetMapping("/albums/byGenre")
    @ApiOperation(value = "Get all information pertaining to an album (without relations) by genre", notes = "Filter the returned items using the name parameter")
    public ResponseEntity<List<GenreWithAlbums>> getAlbumsByGenreName(@RequestParam(name = "name") String name) {
        if (name != null && !name.isEmpty()) {
            if (!albumService.findSimpleAlbumsByGenreName(name).isPresent()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(albumService.findSimpleAlbumsByGenreName(name).get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Get albums by artist name
     * <p>
     * Without relations
     *
     * @param name Artist of the album
     * @return Zero or more albums
     */
    @GetMapping("/albums/byArtist")
    @ApiOperation(value = "Get all information pertaining to an album by artist", notes = "Filter the returned items using the name parameter")
    public ResponseEntity<List<ArtistWithAlbums>> getAlbumsByArtistName(@RequestParam(name = "name") String name) {
        if (name != null && !name.isEmpty()) {
            if (!albumService.findAlbumsByArtistName(name).isPresent()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(albumService.findAlbumsByArtistName(name).get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Get an album by album by ID
     * <p>
     * Without relations
     *
     * @param id ID of the album
     * @return Zero or one album
     */
    @GetMapping("/simple/albums/{id}")
    @ApiOperation(value = "Get all information pertaining to a certain album (without relations) by its ID")
    public ResponseEntity<SimpleAlbum> getSimpleAlbum(@PathVariable Long id) {
        if (!albumService.findSimpleAlbumById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(albumService.findSimpleAlbumById(id).get(), HttpStatus.OK);
        }
    }

    /**
     * Get albums by album name
     * <p>
     * With relations
     *
     * @param name name of the album
     * @return Zero or more album
     */
    @GetMapping("/extended/albums")
    @ApiOperation(value = "Get all information pertaining to an album (with relations)")
    public ResponseEntity<List<Album>> getExtendedAlbums(@RequestParam(name = "name", required = false) String name) {
        if (name != null && !name.isEmpty()) {
            if (!albumService.findAlbumsByNameContaining(name).isPresent()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(albumService.findAlbumsByNameContaining(name).get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(albumService.findAllExtendedAlbums(), HttpStatus.OK);
    }

    /**
     * Get an album by album by ID
     * <p>
     * With relations
     *
     * @param id ID of the album
     * @return Zero or one album
     */
    @GetMapping("/extended/albums/{id}")
    @ApiOperation(value = "Get all information pertaining to a certain album (with relations) by its ID")
    public ResponseEntity<Album> getExtendedAlbum(@PathVariable Long id) {
        if (!albumService.findExtendedAlbumById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(albumService.findExtendedAlbumById(id).get(), HttpStatus.OK);
        }
    }

    /**
     * Add a track to an existing album by track ID
     *
     * @param albumId ID of the album
     * @param trackId ID of the track
     * @return The album
     */
    @PostMapping("/extended/albums/{albumId}/tracks/{trackId}")
    @ApiOperation(value = "Add a new track to an existing album")
    public ResponseEntity<AlbumWithTracks> addTrackToAlbum(@PathVariable Long albumId, @PathVariable Long trackId) {
        if (!albumService.findTrackByAlbumId(albumId).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        if (!albumService.findTrackById(trackId).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        AlbumWithTracks album = albumService.findTrackByAlbumId(albumId).get();
        boolean trackExistsInAlbum = false;
        for (SimpleTrack albumTrack : album.getTracks()) {
            if (albumTrack.getId().equals(trackId)) {
                trackExistsInAlbum = true;
            }
        }
        if (!trackExistsInAlbum) {
            album.getTracks().add(albumService.findTrackById(trackId).get());
            return new ResponseEntity<>(albumService.addTrackToAlbum(album), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }


    /**
     * Add an artist to an existing album by artist ID
     *
     * @param albumId  ID of the album
     * @param artistId ID of the artist
     * @return The album
     */
    @PostMapping("/extended/albums/{albumId}/artists/{artistId}")
    @ApiOperation(value = "Add a new artist to an existing album")
    public ResponseEntity<AlbumWithArtists> addArtistToAlbum(@PathVariable Long albumId, @PathVariable Long artistId) {
        if (!albumService.findArtistByAlbumId(albumId).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        if (!albumService.findArtistById(artistId).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        AlbumWithArtists album = albumService.findArtistByAlbumId(albumId).get();
        boolean ArtistExistsInAlbum = false;
        for (SimpleArtist artistAlbum : album.getArtists()) {
            if (artistAlbum.getId().equals(artistId)) {
                ArtistExistsInAlbum = true;
            }
        }
        if (!ArtistExistsInAlbum) {
            album.getArtists().add(albumService.findArtistById(artistId).get());
            return new ResponseEntity<>(albumService.addArtistToAlbum(album), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    /**
     * Add a genre to an existing album by genre ID
     *
     * @param albumId ID of the album
     * @param genreId ID of the genre
     * @return The album
     */
    @PostMapping("/extended/albums/{albumId}/genres/{genreId}")
    @ApiOperation(value = "Add a new genre to an existing album")
    public ResponseEntity<AlbumWithGenres> addGenreToAlbum(@PathVariable Long albumId, @PathVariable Long genreId) {
        if (!albumService.findGenreByAlbumId(albumId).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        if (!albumService.findGenreById(genreId).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        AlbumWithGenres album = albumService.findGenreByAlbumId(albumId).get();
        boolean ArtistExistsInAlbum = false;
        for (SimpleGenre simpleGenre : album.getGenres()) {
            if (simpleGenre.getId().equals(genreId)) {
                ArtistExistsInAlbum = true;
            }
        }
        if (!ArtistExistsInAlbum) {
            album.getGenres().add(albumService.findGenreById(genreId).get());
            return new ResponseEntity<>(albumService.addGenreToAlbum(album), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

    }
}
