package com.pijukebox.repository;

import com.pijukebox.model.Album;

import java.util.List;

public interface IAlbumRepository {
    List<Album> findAll();

    Album getById(Long id);

    Album getAlbumDetails(Long id);
}
