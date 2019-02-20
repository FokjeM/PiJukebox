package com.pijukebox.service.impl;

import com.pijukebox.model.album.Album;
import com.pijukebox.model.album.AlbumWithArtists;
import com.pijukebox.model.album.AlbumWithGenres;
import com.pijukebox.model.album.AlbumWithTracks;
import com.pijukebox.model.simple.SimpleAlbum;
import com.pijukebox.model.simple.SimpleArtist;
import com.pijukebox.model.simple.SimpleGenre;
import com.pijukebox.model.simple.SimpleTrack;
import com.pijukebox.repository.*;
import com.pijukebox.service.IAlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SuppressWarnings("OptionalGetWithoutIsPresent")
@Service
@Transactional
public class AlbumServiceImpl implements IAlbumService {

    private final IAlbumRepository albumRepository;
    private final ISimpleAlbumRepository simpleAlbumRepository;
    private final IAlbumWithTracksRepository albumWithTracksRepository;
    private final ISimpleTrackRepository simpleTrackRepository;
    private final ISimpleArtistRepository simpleArtistRepository;
    private final ISimpleGenreRepository simpleGenreRepository;
    private final IAlbumWithArtistsRepository albumWithArtistsRepository;
    private final IAlbumWithGenreRepository albumWithGenreRepository;

    @Autowired
    public AlbumServiceImpl(IAlbumRepository albumRepository, ISimpleAlbumRepository simpleAlbumRepository, IAlbumWithTracksRepository albumWithTracksRepository, ISimpleTrackRepository simpleTrackRepository, ISimpleArtistRepository simpleArtistRepository, ISimpleGenreRepository simpleGenreRepository, IAlbumWithArtistsRepository albumWithArtistsRepository, IAlbumWithGenreRepository albumWithGenreRepository) {
        this.albumRepository = albumRepository;
        this.simpleAlbumRepository = simpleAlbumRepository;
        this.albumWithTracksRepository = albumWithTracksRepository;
        this.simpleTrackRepository = simpleTrackRepository;
        this.simpleArtistRepository = simpleArtistRepository;
        this.simpleGenreRepository = simpleGenreRepository;
        this.albumWithArtistsRepository = albumWithArtistsRepository;
        this.albumWithGenreRepository = albumWithGenreRepository;
    }

    @Override
    public ResponseEntity<List<Album>> findAllExtendedAlbums() {
        return new ResponseEntity<>(albumRepository.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Album> findExtendedAlbumById(Long id) {
        return new ResponseEntity<>(albumRepository.findById(id).get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Album>> findAlbumsByNameContaining(String name) {
        return new ResponseEntity<>(albumRepository.findAlbumsByNameContaining(name).get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<SimpleAlbum>> findAllSimpleAlbums() {
        return new ResponseEntity<>(simpleAlbumRepository.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SimpleAlbum> findSimpleAlbumById(Long id) {
        return new ResponseEntity<>(simpleAlbumRepository.findById(id).get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<SimpleAlbum>> findSimpleAlbumsByNameContaining(String name) {
        return new ResponseEntity<>(simpleAlbumRepository.findSimpleAlbumsByNameContaining(name).get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<AlbumWithArtists>> findAlbumWithArtistsByNameContaining(String name) {
        return new ResponseEntity<>(albumWithArtistsRepository.findAlbumWithArtistsByNameContaining(name).get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<AlbumWithGenres>> findAlbumWithGenresByNameContaining(String name) {
        return new ResponseEntity<>(albumWithGenreRepository.findAlbumWithGenresByNameContaining(name).get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<AlbumWithTracks>> findAlbumWithTracksByNameContaining(String name) {
        return new ResponseEntity<>(albumWithTracksRepository.findAlbumWithTracksByNameContaining(name).get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SimpleTrack> findTrackById(Long id) {
        return new ResponseEntity<>(simpleTrackRepository.findById(id).get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AlbumWithTracks> findTrackByAlbumId(Long id) {
        return new ResponseEntity<>(albumWithTracksRepository.findById(id).get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AlbumWithTracks> addTrackToAlbum(AlbumWithTracks track) {
        return new ResponseEntity<>(albumWithTracksRepository.save(track), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SimpleArtist> findArtistById(Long id) {
        return new ResponseEntity<>(simpleArtistRepository.findById(id).get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AlbumWithArtists> findArtistByAlbumId(Long id) {
        return new ResponseEntity<>(albumWithArtistsRepository.findById(id).get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AlbumWithArtists> addArtistToAlbum(AlbumWithArtists album) {
        return new ResponseEntity<>(albumWithArtistsRepository.save(album), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AlbumWithGenres> findGenreByAlbumId(Long id) {
        return new ResponseEntity<>(albumWithGenreRepository.findById(id).get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SimpleGenre> findGenreById(Long id) {
        return new ResponseEntity<>(simpleGenreRepository.findById(id).get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AlbumWithGenres> addGenreToAlbum(AlbumWithGenres album) {
        return new ResponseEntity<>(albumWithGenreRepository.save(album), HttpStatus.OK);
    }
}
