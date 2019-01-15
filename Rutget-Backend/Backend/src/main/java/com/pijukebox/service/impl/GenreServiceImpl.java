package com.pijukebox.service.impl;

import com.pijukebox.model.genre.Genre;
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
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    @Override
    public Optional<Genre> findById(Long id) {
        return genreRepository.findById(id);
    }

    @Override
    public Optional<List<Genre>> findGenresByNameContaining(String name) {
        return genreRepository.findGenresByNameContaining(name);
    }
}
