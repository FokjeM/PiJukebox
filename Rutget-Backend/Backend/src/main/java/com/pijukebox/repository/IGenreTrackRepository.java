package com.pijukebox.repository;

import com.pijukebox.model.genre.Genre;
import com.pijukebox.model.genre.GenreTrack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IGenreTrackRepository extends JpaRepository<GenreTrack, Long> {
    List<GenreTrack> findAll();

    Optional<GenreTrack> findById();

    Optional<List<GenreTrack>> findGenreTracksByNameContaining(String name);
}
