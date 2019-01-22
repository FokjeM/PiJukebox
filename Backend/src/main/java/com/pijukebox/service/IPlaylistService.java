package com.pijukebox.service;

import com.pijukebox.model.playlist.PlaylistWithTracks;
import com.pijukebox.model.simple.SimplePlaylist;

import java.util.List;
import java.util.Optional;

public interface IPlaylistService {

    List<PlaylistWithTracks> findAll();

    Optional<PlaylistWithTracks> findById(Long id);

    List<SimplePlaylist> findAllSimplePlaylists();

    Optional<SimplePlaylist> findSimplePlaylistById(Long id);

    Optional<List<SimplePlaylist>> findSimplePlaylistsByName(String name);

    SimplePlaylist addNewPlaylist(SimplePlaylist simplePlaylist);

    PlaylistWithTracks addTrackToPlaylist(PlaylistWithTracks playlistWithTracks);

    PlaylistWithTracks deleteTrackFromPlaylist(PlaylistWithTracks playlistWithTracks);
}
