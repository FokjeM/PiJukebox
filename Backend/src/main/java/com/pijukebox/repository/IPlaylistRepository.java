package com.pijukebox.repository;

import com.pijukebox.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPlaylistRepository extends JpaRepository<Playlist, Long> {

    List<Playlist> findAll();

    Optional<Playlist> findById(Long id);

    Optional<List<Playlist>> findAllByUserID(Long userID);

}
