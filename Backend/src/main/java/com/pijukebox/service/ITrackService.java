package com.pijukebox.service;

import com.pijukebox.model.artist.ArtistTrack;
import com.pijukebox.model.genre.GenreTrack;
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

    Optional<List<Track>> findAllTrackByName(String name);

    Optional<List<ArtistTrack>> findAllTracksByArtistName(String name);

    Optional<List<GenreTrack>> findAllTracksByGenreName(String name);

    SimpleTrack addSimpleTrack(SimpleTrack simpleTrack);

}
