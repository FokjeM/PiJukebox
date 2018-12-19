package com.pijukebox.repository;

import com.pijukebox.model.User;

import java.util.List;

public interface IUserRepository {
    List<User> findAll();

    User getById(Long id);

    User getByName(String name);
}
