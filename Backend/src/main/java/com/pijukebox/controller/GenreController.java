package com.pijukebox.controller;

import com.pijukebox.model.Genre;
import com.pijukebox.service.GenreService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GenreController {

    private GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService)
    {
        this.genreService = genreService;
    }
    @GetMapping("/genre")
    public List<Genre> findAll() {
        return genreService.findAll();
    }

    @GetMapping("/genre/{name}")
    public Genre findByName(@PathVariable("name") String name) {
        return genreService.findByName(name);
    }

    @PostMapping("/genre")
    public Genre addGenre(Genre genre) {
        return genreService.addGenre(genre);
    }

    @DeleteMapping("/genre/{id}")
    public Genre deleteGenre(Long id) {
        return genreService.deleteGenre(id);
    }
}
