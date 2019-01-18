package com.pijukebox.repository;

import com.pijukebox.model.simple.SimpleGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ISimpleGenreRepository extends JpaRepository<SimpleGenre, Long> {
    List<SimpleGenre> findAll();

    Optional<SimpleGenre> findById(Long id);
}
