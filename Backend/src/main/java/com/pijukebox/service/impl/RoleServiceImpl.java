package com.pijukebox.service.impl;

import com.pijukebox.model.Role;
import com.pijukebox.service.IRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
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
