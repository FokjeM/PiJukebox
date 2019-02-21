package com.pijukebox.service.impl;

import com.pijukebox.model.simple.SimpleGenre;
import com.pijukebox.repository.IGenreRepository;
import com.pijukebox.service.IGenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SuppressWarnings("OptionalGetWithoutIsPresent")
@Service
@Transactional
public class GenreServiceImpl implements IGenreService {

    private final IGenreRepository genreRepository;

    @Autowired
    public GenreServiceImpl(IGenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public ResponseEntity<List<SimpleGenre>> findAll() {
        return new ResponseEntity<>(genreRepository.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SimpleGenre> findById(Long id) {
        return new ResponseEntity<>(genreRepository.findById(id).get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<SimpleGenre>> findGenresByNameContaining(String name) {
        if (genreRepository.findGenresByNameContaining(name).isPresent())
        {
            return new ResponseEntity<>(genreRepository.findGenresByNameContaining(name).get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }

    @Override
    public ResponseEntity<SimpleGenre> addSimpleGenre(SimpleGenre simpleGenre) {
        return new ResponseEntity<>(genreRepository.saveAndFlush(simpleGenre), HttpStatus.OK);
    }
}
