package com.pijukebox.repository;

import com.pijukebox.model.genre.Genre;
import com.pijukebox.model.simple.SimpleGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IGenreRepository extends JpaRepository<Genre, Long> {
    List<Genre> findAll();

    Optional<Genre> findById(Long id);

    Optional<List<Genre>> findGenresByNameContaining(String name);
}
