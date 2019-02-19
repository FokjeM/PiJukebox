package com.pijukebox.controller;

import com.pijukebox.model.album.Album;
import com.pijukebox.model.album.AlbumWithArtists;
import com.pijukebox.model.album.AlbumWithGenres;
import com.pijukebox.model.album.AlbumWithTracks;
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
            return new ResponseEntity<>(albumService.findSimpleAlbumsByNameContaining(name).getBody(), HttpStatus.OK);
        }
        return new ResponseEntity<>(albumService.findAllSimpleAlbums().getBody(), HttpStatus.OK);
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
        return new ResponseEntity<>(albumService.findSimpleAlbumById(id).getBody(), HttpStatus.OK);
    }

    /**
     * Get albums by album, genre, artist or a track name
     * <p>
     * With relations
     *
     * @param name     name of the album when 'searchBy' is not set, otherwise name of the genre, artist or track
     * @param searchBy find an album by genre, artist or track
     * @return Zero or more album
     */
    @GetMapping("/extended/albums")
    @ApiOperation(value = "Get all information pertaining to an album (with relations)")
    public ResponseEntity<?> getExtendedAlbums(@RequestParam(name = "name", required = false) String name, @RequestParam(name = "searchBy", required = false) String searchBy) {
        if (name != null && !name.isEmpty()) {
            if (searchBy != null && !searchBy.isEmpty()) {
                switch (searchBy.toLowerCase()) {
                    case "genre":
                        return new ResponseEntity<>(albumService.findAlbumWithGenresByNameContaining(name).getBody(), HttpStatus.OK);
                    case "artist":
                        return new ResponseEntity<>(albumService.findAlbumWithArtistsByNameContaining(name).getBody(), HttpStatus.OK);
                    case "track":
                        return new ResponseEntity<>(albumService.findAlbumWithTracksByNameContaining(name).getBody(), HttpStatus.OK);
                    default:
                        return new ResponseEntity<>("No valid search value. Use 'genre', 'artist' or 'track'", HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>(albumService.findAlbumsByNameContaining(name).getBody(), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(albumService.findAllExtendedAlbums().getBody(), HttpStatus.OK);
        }
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
        return albumService.findExtendedAlbumById(id);
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
        AlbumWithTracks album = albumService.findTrackByAlbumId(albumId).getBody();
        boolean trackExistsInAlbum = false;
        for (SimpleTrack albumTrack : album.getTracks()) {
            if (albumTrack.getId().equals(trackId)) {
                trackExistsInAlbum = true;
            }
        }
        if (!trackExistsInAlbum) {
            album.getTracks().add(albumService.findTrackById(trackId).getBody());
            return new ResponseEntity<>(albumService.addTrackToAlbum(album).getBody(), HttpStatus.CREATED);
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
        AlbumWithArtists album = albumService.findArtistByAlbumId(albumId).getBody();
        boolean ArtistExistsInAlbum = false;
        for (SimpleArtist artistAlbum : album.getArtists()) {
            if (artistAlbum.getId().equals(artistId)) {
                ArtistExistsInAlbum = true;
            }
        }
        if (!ArtistExistsInAlbum) {
            album.getArtists().add(albumService.findArtistById(artistId).getBody());
            return new ResponseEntity<>(albumService.addArtistToAlbum(album).getBody(), HttpStatus.CREATED);
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
        AlbumWithGenres album = albumService.findGenreByAlbumId(albumId).getBody();
        boolean ArtistExistsInAlbum = false;
        for (SimpleGenre simpleGenre : album.getGenres()) {
            if (simpleGenre.getId().equals(genreId)) {
                ArtistExistsInAlbum = true;
            }
        }
        if (!ArtistExistsInAlbum) {
            album.getGenres().add(albumService.findGenreById(genreId).getBody());
            return new ResponseEntity<>(albumService.addGenreToAlbum(album).getBody(), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
