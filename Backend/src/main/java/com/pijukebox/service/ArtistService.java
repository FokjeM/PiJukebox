package com.pijukebox.service;

import com.pijukebox.model.Artist;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArtistService {

    List<Artist> findAll();

    List<Artist> findMany(Long amount);

    Artist findByName(String name);

    Artist addArtist(Artist artist);

    Artist deleteArtist(Long id);
}
