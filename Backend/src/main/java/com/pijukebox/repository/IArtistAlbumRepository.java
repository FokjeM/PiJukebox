package com.pijukebox.repository;

import com.pijukebox.model.artist.ArtistWithAlbums;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IArtistAlbumRepository extends JpaRepository<ArtistWithAlbums, Long> {

    Optional<List<ArtistWithAlbums>> findArtistAlbumsByNameContaining(String name);
}
