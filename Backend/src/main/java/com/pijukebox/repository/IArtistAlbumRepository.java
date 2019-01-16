package com.pijukebox.repository;

import com.pijukebox.model.artist.ArtistAlbum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IArtistAlbumRepository extends JpaRepository<ArtistAlbum, Long> {

    Optional<List<ArtistAlbum>> findArtistAlbumsByNameContaining(String name);
}
