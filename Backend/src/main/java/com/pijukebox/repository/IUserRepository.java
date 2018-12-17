package com.pijukebox.repository;

import com.pijukebox.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    List<User> findAll();

    User findById();

    User findByName(String name);
}
