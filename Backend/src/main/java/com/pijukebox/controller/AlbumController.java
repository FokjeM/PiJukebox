package com.pijukebox.controller;

import com.pijukebox.model.Album;
import com.pijukebox.service.AlbumService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AlbumController{

    private AlbumService albumService;

    @Autowired
    public AlbumController(AlbumService albumService)
    {
        this.albumService = albumService;
    }

    @GetMapping("/album")
    public List<Album> findAll() {
        return albumService.findAll();
    }

    @GetMapping("/album/{id}")
    public Album findById(@PathVariable("id") Long id) {
        return albumService.findById(id);
    }

    @PostMapping("/album")
    public Album addAlbum(@RequestBody Album album) {
        return albumService.addAlbum(album);
    }

    @DeleteMapping("/album/{id}")
    public Album deleteAlbum(@PathVariable("id") Long id) {
        return albumService.deleteAlbum(id);
    }



}
