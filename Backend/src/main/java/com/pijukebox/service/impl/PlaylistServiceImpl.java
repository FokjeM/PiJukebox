package com.pijukebox.service.impl;

import com.pijukebox.model.playlist.PlaylistTrack;
import com.pijukebox.repository.IPlaylistRepository;
import com.pijukebox.service.IPlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PlaylistServiceImpl implements IPlaylistService {

    private final IPlaylistRepository playlistRepository;

    @Autowired
    public PlaylistServiceImpl(IPlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    @Override
    public List<PlaylistTrack> findAll() {
        return playlistRepository.findAll();
    }

    @Override
    public Optional<PlaylistTrack> findById(Long id) {
        return playlistRepository.findById(id);
    }

    @Override
    public Optional<List<PlaylistTrack>> findAllByUserID(Long userID) {
        return playlistRepository.findAllByUserID(userID);
    }
}
