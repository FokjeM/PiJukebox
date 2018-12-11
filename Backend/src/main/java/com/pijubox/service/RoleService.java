package com.pijubox.service;

import com.pijubox.model.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAll();

    Role findByName(String name);
}
