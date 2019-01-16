package com.pijukebox.service.impl;

import com.pijukebox.model.simple.SimpleGenre;
import com.pijukebox.repository.IGenreRepository;
import com.pijukebox.service.IGenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GenreServiceImpl implements IGenreService {

    private final IGenreRepository genreRepository;

    @Autowired
    public GenreServiceImpl(IGenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public List<SimpleGenre> findAll() {
        return genreRepository.findAll();
    }

    @Override
    public Optional<SimpleGenre> findById(Long id) {
        return genreRepository.findById(id);
    }

    @Override
    public Optional<List<SimpleGenre>> findGenresByNameContaining(String name) {
        return genreRepository.findSimpleGenresByNameContaining(name);
    }
}
