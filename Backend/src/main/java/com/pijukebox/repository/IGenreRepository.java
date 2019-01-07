package com.pijukebox.repository;

import com.pijukebox.model.Artist;
import com.pijukebox.model.Genre;

import java.util.List;

public interface IGenreRepository {
    List<Genre> findAll();

    Genre getById(Long id);
}
