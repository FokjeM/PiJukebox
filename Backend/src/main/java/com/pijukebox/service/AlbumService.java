package com.pijukebox.service;

import com.pijukebox.model.Album;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AlbumService {
    List<Album> findAll();

    Album findById(Long id);

    Album addAlbum(Album album);

    Album deleteAlbum(Long id);
}
