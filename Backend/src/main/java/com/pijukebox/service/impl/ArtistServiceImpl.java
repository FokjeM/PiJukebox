package com.pijukebox.service.impl;

import com.pijukebox.model.simple.SimpleArtist;
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
    public List<SimpleArtist> findAll() {
        return null;
    }

    @Override
    public List<SimpleArtist> findMany(Long amount) {
        return null;
    }

    @Override
    public SimpleArtist findByName(String name) {
        return null;
    }

    @Override
    public Optional<SimpleArtist> findById(Long id) {
        return Optional.empty();
    }
}
