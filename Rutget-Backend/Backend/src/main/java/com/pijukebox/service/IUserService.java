package com.pijukebox.service;

import com.pijukebox.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> findAll();

    User findByName(String firstname, String lastname);

    Optional<User> findById(Long id);
}
