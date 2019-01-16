package com.pijukebox.service;

import com.pijukebox.model.simple.SimpleArtist;

import java.util.List;

public interface IArtistService {

    List<SimpleArtist> findAll();


    SimpleArtist findByName(String name);
}
