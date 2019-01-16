package com.pijukebox.service;

import com.pijukebox.model.simple.SimpleArtist;

import java.util.List;

public interface IArtistService {

    List<SimpleArtist> findAll();

    List<SimpleArtist> findMany(Long amount);

    SimpleArtist findByName(String name);
}
