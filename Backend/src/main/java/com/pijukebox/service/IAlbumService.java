package com.pijukebox.service;

import com.pijukebox.model.album.Album;
import com.pijukebox.model.album.AlbumWithArtists;
import com.pijukebox.model.album.AlbumWithGenres;
import com.pijukebox.model.album.AlbumWithTracks;
import com.pijukebox.model.simple.SimpleAlbum;
import com.pijukebox.model.simple.SimpleArtist;
import com.pijukebox.model.simple.SimpleGenre;
import com.pijukebox.model.simple.SimpleTrack;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IAlbumService {

    ResponseEntity<List<Album>> findAllExtendedAlbums();

    ResponseEntity<Album> findExtendedAlbumById(Long id);

    ResponseEntity<List<Album>> findAlbumsByNameContaining(String name);

    ResponseEntity<List<SimpleAlbum>> findAllSimpleAlbums();

    ResponseEntity<SimpleAlbum> findSimpleAlbumById(Long id);

    ResponseEntity<List<SimpleAlbum>> findSimpleAlbumsByNameContaining(String name);

    ResponseEntity<List<AlbumWithArtists>> findAlbumWithArtistsByNameContaining(String name);

    ResponseEntity<List<AlbumWithGenres>> findAlbumWithGenresByNameContaining(String name);

    ResponseEntity<List<AlbumWithTracks>> findAlbumWithTracksByNameContaining(String name);

    ResponseEntity<SimpleTrack> findTrackById(Long id);

    ResponseEntity<AlbumWithTracks> findTrackByAlbumId(Long id);

    ResponseEntity<AlbumWithTracks> addTrackToAlbum(AlbumWithTracks track);

    ResponseEntity<SimpleArtist> findArtistById(Long id);

    ResponseEntity<AlbumWithArtists> findArtistByAlbumId(Long id);

    ResponseEntity<AlbumWithArtists> addArtistToAlbum(AlbumWithArtists album);

    ResponseEntity<AlbumWithGenres> findGenreByAlbumId(Long id);

    ResponseEntity<SimpleGenre> findGenreById(Long id);

    ResponseEntity<AlbumWithGenres> addGenreToAlbum(AlbumWithGenres album);
}
