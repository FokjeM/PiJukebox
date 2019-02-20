package com.pijukebox.service;

import com.pijukebox.model.artist.ArtistWithTracks;
import com.pijukebox.model.genre.GenreWithTracks;
import com.pijukebox.model.simple.SimpleTrack;
import com.pijukebox.model.track.Track;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ITrackService {

    ResponseEntity<List<Track>> findAllTracksWithDetails();

    ResponseEntity<Track> findTrackDetailsById(Long id);

    ResponseEntity<List<SimpleTrack>> findAllSimpleTrack();

    ResponseEntity<SimpleTrack> findSimpleTrackByName(String name);

    ResponseEntity<SimpleTrack> findSimpleTrackById(Long id);

    ResponseEntity<List<SimpleTrack>> findAllSimpleTrackByName(String name);

    ResponseEntity<List<Track>> findAllTracksByName(String name);

    ResponseEntity<List<ArtistWithTracks>> findAllTracksByArtistName(String name);

    ResponseEntity<List<GenreWithTracks>> findAllTracksByGenreName(String name);

    ResponseEntity<SimpleTrack> addSimpleTrack(SimpleTrack simpleTrack);

    ResponseEntity<ArtistWithTracks> addArtistToTrack(ArtistWithTracks artistWithTracks);

    ResponseEntity<GenreWithTracks> addGenreToTrack(GenreWithTracks genreWithTracks);

    ResponseEntity<GenreWithTracks> findTrackByGenreId(Long id);

    ResponseEntity<ArtistWithTracks> findTrackByArtistId(Long id);
}
