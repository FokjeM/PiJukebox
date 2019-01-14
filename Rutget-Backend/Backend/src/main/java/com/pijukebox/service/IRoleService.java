package com.pijukebox.service;

import com.pijukebox.model.Role;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IRoleService {
    List<Role> findAll();

    Role findByName(String name);
}
