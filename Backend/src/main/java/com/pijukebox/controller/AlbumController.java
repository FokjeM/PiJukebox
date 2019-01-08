package com.pijukebox.controller;

import com.pijukebox.model.Album;
import com.pijukebox.service.IAlbumService;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<Album> albumDetails() {
        return albumService.findAll();
    }

    @GetMapping("/albums/{albumId}")
    @ApiOperation(value = "Get all information pertaining to an album")
//    @PreAuthorize("hasRole('ROLE_USER')")
    public Album albumDetails(@PathVariable Long albumId) {
        return albumService.findById(albumId);
    }
}
