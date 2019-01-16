package com.pijukebox.repository;

import com.pijukebox.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IAlbumRepository extends JpaRepository<Album, Long> {

    List<Album> findAll();

    Optional<Album> findById(Long id);

    Optional<List<Album>> findAlbumsByNameContaining(String name);
}
