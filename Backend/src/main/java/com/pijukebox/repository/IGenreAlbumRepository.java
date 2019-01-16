package com.pijukebox.repository;

import com.pijukebox.model.genre.GenreAlbum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IGenreAlbumRepository extends JpaRepository<GenreAlbum, Long> {

    Optional<List<GenreAlbum>> findGenreAlbumsByNameContaining(String name);
}
