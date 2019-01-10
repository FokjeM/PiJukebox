package com.pijukebox.controller;

import com.pijukebox.service.IArtistService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/v1/artists")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Transactional
public class ArtistController {

    private IArtistService IArtistService;

    public ArtistController(IArtistService IArtistService) {
        this.IArtistService = IArtistService;
    }

}
