package com.pijukebox.repository;

import com.pijukebox.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPlaylistRepository extends JpaRepository<Playlist, Long> {
}
