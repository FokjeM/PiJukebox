package com.pijukebox.service;

import com.pijukebox.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> findAll();

    Optional<User> findByName(String firstname, String lastname);

    Optional<User> findById(Long id);

    Optional<User> findByToken(String token);

    Optional<User> findByEmailAndPassword(String email, String password);

    User saveUser(User user);

}
