package com.pijukebox.service;

import com.pijukebox.model.Album;
import com.pijukebox.model.Genre;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IAlbumService {
    List<Album> findAll();

    Album findById(Long id);

    Album findAlbumGenre(Long id);

    List<Album> findAlbumsDetails();
}
