package com.pijukebox.service;

import com.pijukebox.model.simple.SimpleTrack;
import com.pijukebox.model.track.Track;
import org.springframework.http.ResponseEntity;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IPlayerService {

    ResponseEntity<String> playOneSong(String fileName);
    ResponseEntity<String> playCurrentSong();
    ResponseEntity<String> playNextSong();
    ResponseEntity<String> playPreviousSong();
    ResponseEntity<String> pauseCurrentSong();
    ResponseEntity<String> stopCurrentSong();
    ResponseEntity<String> addSongToQueue(String filename);
    ResponseEntity<String> removeSongFromQueue(String filename);
    ResponseEntity<Map<String, String>> getPlayerStatus();
    ResponseEntity<Map<String, Integer>> getPlayerVolume();
    ResponseEntity<String> setPlayerVolume(int volume);
    ResponseEntity<Map<String, Boolean>> getRepeatState();
    ResponseEntity<String> toggleRepeatState();
    ResponseEntity<Track> getCurrentSong();
    ResponseEntity<String> clearQueue(boolean stopCurrentSong);
    ResponseEntity<List<Track>> getQueue();
    void setFolderPath(Path path);
    ResponseEntity<String> addPlaylistToQueue(Set<SimpleTrack> tracks);
    ResponseEntity<Map<String, String>> getTrackDetails();

}
