package com.pijukebox.service;

import com.pijukebox.model.artist.ArtistTrack;
import com.pijukebox.model.simple.SimpleArtist;

import java.util.List;
import java.util.Optional;

public interface IArtistService {

    List<SimpleArtist> findAll();

    Optional<SimpleArtist> findById(Long id);

    Optional<List<SimpleArtist>> findByName(String name);

    Optional<ArtistTrack> findTracksByArtistId(Long id);
}
