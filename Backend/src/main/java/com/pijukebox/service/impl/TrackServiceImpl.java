package com.pijukebox.service.impl;

import com.pijukebox.model.track.Track;
import com.pijukebox.repository.ITrackRepository;
import com.pijukebox.service.ITrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TrackServiceImpl implements ITrackService {
    private final ITrackRepository trackRepository;

    @Autowired
    public TrackServiceImpl(ITrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    @Override
    public List<Track> findAll() {
        return trackRepository.findAll();
    }

    @Transactional
    @Override
    public Optional<Track> findById(Long id) {
        return trackRepository.findById(id);
    }
}
