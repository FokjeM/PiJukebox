package com.pijukebox.repository;

import com.pijukebox.model.artist.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IArtistRepository extends JpaRepository<Artist, Long> {
    Optional<List<Artist>> findExtendedArtistsByNameContaining(String name);
}
