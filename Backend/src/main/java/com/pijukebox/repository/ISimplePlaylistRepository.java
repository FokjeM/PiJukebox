package com.pijukebox.repository;

import com.pijukebox.model.simple.SimplePlaylist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ISimplePlaylistRepository extends JpaRepository<SimplePlaylist, Long> {
    List<SimplePlaylist> findAll();
}
