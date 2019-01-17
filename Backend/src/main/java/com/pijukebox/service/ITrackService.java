package com.pijukebox.service;

import com.pijukebox.model.Track;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ITrackService {

    List<Track> findAll();

    Optional<Track> findById(Long id);

    Optional<List<Track>> findByNameContaining(String name);
}
