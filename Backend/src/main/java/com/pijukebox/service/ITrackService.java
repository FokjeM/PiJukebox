package com.pijukebox.service;

import com.pijukebox.model.artist.ArtistWithTracks;
import com.pijukebox.model.genre.GenreWithTracks;
import com.pijukebox.model.simple.SimpleTrack;
import com.pijukebox.model.track.Track;

import java.util.List;
import java.util.Optional;

public interface ITrackService {

    List<Track> findAllTracksWithDetails();

    Optional<Track> findTrackDetailsById(Long id);

    Optional<List<SimpleTrack>> findAllSimpleTrack();

    Optional<SimpleTrack> findSimpleTrackById(Long id);

    Optional<List<SimpleTrack>> findAllSimpleTrackByName(String name);

    Optional<List<Track>> findAllTracksByName(String name);

    Optional<List<ArtistWithTracks>> findAllTracksByArtistName(String name);

    Optional<List<GenreWithTracks>> findAllTracksByGenreName(String name);

    SimpleTrack addSimpleTrack(SimpleTrack simpleTrack);

    ArtistWithTracks addArtistToTrack(ArtistWithTracks artistWithTracks);

    GenreWithTracks addGenreToTrack(GenreWithTracks genreWithTracks);

    Optional<GenreWithTracks> findTrackByGenreId(Long id);

    Optional<ArtistWithTracks> findTrackByArtistId(Long id);
}
