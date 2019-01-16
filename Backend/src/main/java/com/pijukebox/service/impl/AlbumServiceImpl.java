package com.pijukebox.service.impl;

import com.pijukebox.model.album.Album;
import com.pijukebox.model.album.AlbumWithArtists;
import com.pijukebox.model.album.AlbumWithTracks;
import com.pijukebox.model.artist.ArtistWithAlbums;
import com.pijukebox.model.genre.GenreWithAlbums;
import com.pijukebox.model.simple.SimpleAlbum;
import com.pijukebox.model.simple.SimpleArtist;
import com.pijukebox.model.simple.SimpleTrack;
import com.pijukebox.repository.*;
import com.pijukebox.service.IAlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AlbumServiceImpl implements IAlbumService {

    private final IAlbumRepository albumRepository;
    private final ISimpleAlbumRepository simpleAlbumRepository;
    private final IGenreAlbumRepository genreAlbumRepository;
    private final IArtistAlbumRepository artistAlbumRepository;
    private final IAlbumTrackRepository albumTrackRepository;
    private final ISimpleTrackRepository simpleTrackRepository;
    private final ISimpleArtistRepository simpleArtistRepository;
    private final IAlbumArtistRepository albumArtistRepository;

    @Autowired
    public AlbumServiceImpl(IAlbumRepository albumRepository, ISimpleAlbumRepository simpleAlbumRepository, IGenreAlbumRepository genreAlbumRepository, IArtistAlbumRepository artistAlbumRepository, IAlbumTrackRepository albumTrackRepository, ISimpleTrackRepository simpleTrackRepository, ISimpleArtistRepository simpleArtistRepository, IAlbumArtistRepository albumArtistRepository) {
        this.albumRepository = albumRepository;
        this.simpleAlbumRepository = simpleAlbumRepository;
        this.genreAlbumRepository = genreAlbumRepository;
        this.artistAlbumRepository = artistAlbumRepository;
        this.albumTrackRepository = albumTrackRepository;
        this.simpleTrackRepository = simpleTrackRepository;
        this.simpleArtistRepository = simpleArtistRepository;
        this.albumArtistRepository = albumArtistRepository;
    }

    @Override
    public List<Album> findAllExtendedAlbums() {
        return albumRepository.findAll();
    }

    @Override
    public Optional<Album> findExtendedAlbumById(Long id) {
        return albumRepository.findById(id);
    }

    @Override
    public Optional<List<Album>> findAlbumsByNameContaining(String name) {
        return albumRepository.findAlbumsByNameContaining(name);
    }

    @Override
    public List<SimpleAlbum> findAllSimpleAlbums() {
        return simpleAlbumRepository.findAll();
    }

    @Override
    public Optional<SimpleAlbum> findSimpleAlbumById(Long id) {
        return simpleAlbumRepository.findById(id);
    }

    @Override
    public Optional<List<SimpleAlbum>> findSimpleAlbumsByNameContaining(String name) {
        return simpleAlbumRepository.findSimpleAlbumsByNameContaining(name);
    }

    @Override
    public Optional<List<GenreWithAlbums>> findSimpleAlbumsByGenreName(String name) {
        return genreAlbumRepository.findGenreAlbumsByNameContaining(name);
    }

    @Override
    public Optional<List<ArtistWithAlbums>> findSimpleAlbumsByArtistName(String name) {
        return artistAlbumRepository.findArtistAlbumsByNameContaining(name);
    }

    @Override
    public Optional<SimpleTrack> findTrackById(Long id) {
        return simpleTrackRepository.findById(id);
    }

    @Override
    public Optional<AlbumWithTracks> findTrackByAlbumId(Long id) {
        return albumTrackRepository.findById(id);
    }

    @Override
    public AlbumWithTracks addTrackToAlbum(AlbumWithTracks track) {
        return albumTrackRepository.save(track);
    }

    @Override
    public Optional<SimpleArtist> findArtistById(Long id) {
        return simpleArtistRepository.findById(id);
    }

    @Override
    public Optional<AlbumWithArtists> findArtistByAlbumId(Long id) {
        return albumArtistRepository.findById(id);
    }

    @Override
    public AlbumWithArtists addArtistToAlbum(AlbumWithArtists album) {
        return albumArtistRepository.save(album);
    }
}
