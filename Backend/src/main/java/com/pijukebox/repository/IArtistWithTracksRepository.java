package com.pijukebox.repository;

import com.pijukebox.model.artist.ArtistWithTracks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IArtistWithTracksRepository extends JpaRepository<ArtistWithTracks, Long> {
    Optional<List<ArtistWithTracks>> findArtistWithTracksByNameContaining(String name);
}
