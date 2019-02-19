package com.pijukebox.repository;

import com.pijukebox.model.simple.SimpleAlbum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ISimpleAlbumRepository extends JpaRepository<SimpleAlbum, Long> {
    Optional<List<SimpleAlbum>> findSimpleAlbumsByNameContaining(String name);
}
