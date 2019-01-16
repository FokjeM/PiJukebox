package com.pijukebox.service.impl;

import com.pijukebox.model.artist.ArtistTrack;
import com.pijukebox.model.simple.SimpleArtist;
import com.pijukebox.repository.IArtistRepository;
import com.pijukebox.service.IAlbumService;
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
    public ArtistServiceImpl(IArtistRepository artistRepository)
    {
        this.artistRepository = artistRepository;
    }
    @Override
    public List<SimpleArtist> findAll() {
        return artistRepository.findAll();
    }

    @Override
    public Optional<SimpleArtist> findById(Long id) {
        return artistRepository.findById(id);
    }

    @Override
    public Optional<List<SimpleArtist>> findByName(String name) {
        return artistRepository.findArtistsByNameContaining(name);
    }

    @Override
    public Optional<ArtistTrack> findTracksByArtistId(Long id) {
        return Optional.empty();
    }
}
