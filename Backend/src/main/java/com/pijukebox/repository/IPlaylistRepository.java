package com.pijukebox.repository;

import com.pijukebox.model.playlist.PlaylistTrack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPlaylistRepository extends JpaRepository<PlaylistTrack, Long> {

    List<PlaylistTrack> findAll();

    Optional<PlaylistTrack> findById(Long id);

    Optional<List<PlaylistTrack>> findAllByUserID(Long userID);

}
