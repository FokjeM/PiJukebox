package com.pijukebox.service;

import com.pijukebox.model.Album;

import java.util.List;

public interface IAlbumService {
    List<Album> findAll();

    Album findById(Long id);

    Album findAlbumDetails(long id);
}
