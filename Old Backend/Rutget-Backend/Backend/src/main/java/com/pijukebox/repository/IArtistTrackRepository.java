package com.pijukebox.repository;

import com.pijukebox.model.artist.ArtistTrack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IArtistTrackRepository extends JpaRepository<ArtistTrack, Long> {
    List<ArtistTrack> findAll();

    Optional<ArtistTrack> findById(Long id);

    Optional<List<ArtistTrack>> findArtistTracksByNameContaining(String name);
}
