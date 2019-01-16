package com.pijukebox.service.impl;

import com.pijukebox.model.simple.SimpleArtist;
import com.pijukebox.repository.IArtistRepository;
import com.pijukebox.service.IArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        return artistRepository.findAll();
    }

    @Override
    public List<SimpleArtist> findMany(Long amount) {
        return artistRepository.findMany(amount);
    }

    @Override
    public SimpleArtist findByName(String name) {
        return artistRepository.findByName(name);
    }
}
