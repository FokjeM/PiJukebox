package com.pijukebox.repository;

import com.pijukebox.model.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ITrackRepository extends JpaRepository<Track, Long> {

    List<Track> findAll();

    Optional<Track> findById(Long id);
}
