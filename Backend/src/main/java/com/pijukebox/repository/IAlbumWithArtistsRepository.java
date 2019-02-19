package com.pijukebox.repository;

import com.pijukebox.model.album.AlbumWithArtists;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IAlbumWithArtistsRepository extends JpaRepository<AlbumWithArtists, Long> {
    Optional<List<AlbumWithArtists>> findAlbumWithArtistsByNameContaining(String name);
}
