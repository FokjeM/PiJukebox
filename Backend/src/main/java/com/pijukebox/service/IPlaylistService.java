package com.pijukebox.service;

import com.pijukebox.model.playlist.PlaylistWithTracks;
import com.pijukebox.model.simple.SimplePlaylist;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IPlaylistService {

    ResponseEntity<List<PlaylistWithTracks>> findAll();

    ResponseEntity<PlaylistWithTracks> findById(Long id);

    ResponseEntity<List<SimplePlaylist>> findAllSimplePlaylists();

    ResponseEntity<SimplePlaylist> findSimplePlaylistById(Long id);

    ResponseEntity<List<SimplePlaylist>> findSimplePlaylistsByName(String name);

    ResponseEntity<SimplePlaylist> addNewPlaylist(SimplePlaylist simplePlaylist);

    ResponseEntity<PlaylistWithTracks> addTrackToPlaylist(PlaylistWithTracks playlistWithTracks);

    ResponseEntity<PlaylistWithTracks> deleteTrackFromPlaylist(PlaylistWithTracks playlistWithTracks);
}
