package com.pijukebox.service.impl;

import com.pijukebox.model.playlist.PlaylistTrack;
import com.pijukebox.model.simple.SimplePlaylist;
import com.pijukebox.repository.IPlaylistRepository;
import com.pijukebox.repository.ISimplePlaylistRepository;
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
    private final ISimplePlaylistRepository simplePlaylistRepository;

    @Autowired
    public PlaylistServiceImpl(IPlaylistRepository playlistRepository, ISimplePlaylistRepository simplePlaylistRepository) {
        this.playlistRepository = playlistRepository;
        this.simplePlaylistRepository = simplePlaylistRepository;
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
    public List<SimplePlaylist> findAllSimplePlaylists() {
        return simplePlaylistRepository.findAll();
    }

    @Override
    public Optional<SimplePlaylist> findSimplePlaylistById(Long id) {
        return simplePlaylistRepository.findById(id);
    }

}
