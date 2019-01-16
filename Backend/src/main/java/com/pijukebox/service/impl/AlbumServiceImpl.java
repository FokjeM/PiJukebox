package com.pijukebox.service.impl;

import com.pijukebox.model.album.Album;
import com.pijukebox.model.album.AlbumWithArtists;
import com.pijukebox.model.album.AlbumWithGenres;
import com.pijukebox.model.album.AlbumWithTracks;
import com.pijukebox.model.artist.ArtistWithAlbums;
import com.pijukebox.model.genre.GenreWithAlbums;
import com.pijukebox.model.simple.SimpleAlbum;
import com.pijukebox.model.simple.SimpleArtist;
import com.pijukebox.model.simple.SimpleGenre;
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
    private final IGenreWithAlbumsRepository genreWithAlbumsRepository;
    private final IArtistWithAlbumsRepository artistWithAlbumsRepository;
    private final IAlbumWithTracksRepository albumWithTracksRepository;
    private final ISimpleTrackRepository simpleTrackRepository;
    private final ISimpleArtistRepository simpleArtistRepository;
    private final ISimpleGenreRepository simpleGenreRepository;
    private final IAlbumWithArtistsRepository albumWithArtistsRepository;
    private final IAlbumWithGenreRepository albumWithGenreRepository;

    @Autowired
    public AlbumServiceImpl(IAlbumRepository albumRepository, ISimpleAlbumRepository simpleAlbumRepository, IGenreWithAlbumsRepository genreWithAlbumsRepository, IArtistWithAlbumsRepository artistWithAlbumsRepository, IAlbumWithTracksRepository albumWithTracksRepository, ISimpleTrackRepository simpleTrackRepository, ISimpleArtistRepository simpleArtistRepository, ISimpleGenreRepository simpleGenreRepository, IAlbumWithArtistsRepository albumWithArtistsRepository, IAlbumWithGenreRepository albumWithGenreRepository) {
        this.albumRepository = albumRepository;
        this.simpleAlbumRepository = simpleAlbumRepository;
        this.genreWithAlbumsRepository = genreWithAlbumsRepository;
        this.artistWithAlbumsRepository = artistWithAlbumsRepository;
        this.albumWithTracksRepository = albumWithTracksRepository;
        this.simpleTrackRepository = simpleTrackRepository;
        this.simpleArtistRepository = simpleArtistRepository;
        this.simpleGenreRepository = simpleGenreRepository;
        this.albumWithArtistsRepository = albumWithArtistsRepository;
        this.albumWithGenreRepository = albumWithGenreRepository;
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
        return genreWithAlbumsRepository.findGenreAlbumsByNameContaining(name);
    }

    @Override
    public Optional<List<ArtistWithAlbums>> findSimpleAlbumsByArtistName(String name) {
        return artistWithAlbumsRepository.findArtistAlbumsByNameContaining(name);
    }

    @Override
    public Optional<SimpleTrack> findTrackById(Long id) {
        return simpleTrackRepository.findById(id);
    }

    @Override
    public Optional<AlbumWithTracks> findTrackByAlbumId(Long id) {
        return albumWithTracksRepository.findById(id);
    }

    @Override
    public AlbumWithTracks addTrackToAlbum(AlbumWithTracks track) {
        return albumWithTracksRepository.save(track);
    }

    @Override
    public Optional<SimpleArtist> findArtistById(Long id) {
        return simpleArtistRepository.findById(id);
    }

    @Override
    public Optional<AlbumWithArtists> findArtistByAlbumId(Long id) {
        return albumWithArtistsRepository.findById(id);
    }

    @Override
    public AlbumWithArtists addArtistToAlbum(AlbumWithArtists album) {
        return albumWithArtistsRepository.save(album);
    }

    @Override
    public Optional<AlbumWithGenres> findGenreByAlbumId(Long id) {
        return albumWithGenreRepository.findById(id);
    }

    @Override
    public Optional<SimpleGenre> findGenreById(Long id) {
        return simpleGenreRepository.findById(id);
    }

    @Override
    public AlbumWithGenres addGenreToAlbum(AlbumWithGenres album) {
        return albumWithGenreRepository.save(album);
    }
}
