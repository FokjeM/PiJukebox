package com.pijukebox.controller;

import com.pijukebox.model.genre.Genre;
import com.pijukebox.model.simple.SimpleGenre;
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

    @GetMapping("/simple/genres")
    @ApiOperation(value = "Get all information pertaining to an genre via its name")
    public ResponseEntity<List<SimpleGenre>> simpleGerne(@RequestParam(name = "name", required = false) String name) {
        try {
            if (name != null && !name.isEmpty()) {
                if (!genreService.findGenresByNameContaining(name).isPresent()) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<>(genreService.findGenresByNameContaining(name).get(), HttpStatus.OK);
            }
            return new ResponseEntity<>(genreService.findAll(), HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Genre with name %s Not Found", name), ex);
        }
    }

    @GetMapping("/simple/genres/{id}")
    @ApiOperation(value = "Get all information pertaining to an genre via its ID")
    public ResponseEntity<SimpleGenre> simpleGenreById(@PathVariable Long id) {
        try {
            if (!genreService.findById(id).isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(genreService.findById(id).get(), HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Genre with ID %s Not Found", id), ex);
        }
    }

    @GetMapping("/extended/genres")
    public ResponseEntity<List<Genre>> extendedGenre(@RequestParam(name = "name", required = false) String name)
    {
        try{
            if (name != null && !name.isEmpty()) {
                if (!genreService.findGenreDetailsByName(name).isPresent()) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<>(genreService.findGenreDetailsByName(name).get(), HttpStatus.OK);
            }
            return new ResponseEntity<>(genreService.findAllGerneDetails(), HttpStatus.OK);
        }catch(Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Genre with Name %s Not Found", name), ex);
        }
    }

    @GetMapping("/extended/genres/{id}")
    public ResponseEntity<Genre> extendedGenreById(@PathVariable Long id)
    {
        try{
            if (!genreService.findGenreDetailsById(id).isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(genreService.findGenreDetailsById(id).get(), HttpStatus.OK);
        }catch(Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Genre with ID %s Not Found", id), ex);
        }
    }
}
