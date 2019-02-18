package com.pijukebox.repository;

import com.pijukebox.model.genre.GenreWithAlbums;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IGenreWithAlbumsRepository extends JpaRepository<GenreWithAlbums, Long> {
    Optional<List<GenreWithAlbums>> findGenreAlbumsByNameContaining(String name);
}
