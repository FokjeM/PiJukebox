package com.pijukebox.service;

import com.pijukebox.model.Playlist;

import java.util.List;

public interface IPlaylistService {
    List<Playlist> findAllByUserId(Long id);

    Playlist findById(Long id);

    Playlist save(Long userId);

    Playlist delete(Long userId, long playlistId);
}
