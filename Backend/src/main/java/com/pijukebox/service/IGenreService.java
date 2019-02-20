package com.pijukebox.service;

import com.pijukebox.model.simple.SimpleGenre;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IGenreService {
    ResponseEntity<List<SimpleGenre>> findAll();

    ResponseEntity<SimpleGenre> findById(Long id);

    ResponseEntity<List<SimpleGenre>> findGenresByNameContaining(String name);

    ResponseEntity<SimpleGenre> addSimpleGenre(SimpleGenre simpleGenre);
}
