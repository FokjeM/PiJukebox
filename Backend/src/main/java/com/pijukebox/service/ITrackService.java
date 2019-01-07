package com.pijukebox.service;

import com.pijukebox.model.Album;
import com.pijukebox.model.Genre;
import com.pijukebox.model.Track;

import java.util.List;

public interface ITrackService {

    List<Track> findAll();

    List<Track> findAll(Long limit);

    Track findById(Long id);

    List<Album> findAlbums();

    List<Genre> findGenres();
}
