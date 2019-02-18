package com.pijukebox.repository;

import com.pijukebox.model.album.AlbumWithGenres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IAlbumWithGenreRepository extends JpaRepository<AlbumWithGenres, Long> {

    List<AlbumWithGenres> findAll();

    Optional<AlbumWithGenres> findById(Long id);

    Optional<List<AlbumWithGenres>> findAlbumWithGenresByGenresContaining(String name);
}
