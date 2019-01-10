package com.pijukebox.service;

import com.pijukebox.model.Album;

import java.util.List;
import java.util.Optional;

public interface IAlbumService {

    List<Album> findAll();

    Optional<Album> findById(Long id);

    Album findByName(String name);
}
