package com.pijukebox.service;

import com.pijukebox.model.artist.Artist;
import com.pijukebox.model.simple.SimpleArtist;

import java.util.List;
import java.util.Optional;

public interface IArtistService {

    List<Artist> findAllExtendedArtists();

    Optional<Artist> findExtendedArtistById(Long id);

    Optional<List<Artist>> findExtendedArtistsByNameContaining(String name);

    List<SimpleArtist> findAllSimpleArtists();

    Optional<SimpleArtist> findSimpleArtistById(Long id);

    Optional<List<SimpleArtist>> findSimpleArtistsByNameContaining(String name);

    SimpleArtist addSimpleArtist(SimpleArtist artist);
}
