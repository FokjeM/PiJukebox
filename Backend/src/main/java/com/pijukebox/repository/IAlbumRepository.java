package com.pijukebox.repository;

import com.pijukebox.model.album.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IAlbumRepository extends JpaRepository<Album, Long> {
    Optional<List<Album>> findAlbumsByNameContaining(String name);
}
