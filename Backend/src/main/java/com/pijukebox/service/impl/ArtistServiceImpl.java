package com.pijukebox.service.impl;

import com.pijukebox.model.artist.Artist;
import com.pijukebox.model.simple.SimpleArtist;
import com.pijukebox.repository.IArtistRepository;
import com.pijukebox.repository.ISimpleArtistRepository;
import com.pijukebox.service.IArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("OptionalGetWithoutIsPresent")
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
    public ResponseEntity<List<Artist>> findAllExtendedArtists() {
        return new ResponseEntity<>(artistRepository.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Artist> findExtendedArtistById(Long id) {
        return new ResponseEntity<>(artistRepository.findById(id).get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Artist>> findExtendedArtistsByNameContaining(String name) {
        return new ResponseEntity<>(artistRepository.findExtendedArtistsByNameContaining(name).get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<SimpleArtist>> findAllSimpleArtists() {
        return new ResponseEntity<>(simpleArtistRepository.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SimpleArtist> findSimpleArtistById(Long id) {
        return new ResponseEntity<>(simpleArtistRepository.findById(id).get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<SimpleArtist>> findSimpleArtistsByNameContaining(String name) {
        if(simpleArtistRepository.findSimpleArtistByNameContaining(name).isPresent())
        {
            return new ResponseEntity<>(simpleArtistRepository.findSimpleArtistByNameContaining(name).get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }

    @Override
    public ResponseEntity<SimpleArtist> addSimpleArtist(SimpleArtist artist) {
        return new ResponseEntity<>(simpleArtistRepository.save(artist), HttpStatus.OK);
    }
}
