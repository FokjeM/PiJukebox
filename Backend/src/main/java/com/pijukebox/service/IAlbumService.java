package com.pijukebox.service;

import com.pijukebox.model.album.Album;
import com.pijukebox.model.genre.GenreAlbum;
import com.pijukebox.model.simple.SimpleAlbum;

import java.util.List;
import java.util.Optional;

public interface IAlbumService {

    List<Album> findAllExtendedAlbums();

    Optional<Album> findExtendedAlbumById(Long id);

    Optional<List<Album>> findAlbumsByNameContaining(String name);


    List<SimpleAlbum> findAllSimpleAlbums();

    Optional<SimpleAlbum> findSimpleAlbumById(Long id);

    Optional<List<SimpleAlbum>> findSimpleAlbumsByNameContaining(String name);

    Optional<List<GenreAlbum>> findSimpleAlbumsByGenreName(String name);
}
