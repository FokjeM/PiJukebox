package com.pijukebox.service.impl;

import com.pijukebox.model.album.Album;
import com.pijukebox.model.artist.ArtistAlbum;
import com.pijukebox.model.genre.GenreAlbum;
import com.pijukebox.model.simple.SimpleAlbum;
import com.pijukebox.repository.IAlbumRepository;
import com.pijukebox.repository.IArtistAlbumRepository;
import com.pijukebox.repository.IGenreAlbumRepository;
import com.pijukebox.repository.ISimpleAlbumRepository;
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

    @Autowired
    public AlbumServiceImpl(IAlbumRepository albumRepository, ISimpleAlbumRepository simpleAlbumRepository, IGenreAlbumRepository genreAlbumRepository, IArtistAlbumRepository artistAlbumRepository) {
        this.albumRepository = albumRepository;
        this.simpleAlbumRepository = simpleAlbumRepository;
        this.genreAlbumRepository = genreAlbumRepository;
        this.artistAlbumRepository = artistAlbumRepository;
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
    public Optional<List<GenreAlbum>> findSimpleAlbumsByGenreName(String name) {
        return genreAlbumRepository.findGenreAlbumsByNameContaining(name);
    }

    @Override
    public Optional<List<ArtistAlbum>> findSimpleAlbumsByArtistName(String name) {
        return artistAlbumRepository.findArtistAlbumsByNameContaining(name);
    }
}
