package com.pijukebox.service;

import com.pijukebox.model.User;
import com.pijukebox.model.Role;

import java.util.List;

public interface IUserService {
    List<User> findAll();

    List<User> findByRole(Role role);

    User findById(Long id);
}
