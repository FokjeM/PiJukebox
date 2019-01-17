package com.pijukebox.repository;

import com.pijukebox.model.simple.SimpleTrack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ISimpleTrackRepository extends JpaRepository<SimpleTrack, Long> {
    List<SimpleTrack> findAll();

    Optional<SimpleTrack> findById(Long id);

    Optional<List<SimpleTrack>> findSimpleTracksByNameContaining(String name);


}
