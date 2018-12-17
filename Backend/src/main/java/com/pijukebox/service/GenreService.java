package com.pijukebox.service;

import com.pijukebox.model.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> findAll();

    Genre findByName(String name);
}
