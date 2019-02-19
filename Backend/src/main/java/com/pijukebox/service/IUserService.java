package com.pijukebox.service;

import com.pijukebox.model.playlist.PlaylistWithTracks;
import com.pijukebox.model.simple.SimplePlaylist;
import com.pijukebox.model.user.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUserService {
    ResponseEntity<List<User>> findAllUsers();

    ResponseEntity<User> findById(Long id);

    ResponseEntity<List<PlaylistWithTracks>> findPlaylistsByUserId(Long id);

    ResponseEntity<List<SimplePlaylist>> findSimplePlaylistsByUserId(Long id);

    ResponseEntity<User> findByToken(String token);

    ResponseEntity<User> findByEmailAndPassword(String email, String password);

    ResponseEntity<User> saveUser(User user);
}
