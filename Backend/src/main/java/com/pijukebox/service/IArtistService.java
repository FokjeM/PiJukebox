package com.pijukebox.service;

import com.pijukebox.model.artist.Artist;
import com.pijukebox.model.simple.SimpleArtist;

import java.util.List;
import java.util.Optional;

public interface IArtistService {

    List<Artist> findAll();

    Optional<SimpleArtist> findById(Long id);

    Optional<List<SimpleArtist>> findGenresByNameContaining(String name);
}
