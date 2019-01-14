package com.pijukebox.controller;

import com.pijukebox.model.Album;
import com.pijukebox.model.Genre;
import com.pijukebox.model.SimpleAlbum;
import com.pijukebox.service.IAlbumService;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AlbumController {

    private IAlbumService albumService;

    @Autowired
    public AlbumController(IAlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping("/albums")
    @ApiOperation(value = "Get all albums")
//    @PreAuthorize("hasRole('ROLE_USER')")
    public List<SimpleAlbum> albumDetails() {
        return albumService.findAll();
    }


    @GetMapping("/album/{albumId}")
    @ApiOperation(value = "Get all information pertaining to an album")
    public SimpleAlbum getById(@PathVariable Long albumId) {
        System.out.println(albumId);
        return albumService.findById(albumId);
    }

    @GetMapping("/albumsDetails")
    public List<Album> getAlbumDetails()
    {
        return albumService.findAlbumsDetails();
    }

//
}