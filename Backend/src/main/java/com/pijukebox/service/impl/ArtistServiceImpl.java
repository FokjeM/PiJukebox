package com.pijukebox.service.impl;

import com.pijukebox.model.artist.Artist;
import com.pijukebox.model.simple.SimpleArtist;
import com.pijukebox.repository.IArtistRepository;
import com.pijukebox.repository.ISimpleArtistRepository;
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
    private final ISimpleArtistRepository simpleArtistRepository;

    @Autowired
    public ArtistServiceImpl(IArtistRepository artistRepository, ISimpleArtistRepository simpleArtistRepository) {
        this.artistRepository = artistRepository;
        this.simpleArtistRepository = simpleArtistRepository;
    }

    @Override
    public List<Artist> findAllExtendedArtists() {
        return artistRepository.findAll();
    }

    @Override
    public Optional<Artist> findExtendedArtistById(Long id) {
        return artistRepository.findById(id);
    }

    @Override
    public Optional<List<Artist>> findExtendedArtistsByNameContaining(String name) {
        return artistRepository.findExtendedArtistsByNameContaining(name);
    }

    @Override
    public List<SimpleArtist> findAllSimpleArtists() {
        return simpleArtistRepository.findAll();
    }

    @Override
    public Optional<SimpleArtist> findSimpleArtistById(Long id) {
        return simpleArtistRepository.findById(id);
    }

    @Override
    public Optional<List<SimpleArtist>> findSimpleArtistsByNameContaining(String name) {
        return simpleArtistRepository.findSimpleArtistByNameContaining(name);
    }

    @Override
    public SimpleArtist addSimpleArtist(SimpleArtist artist) {
        return simpleArtistRepository.save(artist);
    }
}
