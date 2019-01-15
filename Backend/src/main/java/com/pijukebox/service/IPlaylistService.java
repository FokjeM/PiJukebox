package com.pijukebox.service;

import com.pijukebox.model.playlist.PlaylistTrack;

import java.util.List;
import java.util.Optional;

public interface IPlaylistService {

    List<PlaylistTrack> findAll();

    Optional<PlaylistTrack> findById(Long id);

    Optional<List<PlaylistTrack>> findAllByUserID(Long userID);

}
