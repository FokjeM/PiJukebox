package com.pijukebox.repository;

import com.pijukebox.model.artist.ArtistWithAlbums;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IArtistWithAlbumsRepository extends JpaRepository<ArtistWithAlbums, Long> {
    List<ArtistWithAlbums> findAll();

    Optional<ArtistWithAlbums> findById(Long id);

    Optional<List<ArtistWithAlbums>> findArtistWithAlbumsByNameContaining(String name);
}
