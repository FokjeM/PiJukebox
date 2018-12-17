package com.pijukebox.service;

import com.pijukebox.model.Genre;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GenreService {
    List<Genre> findAll();

    Genre findByName(String name);

    Genre addGenre(Genre genre);

    Genre deleteGenre(Long id);
}
