package com.pijukebox.service.impl;

import com.pijukebox.model.playlist.PlaylistWithTracks;
import com.pijukebox.model.simple.SimplePlaylist;
import com.pijukebox.model.user.User;
import com.pijukebox.repository.IPlaylistWithTracksRepository;
import com.pijukebox.repository.ISimplePlaylistRepository;
import com.pijukebox.repository.IUserRepository;
import com.pijukebox.service.IUserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SuppressWarnings("OptionalGetWithoutIsPresent")
@Data
@Service
@Transactional
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;
    private final IPlaylistWithTracksRepository playlistRepository;
    private final ISimplePlaylistRepository simplePlaylistRepository;

    @Autowired
    public UserServiceImpl(IUserRepository userRepository, IPlaylistWithTracksRepository playlistRepository, ISimplePlaylistRepository simplePlaylistRepository) {
        this.userRepository = userRepository;
        this.playlistRepository = playlistRepository;
        this.simplePlaylistRepository = simplePlaylistRepository;
    }

    @Override
    public ResponseEntity<List<User>> findAllUsers() {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<User> findByToken(String token) {
        return new ResponseEntity<>(userRepository.findByToken(token).get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<User> findByEmailAndPassword(String email, String password) {
        return new ResponseEntity<>(userRepository.findByEmailAndPassword(email, password).get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<User> saveUser(User user) {
        return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
    }


    @Override
    public ResponseEntity<User> findById(Long id) {
        return new ResponseEntity<>(userRepository.findById(id).get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<PlaylistWithTracks>> findPlaylistsByUserId(Long userID) {
        return new ResponseEntity<>(playlistRepository.findAllByUserID(userID).get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<SimplePlaylist>> findSimplePlaylistsByUserId(Long userID) {
        return new ResponseEntity<>(simplePlaylistRepository.findAllByUserID(userID).get(), HttpStatus.OK);
    }
}
