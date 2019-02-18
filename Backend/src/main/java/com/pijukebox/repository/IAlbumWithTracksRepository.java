package com.pijukebox.repository;

import com.pijukebox.model.album.AlbumWithTracks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IAlbumWithTracksRepository extends JpaRepository<AlbumWithTracks, Long> {

    List<AlbumWithTracks> findAll();

    Optional<AlbumWithTracks> findById(Long id);

    Optional<List<AlbumWithTracks>> findAlbumWithTracksByNameContaining(String name);
}
