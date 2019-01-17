package com.pijukebox.repository;

import com.pijukebox.model.simple.SimplePlaylist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ISimplePlaylistRepository extends JpaRepository<SimplePlaylist, Long> {

    List<SimplePlaylist> findAll();

    Optional<SimplePlaylist> findById(Long id);

    Optional<List<SimplePlaylist>> findAllByUserID(Long userID);
}
