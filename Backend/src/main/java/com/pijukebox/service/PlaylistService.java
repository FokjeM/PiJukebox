package com.pijukebox.service;

import com.pijukebox.model.Playlist;

import java.util.List;

public interface PlaylistService {
    List<Playlist> findAll();

    Playlist findById(Long id);
}
