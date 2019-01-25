package com.pijukebox.service;

import com.pijukebox.model.playlist.PlaylistWithTracks;
import com.pijukebox.model.simple.SimplePlaylist;
import com.pijukebox.model.user.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> findAllUsers();

    Optional<User> findById(Long id);

    Optional<List<PlaylistWithTracks>> findPlaylistsByUserId(Long id);

    Optional<List<SimplePlaylist>> findSimplePlaylistsByUserId(Long id);

    Optional<User> findByToken(String token);

    Optional<User> findByEmailAndPassword(String email, String password);

    User saveUser(User user);
}
