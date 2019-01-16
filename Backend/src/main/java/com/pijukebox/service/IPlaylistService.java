package com.pijukebox.service;

import com.pijukebox.model.playlist.Playlist;

import java.util.List;

public interface IPlaylistService {
    List<Playlist> findAllByUserId(Long id);

    Playlist findById(Long id);

    Playlist save(Playlist playlist);

    Playlist delete(Long userId, long playlistId);
}
