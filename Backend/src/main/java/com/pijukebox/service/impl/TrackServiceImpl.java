package com.pijukebox.service.impl;

import com.pijukebox.model.artist.ArtistWithTracks;
import com.pijukebox.model.genre.GenreWithTracks;
import com.pijukebox.model.simple.SimpleTrack;
import com.pijukebox.model.track.Track;
import com.pijukebox.repository.IArtistWithTracksRepository;
import com.pijukebox.repository.IGenreWithTracksRepository;
import com.pijukebox.repository.ISimpleTrackRepository;
import com.pijukebox.repository.ITrackRepository;
import com.pijukebox.service.ITrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SuppressWarnings("OptionalGetWithoutIsPresent")
@Service
@Transactional
public class TrackServiceImpl implements ITrackService {
    private final ITrackRepository trackRepository;
    private final IArtistWithTracksRepository artistWithTracksRepository;
    private final IGenreWithTracksRepository genreWithTracksRepository;
    private final ISimpleTrackRepository simpleTrackRepository;

    @Autowired
    public TrackServiceImpl(ITrackRepository trackRepository, IArtistWithTracksRepository artistWithTracksRepository, IGenreWithTracksRepository genreWithTracksRepository, ISimpleTrackRepository simpleTrackRepository) {
        this.trackRepository = trackRepository;
        this.artistWithTracksRepository = artistWithTracksRepository;
        this.genreWithTracksRepository = genreWithTracksRepository;
        this.simpleTrackRepository = simpleTrackRepository;
    }

    @Override
    public ResponseEntity<List<Track>> findAllTracksWithDetails() {
        return new ResponseEntity<>(trackRepository.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Track> findTrackDetailsById(Long id) {
        return new ResponseEntity<>(trackRepository.findById(id).get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<SimpleTrack>> findAllSimpleTrack() {
        return new ResponseEntity<>(simpleTrackRepository.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SimpleTrack> findSimpleTrackByName(String name) {
        if(simpleTrackRepository.findSimpleTrackByName(name).isPresent())
        {
            return new ResponseEntity<>(simpleTrackRepository.findSimpleTrackByName(name).get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }

    @Override
    public ResponseEntity<SimpleTrack> findSimpleTrackById(Long id) {
        return new ResponseEntity<>(simpleTrackRepository.findById(id).get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<SimpleTrack>> findAllSimpleTrackByName(String name) {
        return new ResponseEntity<>(simpleTrackRepository.findSimpleTracksByNameContaining(name).get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Track>> findAllTracksByName(String name) {
        return new ResponseEntity<>(trackRepository.findTracksByNameContaining(name).get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ArtistWithTracks>> findAllTracksByArtistName(String name) {
        return new ResponseEntity<>(artistWithTracksRepository.findArtistWithTracksByNameContaining(name).get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<GenreWithTracks>> findAllTracksByGenreName(String name) {
        return new ResponseEntity<>(genreWithTracksRepository.findGenreTracksByNameContaining(name).get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SimpleTrack> addSimpleTrack(SimpleTrack simpleTrack) {
        return new ResponseEntity<>(simpleTrackRepository.save(simpleTrack), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ArtistWithTracks> addArtistToTrack(ArtistWithTracks artistWithTracks) {
        return new ResponseEntity<>(artistWithTracksRepository.save(artistWithTracks), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GenreWithTracks> addGenreToTrack(GenreWithTracks genreWithTracks) {
        return new ResponseEntity<>(genreWithTracksRepository.save(genreWithTracks), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GenreWithTracks> findTrackByGenreId(Long id) {
        return new ResponseEntity<>(genreWithTracksRepository.findById(id).get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ArtistWithTracks> findTrackByArtistId(Long id) {
        return new ResponseEntity<>(artistWithTracksRepository.findById(id).get(), HttpStatus.OK);
    }
}
