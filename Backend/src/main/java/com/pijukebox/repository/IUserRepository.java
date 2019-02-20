package com.pijukebox.repository;

import com.pijukebox.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByToken(String token);

    Optional<User> findByEmailAndPassword(String email, String password);
}
