package com.pijukebox.repository;

import com.pijukebox.model.playlist.PlaylistWithTracks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPlaylistRepository extends JpaRepository<PlaylistWithTracks, Long> {

    List<PlaylistWithTracks> findAll();

    Optional<PlaylistWithTracks> findById(Long id);

    Optional<List<PlaylistWithTracks>> findAllByUserID(Long userID);

}
