package com.pijukebox.service;

import com.pijukebox.model.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAll();

    Role findByName(String name);
}
