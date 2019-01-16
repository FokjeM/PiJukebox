package com.pijukebox.controller;

import com.pijukebox.model.album.Album;
import com.pijukebox.model.simple.SimpleAlbum;
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

    @GetMapping("/albums")
    @ApiOperation(value = "Get all information pertaining to an album via its name")
    public ResponseEntity<List<SimpleAlbum>> albums(@RequestParam(name = "name", required = false) String name) {
        try {
            if (name != null && !name.isEmpty()) {
                if (!albumService.findAlbumsByNameContaining(name).isPresent()) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<>(albumService.findAlbumsByNameContaining(name).get(), HttpStatus.OK);
            }
            return new ResponseEntity<>(albumService.findAll(), HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Album with ID {id} Not Found", ex);
        }
    }

    @GetMapping("/albums/{id}")
    @ApiOperation(value = "Get all information pertaining to an album via its ID")
    public ResponseEntity<SimpleAlbum> albumDetails(@PathVariable Long id) {
        try {
            if (!albumService.findById(id).isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(albumService.findById(id).get(), HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Album with ID {id} Not Found", ex);
        }
    }
}
