package com.pijukebox.repository;

import com.pijukebox.model.track.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITrackRepository extends JpaRepository<Track, Long> {

    List<Track> findAll();

    Optional<Track> findById(Long id);

    Optional<List<Track>> findTrakcsByNameContaining(String name);
}
