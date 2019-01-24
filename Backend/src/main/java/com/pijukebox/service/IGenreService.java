package com.pijukebox.service;

import com.pijukebox.model.simple.SimpleGenre;

import java.util.List;
import java.util.Optional;

public interface IGenreService {
    List<SimpleGenre> findAll();

    Optional<SimpleGenre> findById(Long id);

    Optional<List<SimpleGenre>> findGenresByNameContaining(String name);
}
