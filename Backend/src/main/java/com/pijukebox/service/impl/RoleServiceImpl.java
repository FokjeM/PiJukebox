package com.pijukebox.service.impl;

import com.pijukebox.model.Role;
import com.pijukebox.service.IRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService {
    @Override
    public List<Role> findAll() {
        return null;
    }

    @Override
    public Role findByName(String name) {
        return null;
    }
}
