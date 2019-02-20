package com.pijukebox.controller;

import com.pijukebox.model.simple.SimpleGenre;
import com.pijukebox.service.IGenreService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class GenreController {

    private final IGenreService genreService;

    @Autowired
    public GenreController(IGenreService genreService) {
        this.genreService = genreService;
    }

    /**
     * Get genres by genre name
     *
     * @param name Name of the genre
     * @return Zero or more genres
     */
    @GetMapping("/genres")
    @ApiOperation(value = "Get all information pertaining to a genre (without relations)", notes = "Filter the returned items using the name parameter")
    public ResponseEntity<List<SimpleGenre>> genres(@RequestParam(name = "name", required = false) String name) {
        if (name != null && !name.isEmpty()) {
            return new ResponseEntity<>(genreService.findGenresByNameContaining(name).getBody(), HttpStatus.OK);
        }
        return new ResponseEntity<>(genreService.findAll().getBody(), HttpStatus.OK);
    }

    /**
     * Get genres by genre ID
     *
     * @param id ID of the genre
     * @return Zero or one genres
     */
    @GetMapping("/genres/{id}")
    @ApiOperation(value = "Get all information pertaining to a certain genre (without relations) by its ID")
    public ResponseEntity<SimpleGenre> genreDetails(@PathVariable Long id) {
        return new ResponseEntity<>(genreService.findById(id).getBody(), HttpStatus.OK);
    }
}
