package com.pijukebox.service.impl;

import com.pijukebox.model.User;
import com.pijukebox.repository.IUserRepository;
import com.pijukebox.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;

    @Autowired
    public UserServiceImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByName(String firstname, String lastname) {
        return userRepository.findByFirstnameAndLastname(firstname, lastname);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
}
