package com.pijukebox.repository;

import com.pijukebox.model.album.AlbumWithTracks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IAlbumWithTracksRepository extends JpaRepository<AlbumWithTracks, Long> {
    Optional<List<AlbumWithTracks>> findAlbumWithTracksByNameContaining(String name);
}
