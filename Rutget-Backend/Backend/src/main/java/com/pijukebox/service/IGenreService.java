package com.pijukebox.service;

import com.pijukebox.model.genre.Genre;

import java.util.List;
import java.util.Optional;

public interface IGenreService {
    List<Genre> findAll();

    Optional<Genre> findById(Long id);

    Optional<List<Genre>> findGenresByNameContaining(String name);
}
