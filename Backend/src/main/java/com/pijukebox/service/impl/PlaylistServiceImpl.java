package com.pijukebox.service.impl;

import com.pijukebox.model.Playlist;
import com.pijukebox.service.IPlaylistService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistServiceImpl implements IPlaylistService {

    @Override
    public List<Playlist> findAllByUserId(Long id) {
        return null;
    }

    @Override
    public Playlist findById(Long id) {
        return null;
    }

    @Override
    public Playlist save(Playlist playlist) {
        return null;
    }

    @Override
    public Playlist delete(Long userId, long playlistId) {
        return null;
    }
}
