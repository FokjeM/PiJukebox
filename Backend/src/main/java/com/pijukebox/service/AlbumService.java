package com.pijukebox.service;

import com.pijukebox.model.Album;

import java.util.List;

public interface AlbumService {
    List<Album> findAll();

    Album findById(Long id);
}
