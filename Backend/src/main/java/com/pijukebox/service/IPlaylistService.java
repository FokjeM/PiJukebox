package com.pijukebox.service;

import com.pijukebox.model.Playlist;

import java.util.List;
import java.util.Optional;

public interface IPlaylistService {

    List<Playlist> findAll();

    Optional<Playlist> findById(Long id);

    Optional<List<Playlist>> findAllByUserID(Long userID);

}
