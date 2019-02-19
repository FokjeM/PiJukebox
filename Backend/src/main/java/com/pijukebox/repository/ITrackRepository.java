package com.pijukebox.repository;

import com.pijukebox.model.track.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITrackRepository extends JpaRepository<Track, Long> {
    Optional<List<Track>> findTracksByNameContaining(String name);
}
