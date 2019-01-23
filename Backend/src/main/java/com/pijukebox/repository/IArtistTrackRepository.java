package com.pijukebox.repository;

import com.pijukebox.model.artist.ArtistWithTracks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IArtistTrackRepository extends JpaRepository<ArtistWithTracks, Long> {
    List<ArtistWithTracks> findAll();

    Optional<ArtistWithTracks> findById(Long id);

    Optional<List<ArtistWithTracks>> findArtistTracksByNameContaining(String name);
}