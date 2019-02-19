package com.pijukebox.repository;

import com.pijukebox.model.album.AlbumWithGenres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IAlbumWithGenreRepository extends JpaRepository<AlbumWithGenres, Long> {
    Optional<List<AlbumWithGenres>> findAlbumWithGenresByNameContaining(String name);
}
