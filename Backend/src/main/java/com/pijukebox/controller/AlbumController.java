package com.pijukebox.controller;

import com.pijukebox.model.Album;
import com.pijukebox.service.IAlbumService;
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
public class AlbumController {

    private final IAlbumService albumService;

    @Autowired
    public AlbumController(IAlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping("/albums")
    @ApiOperation(value = "Get all albums")
    public List<Album> albums() {
        try {
            return albumService.findAll();
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No albums found", ex);
        }
    }

    @GetMapping("/albums/{id}")
    @ApiOperation(value = "Get all information pertaining to an album")
    public Optional<Album> albumDetails(@PathVariable Long id) {
        try {
            if (!albumService.findById(id).isPresent()) {
                return Optional.empty();
            }
            return Optional.of(albumService.findById(id).get());
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Album with ID {id} Not Found", ex);
        }
    }
}
