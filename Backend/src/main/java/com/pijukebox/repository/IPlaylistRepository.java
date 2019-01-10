package com.pijukebox.repository;

import com.pijukebox.model.Playlist;
import com.pijukebox.model.Track;

import java.util.Map;

public interface IPlaylistRepository {

    Map<Playlist, Track> getAllPlaylistTracks(Long id);
}
