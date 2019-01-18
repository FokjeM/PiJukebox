package com.pijukebox.service.impl;

import com.pijukebox.model.playlist.PlaylistWithTracks;
import com.pijukebox.model.simple.SimplePlaylist;
import com.pijukebox.model.user.User;
import com.pijukebox.repository.IPlaylistRepository;
import com.pijukebox.repository.ISimplePlaylistRepository;
import com.pijukebox.repository.IUserRepository;
import com.pijukebox.service.IUserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Data
@Service
@Transactional
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;
    private final IPlaylistRepository playlistRepository;
    private final ISimplePlaylistRepository simplePlaylistRepository;

    @Autowired
    public UserServiceImpl(IUserRepository userRepository, IPlaylistRepository playlistRepository, ISimplePlaylistRepository simplePlaylistRepository) {
        this.userRepository = userRepository;
        this.playlistRepository = playlistRepository;
        this.simplePlaylistRepository = simplePlaylistRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findByName(String firstname, String lastname) {
        return userRepository.findByFirstnameAndLastname(firstname, lastname);
    }

    @Override
    public Optional<User> findByToken(String token) {
        return userRepository.findByToken(token);
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }


    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<List<PlaylistWithTracks>> findPlaylistsByUser(Long userID) {
        return playlistRepository.findAllByUserID(userID);
    }

    @Override
    public Optional<List<SimplePlaylist>> findSimplePlaylistsByUser(Long userID) {
        return simplePlaylistRepository.findAllByUserID(userID);
    }
}
