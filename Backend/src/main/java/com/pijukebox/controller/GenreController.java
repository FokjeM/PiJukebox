package com.pijukebox.controller;

import com.pijukebox.model.Genre;
import com.pijukebox.service.IGenreService;
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
public class GenreController {

    private final IGenreService genreService;

    @Autowired
    public GenreController(IGenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/genres")
    @ApiOperation(value = "Get all information pertaining to an genre via its name")
    public ResponseEntity<List<Genre>> genres(@RequestParam(name = "name", required = false) String name) {
        try {
            if (name != null && !name.isEmpty()) {
                if (!genreService.findGenresByNameContaining(name).isPresent()) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<>(genreService.findGenresByNameContaining(name).get(), HttpStatus.OK);
            }
            return new ResponseEntity<>(genreService.findAll(), HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Album with ID {id} Not Found", ex);
        }
    }

    @GetMapping("/genres/{id}")
    @ApiOperation(value = "Get all information pertaining to an genre via its ID")
    public ResponseEntity<Genre> genreDetails(@PathVariable Long id) {
        try {
            if (!genreService.findById(id).isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(genreService.findById(id).get(), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Genre with ID {id} Not Found", ex);
        }
    }
}
