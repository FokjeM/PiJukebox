package com.pijukebox.repository;

import com.pijukebox.model.Album;

import java.util.List;

public interface IAlbumRepository {
    List<Album> getAll();

    Album getById(Long id);

    Album getDetails(Long id);
}
