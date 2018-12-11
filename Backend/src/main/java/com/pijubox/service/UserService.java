package com.pijubox.service;

import com.pijubox.model.User;
import com.pijubox.model.Role;

import java.util.List;

public interface UserService {
    User save(User user);

    List<User> findAll();

    List<User> findByRole(Role role);

    User findById(Long id);

}
