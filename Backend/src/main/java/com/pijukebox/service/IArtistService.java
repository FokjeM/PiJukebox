package com.pijukebox.service;

import com.pijukebox.model.artist.Artist;
import com.pijukebox.model.simple.SimpleArtist;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IArtistService {

    ResponseEntity<List<Artist>> findAllExtendedArtists();

    ResponseEntity<Artist> findExtendedArtistById(Long id);

    ResponseEntity<List<Artist>> findExtendedArtistsByNameContaining(String name);

    ResponseEntity<List<SimpleArtist>> findAllSimpleArtists();

    ResponseEntity<SimpleArtist> findSimpleArtistById(Long id);

    ResponseEntity<List<SimpleArtist>> findSimpleArtistsByNameContaining(String name);

    ResponseEntity<SimpleArtist> addSimpleArtist(SimpleArtist artist);
}
