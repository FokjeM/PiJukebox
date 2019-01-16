package com.pijukebox.repository;

import com.pijukebox.model.album.Album;
import com.pijukebox.model.album.AlbumTrack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IAlbumTrackRepository extends JpaRepository<AlbumTrack, Long> {

    List<AlbumTrack> findAll();

    Optional<AlbumTrack> findById(Long id);

    Optional<List<AlbumTrack>> findAlbumTracksByNameContaining(String name);
}
