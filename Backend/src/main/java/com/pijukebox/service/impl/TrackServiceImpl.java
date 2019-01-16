package com.pijukebox.service.impl;

import com.pijukebox.model.artist.ArtistWithTracks;
import com.pijukebox.model.genre.GenreWithTracks;
import com.pijukebox.model.simple.SimpleTrack;
import com.pijukebox.model.track.Track;
import com.pijukebox.repository.IArtistTrackRepository;
import com.pijukebox.repository.IGenreTrackRepository;
import com.pijukebox.repository.ISimpleTrackRepository;
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
    private final IArtistTrackRepository artistTrackRepository;
    private final IGenreTrackRepository genreTrackRepository;
    private final ISimpleTrackRepository simpleTrackRepository;

    @Autowired
    public TrackServiceImpl(ITrackRepository trackRepository,
                            IArtistTrackRepository artistTrackRepository,
                            IGenreTrackRepository genreTrackRepository,
                            ISimpleTrackRepository simpleTrackRepository) {
        this.trackRepository = trackRepository;
        this.artistTrackRepository = artistTrackRepository;
        this.genreTrackRepository = genreTrackRepository;
        this.simpleTrackRepository = simpleTrackRepository;
    }

    @Override
    public List<Track> findAllTracksWithDetails() {
        return trackRepository.findAll();
    }

    @Override
    public Optional<Track> findTrackDetailsById(Long id) {
        return trackRepository.findById(id);
    }

    @Override
    public Optional<List<SimpleTrack>> findAllSimpleTrack() {
        return Optional.of(simpleTrackRepository.findAll());
    }

    @Override
    public Optional<SimpleTrack> findSimpleTrackById(Long id) {
        return simpleTrackRepository.findById(id);
    }

    @Override
    public Optional<List<SimpleTrack>> findAllSimpleTrackByName(String name) {
        return simpleTrackRepository.findSimpleTracksByNameContaining(name);
    }

    @Override
    public Optional<List<Track>> findAllTrackByName(String name) {
        return trackRepository.findTracksByNameContaining(name);
    }

    @Override
    public Optional<List<ArtistWithTracks>> findAllTracksByArtistName(String name) {
        return artistTrackRepository.findArtistTracksByNameContaining(name);
    }

    @Override
    public Optional<List<GenreWithTracks>> findAllTracksByGenreName(String name) {
        return genreTrackRepository.findGenreTracksByNameContaining(name);
    }

    @Override
    public SimpleTrack addSimpleTrack(SimpleTrack simpleTrack) {
        return simpleTrackRepository.save(simpleTrack);
    }

    @Override
    public ArtistWithTracks addArtistToTrack(ArtistWithTracks artistWithTracks) {
        return artistTrackRepository.save(artistWithTracks);
    }

    @Override
    public GenreWithTracks addGenreToTrack(GenreWithTracks genreWithTracks) {
        return genreTrackRepository.save(genreWithTracks);
    }

    @Override
    public Optional<GenreWithTracks> findTrackByGenreId(Long id) {
        return genreTrackRepository.findById(id);
    }

    @Override
    public Optional<ArtistWithTracks> findTrackByArtistId(Long id) {
        return artistTrackRepository.findById(id);
    }

}
