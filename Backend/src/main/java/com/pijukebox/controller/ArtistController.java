package com.pijukebox.controller;

import com.pijukebox.service.IArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class ArtistController {

    private final IArtistService IArtistService;

    @Autowired
    public ArtistController(IArtistService IArtistService) {
        this.IArtistService = IArtistService;
    }
}
