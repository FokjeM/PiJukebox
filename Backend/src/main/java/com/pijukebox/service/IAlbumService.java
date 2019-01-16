package com.pijukebox.service;

import com.pijukebox.model.album.Album;
import com.pijukebox.model.simple.SimpleAlbum;

import java.util.List;
import java.util.Optional;

public interface IAlbumService {

    List<SimpleAlbum> findAll();

    Optional<SimpleAlbum> findById(Long id);

    Optional<List<SimpleAlbum>> findAlbumsByNameContaining(String name);
}
