package com.pijukebox.service;

import com.pijukebox.model.playlist.PlaylistTrack;
import com.pijukebox.model.simple.SimplePlaylist;

import java.util.List;
import java.util.Optional;

public interface IPlaylistService {

    List<PlaylistTrack> findAll();

    Optional<PlaylistTrack> findById(Long id);

    List<SimplePlaylist> findAllSimplePlaylists();

    Optional<SimplePlaylist> findSimplePlaylistById(Long id);

}
