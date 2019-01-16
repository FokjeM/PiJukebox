package com.pijukebox.service;

import com.pijukebox.model.playlist.PlaylistTrack;
import com.pijukebox.model.simple.SimplePlaylist;
import com.pijukebox.model.simple.SimpleTrack;

import java.util.List;
import java.util.Optional;

public interface IPlaylistService {

    List<PlaylistTrack> findAll();

    Optional<PlaylistTrack> findById(Long id);

    List<SimplePlaylist> findAllSimplePlaylists();

    Optional<SimplePlaylist> findSimplePlaylistById(Long id);

    SimplePlaylist addNewPlaylist(SimplePlaylist simplePlaylist);

    PlaylistTrack addTrackToPlaylist(PlaylistTrack playlistTrack);

    PlaylistTrack deleteTrackFromPlaylist(PlaylistTrack playlistTrack);

}
