package com.pijukebox.repository;

import com.pijukebox.model.album.Album;
import com.pijukebox.model.simple.SimpleAlbum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IAlbumRepository extends JpaRepository<SimpleAlbum, Long> {

    List<SimpleAlbum> findAll();

    Optional<SimpleAlbum> findById(Long id);

    Optional<List<SimpleAlbum>> findAlbumsByNameContaining(String name);
}
