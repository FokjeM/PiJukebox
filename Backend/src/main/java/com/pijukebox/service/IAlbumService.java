package com.pijukebox.service;

import com.pijukebox.model.Album;
import com.pijukebox.model.SimpleAlbum;

import java.util.List;

public interface IAlbumService {
    List<SimpleAlbum> findAll();

    SimpleAlbum findById(Long id);

    List<Album> findAlbumsDetails();
}
