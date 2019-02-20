package com.pijukebox.service.impl;

import com.pijukebox.model.playlist.PlaylistWithTracks;
import com.pijukebox.model.simple.SimplePlaylist;
import com.pijukebox.repository.IPlaylistWithTracksRepository;
import com.pijukebox.repository.ISimplePlaylistRepository;
import com.pijukebox.service.IPlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SuppressWarnings("OptionalGetWithoutIsPresent")
@Service
@Transactional
public class PlaylistServiceImpl implements IPlaylistService {

    private final IPlaylistWithTracksRepository playlistWithTracksRepository;
    private final ISimplePlaylistRepository simplePlaylistRepository;

    @Autowired
    public PlaylistServiceImpl(IPlaylistWithTracksRepository playlistRepository, ISimplePlaylistRepository simplePlaylistRepository) {
        this.playlistWithTracksRepository = playlistRepository;
        this.simplePlaylistRepository = simplePlaylistRepository;
    }

    @Override
    public ResponseEntity<List<PlaylistWithTracks>> findAll() {
        return new ResponseEntity<>(playlistWithTracksRepository.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PlaylistWithTracks> findById(Long id) {
        return new ResponseEntity<>(playlistWithTracksRepository.findById(id).get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<SimplePlaylist>> findAllSimplePlaylists() {
        return new ResponseEntity<>(simplePlaylistRepository.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SimplePlaylist> findSimplePlaylistById(Long id) {
        return new ResponseEntity<>(simplePlaylistRepository.findById(id).get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<SimplePlaylist>> findSimplePlaylistsByName(String name) {
        return new ResponseEntity<>(simplePlaylistRepository.findSimplePlaylistsByTitleContaining(name).get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SimplePlaylist> addNewPlaylist(SimplePlaylist simplePlaylist) {
        return new ResponseEntity<>(simplePlaylistRepository.save(simplePlaylist), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PlaylistWithTracks> addTrackToPlaylist(PlaylistWithTracks playlistWithTracks) {
        return new ResponseEntity<>(playlistWithTracksRepository.save(playlistWithTracks), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PlaylistWithTracks> deleteTrackFromPlaylist(PlaylistWithTracks playlistWithTracks) {
        return new ResponseEntity<>(playlistWithTracksRepository.save(playlistWithTracks), HttpStatus.OK);
    }
}
