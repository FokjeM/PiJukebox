package com.pijukebox.controller;

import com.pijukebox.model.Album;
import com.pijukebox.service.IAlbumService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public List<Album> albumDetails() {
        return albumService.findAll();
    }

    @GetMapping("/albums/{albumId}")
    @ApiOperation(value = "Get all information pertaining to an album")
    public Optional<Album> albumDetails(@PathVariable Long albumId) {
        return albumService.findById(albumId);
    }
}
