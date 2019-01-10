package com.pijukebox.controller;

import com.pijukebox.model.Genre;
import com.pijukebox.repository.IAlbumRepository;
import com.pijukebox.repository.IGenreRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/v1/genres")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GenreController {

    private IGenreRepository iGenreRepository;
    @Autowired
    public GenreController(IGenreRepository iGenreRepository)
    {
        this.iGenreRepository = iGenreRepository;
    }

    @GetMapping("/genres")
    public List<Genre> findAll() {
        return iGenreRepository.findAll();
    }

    @GetMapping("/genre/{id}")
    public Genre getById(@PathVariable Long id) {
        return iGenreRepository.getById(id);
    }
}
