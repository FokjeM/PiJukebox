package com.pijukebox.service.impl;

import com.pijukebox.model.Artist;
import com.pijukebox.repository.IArtistRepository;
import com.pijukebox.service.IArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ArtistServiceImpl implements IArtistService {

    private final IArtistRepository artistRepository;

    @Autowired
    public ArtistServiceImpl(IArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @Override
    public List<Artist> findAll() {
        return null;
    }

    @Override
    public Optional<Artist> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<List<Artist>> findGenresByNameContaining(String name) {
        return Optional.empty();
    }
}
