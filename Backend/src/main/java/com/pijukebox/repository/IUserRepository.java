package com.pijukebox.repository;

import com.pijukebox.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    List<User> findAll();

    Optional<User> findById(Long id);

    Optional<User> findByFirstnameAndLastname(String firstname, String lastname);

    Optional<User> findByToken(String token);

    Optional<User> findByEmailAndPassword(String email, String password);

    void deleteById(Long id);
}
