package com.pijukebox.repository;

import com.pijukebox.model.genre.GenreWithTracks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IGenreTrackRepository extends JpaRepository<GenreWithTracks, Long> {
    List<GenreWithTracks> findAll();

    Optional<GenreWithTracks> findById(Long id);

    Optional<List<GenreWithTracks>> findGenreTracksByNameContaining(String name);
}
