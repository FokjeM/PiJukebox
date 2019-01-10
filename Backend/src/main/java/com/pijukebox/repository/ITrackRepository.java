package com.pijukebox.repository;

import com.pijukebox.model.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITrackRepository extends JpaRepository<Track, Long> {
}
