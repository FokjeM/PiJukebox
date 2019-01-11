package com.pijukebox.service.impl;

import com.pijukebox.model.Album;
import com.pijukebox.model.SimpleAlbum;
import com.pijukebox.model.Track;
import com.pijukebox.repository.ITrackRepository;
import com.pijukebox.service.ITrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

        Optional<Track> track = trackRepository.findById(id);

        if (!track.isPresent()) {
            return Optional.empty();
        }

        // track.getAlbums().stream().map(album -> new SimpleAlbum(album.getId(), album.getName())).collect(Collectors.toSet());

        //track.get().getAlbums().stream().map(album -> new SimpleAlbum(album.getId(), album.getName())).collect(Collectors.toSet());

        Set<Album> trackWithAlbums = track.get().getAlbums();
        track.get().setAlbums(trackWithAlbums);

        return track;
    }
}
