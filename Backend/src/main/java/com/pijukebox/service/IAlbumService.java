package com.pijukebox.service;

import com.pijukebox.model.album.Album;
import com.pijukebox.model.album.AlbumWithArtists;
import com.pijukebox.model.album.AlbumWithTracks;
import com.pijukebox.model.artist.ArtistAlbum;
import com.pijukebox.model.genre.GenreAlbum;
import com.pijukebox.model.simple.SimpleAlbum;
import com.pijukebox.model.simple.SimpleArtist;
import com.pijukebox.model.simple.SimpleTrack;

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

    Optional<List<ArtistAlbum>> findSimpleAlbumsByArtistName(String name);

    Optional<SimpleTrack> findTrackById(Long id);

    Optional<AlbumWithTracks> findTrackByAlbumId(Long id);

    AlbumWithTracks addTrackToAlbum(AlbumWithTracks track);

    Optional<SimpleArtist> findArtistById(Long id);

    Optional<AlbumWithArtists> findArtistByAlbumId(Long id);

    AlbumWithArtists addArtistToAlbum(AlbumWithArtists album);
}
