package com.pijukebox.service.impl;

import com.pijukebox.model.genre.Genre;
import com.pijukebox.model.simple.SimpleGenre;
import com.pijukebox.repository.IGenreRepository;
import com.pijukebox.repository.ISimpleGenreRepository;
import com.pijukebox.service.IGenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GenreServiceImpl implements IGenreService {

    private final ISimpleGenreRepository simpleGenreRepository;
    private final IGenreRepository genreRepository;

    @Autowired
    public GenreServiceImpl(ISimpleGenreRepository simpleGenreRepository,
                            IGenreRepository genreRepository) {
        this.simpleGenreRepository = simpleGenreRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    public List<SimpleGenre> findAll() {
        return simpleGenreRepository.findAll();
    }

    @Override
    public Optional<SimpleGenre> findById(Long id) {
        return simpleGenreRepository.findById(id);
    }

    @Override
    public Optional<List<SimpleGenre>> findGenresByNameContaining(String name) {
        return simpleGenreRepository.findSimpleGenresByNameContaining(name);
    }

    @Override
    public Optional<Genre> findGenreDetailsById(Long id) {
        return genreRepository.findById(id);
    }

    @Override
    public List<Genre> findAllGerneDetails() {
        return genreRepository.findAll();
    }

    @Override
    public Optional<List<Genre>> findGenreDetailsByName(String name) {
        return genreRepository.findGenresByNameContaining(name);
    }
}
