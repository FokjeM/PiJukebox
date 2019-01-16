package com.pijukebox.service;

import com.pijukebox.model.playlist.PlaylistTrack;
import com.pijukebox.model.simple.SimplePlaylist;
import com.pijukebox.model.user.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> findAll();

    User findByName(String firstname, String lastname);

    Optional<User> findById(Long id);

    Optional<List<PlaylistTrack>> findPlaylistsByUser(Long userID);

    Optional<List<SimplePlaylist>> findSimplePlaylistsByUser(Long userID);

}
