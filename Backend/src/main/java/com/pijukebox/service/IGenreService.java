package com.pijukebox.service;

import com.pijukebox.model.Genre;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IGenreService {
    List<Genre> findAll();

    Genre findByName(String name);
}
