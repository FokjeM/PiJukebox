package com.pijukebox.repository;

import com.pijukebox.model.simple.SimpleArtist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ISimpleArtistRepository extends JpaRepository<SimpleArtist, Long> {

    List<SimpleArtist> findAll();

    Optional<SimpleArtist> findById(Long id);

    Optional<List<SimpleArtist>> findSimpleArtistByNameContaining(String name);
}