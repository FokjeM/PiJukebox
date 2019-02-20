package com.pijukebox.service.impl;

import com.pijukebox.model.simple.SimpleTrack;
import com.pijukebox.model.track.Track;
import com.pijukebox.player.PlayerStatus;
import com.pijukebox.player.PlayerWrapper;
import com.pijukebox.repository.ITrackRepository;
import com.pijukebox.service.IPlayerService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Path;
import java.util.*;

@SuppressWarnings("OptionalGetWithoutIsPresent")
@Service
@Transactional
public class PlayerServiceImpl implements IPlayerService {

    private PlayerWrapper playerWrapper;
    private ITrackRepository trackRepository;

    @Autowired
    public PlayerServiceImpl(PlayerWrapper playerWrapper, ITrackRepository trackRepository) {
        this.playerWrapper = playerWrapper;
        this.trackRepository = trackRepository;
    }

    @Override
    public ResponseEntity<String> playOneSong(String fileName) {
        playerWrapper.playOneSong(fileName);
        return new ResponseEntity<>(String.format("The song: '%s' is playing", fileName), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> playCurrentSong() {
        playerWrapper.playCurrentSong();
        return new ResponseEntity<>("Playing current song...", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> playNextSong() {
        playerWrapper.playNextSong();
        return new ResponseEntity<>("Next song playing...", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> playPreviousSong() {
        playerWrapper.playPreviousSong();
        return new ResponseEntity<>("Playing previous song", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> pauseCurrentSong() {
        playerWrapper.pauseSong();
        return new ResponseEntity<>("Paused current song", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> stopCurrentSong() {
        playerWrapper.stopSong();
        return new ResponseEntity<>("Stopped current song", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> addSongToQueue(String fileName) {
        playerWrapper.addSongToPlaylist(fileName);
        return new ResponseEntity<>(String.format("The song: '%s' has been added to the queue", fileName), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> removeSongFromQueue(String fileName) {
        playerWrapper.addSongToPlaylist(fileName);
        return new ResponseEntity<>(String.format("The song: '%s' has been removed to the queue", fileName), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getPlayerStatus() {
        System.out.println("The status is: " + playerWrapper.getPlayerStatus());

        Map<String, Object> status = new HashMap<>();
        status.put("isPlaying", playerWrapper.getPlayerStatus());
        status.put("volumeLevel", playerWrapper.getPlayerVolume());
        status.put("repeatState", playerWrapper.getRepeatState());
        status.put("shuffleState", playerWrapper.getShuffleState());
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Integer>> getPlayerVolume() {
        Map<String, Integer> volume = new HashMap<>();
        volume.put("volume", playerWrapper.getPlayerVolume());
        return new ResponseEntity<>(volume, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> setPlayerVolume(int volume) {
        playerWrapper.setPlayerVolume(volume);
        return new ResponseEntity<>(String.format("The volume has been set to: %d", volume), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> toggleShuffleState() {
        playerWrapper.toggleShuffleState();
        return new ResponseEntity<>("Shuffle has been toggled", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> toggleRepeatState() {
        playerWrapper.toggleRepeatState();
        return new ResponseEntity<>(String.format("The volume has been set to: %s", playerWrapper.getRepeatState()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Track> getCurrentSong() {
        if (!playerWrapper.getQueue().isEmpty()) {

            String name = FilenameUtils.removeExtension(playerWrapper.getCurrentSong());
            return new ResponseEntity<>(trackRepository.findTracksByNameContaining(name).get().get(0), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
    }

    @Override
    public ResponseEntity<String> clearQueue(boolean stopCurrentSong) {
        playerWrapper.clearQueue(stopCurrentSong);
        return new ResponseEntity<>("The queue has been clear", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Track>> getQueue() {
        List<String> songs = playerWrapper.getPlayerQueue();
        List<Track> queue = new ArrayList<>();
        for (String song : songs) {
            String name = FilenameUtils.removeExtension(song);
            Optional<List<Track>> track = trackRepository.findTracksByNameContaining(name);
            // TODO Check if statement below works
            queue.add(track.get().get(0));
        }
        return new ResponseEntity<>(queue, HttpStatus.OK);
    }

    @Override
    public void setFolderPath(Path path) {
        playerWrapper.setMusicPath(path);
    }

    @Override
    public ResponseEntity<String> addPlaylistToQueue(Set<SimpleTrack> tracks) {
        for (SimpleTrack track : tracks) {
            playerWrapper.addSongToPlaylist(track.getFilename());
        }
        return new ResponseEntity<>("Playlist added", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, String>> getTrackDetails() {
        Map<String, String> status = new HashMap<>();

        status.put("title", playerWrapper.getCurrentSong());
        status.put("artist", playerWrapper.getArtist());
        status.put("genre", playerWrapper.getGenre());
        status.put("album", playerWrapper.getAlbum());
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}
