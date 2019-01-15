package com.pijukebox.service;

import com.pijukebox.model.artist.Artist;

import java.util.List;
import java.util.Optional;

public interface IArtistService {

    List<Artist> findAll();

    Optional<Artist> findById(Long id);

    Optional<List<Artist>> findGenresByNameContaining(String name);
}
