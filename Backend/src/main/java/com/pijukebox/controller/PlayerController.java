package com.pijukebox.controller;

import com.pijukebox.model.playlist.PlaylistWithTracks;
import com.pijukebox.model.simple.SimpleTrack;
import com.pijukebox.model.track.Track;
import com.pijukebox.service.IPlayerService;
import com.pijukebox.service.IPlaylistService;
import com.pijukebox.service.ITrackService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/v1/player")
public class PlayerController {

    private final ITrackService trackService;
    private final IPlaylistService playlistService;
    private final IPlayerService playerService;

    /**
     * Instantiates a new Player controller
     *
     * @param trackService    the track service
     * @param playlistService the playlist service
     * @param playerService   the player service
     */
    @Autowired
    public PlayerController(ITrackService trackService, IPlaylistService playlistService, IPlayerService playerService) {
        this.trackService = trackService;
        this.playlistService = playlistService;
        this.playerService = playerService;
        playerService.setFolderPath(Paths.get(com.pijukebox.configuration.ApplicationInitializer.getMediaPath()));
    }

    /**
     * Play a song
     *
     * @param filename The filename of a song
     * @return HttpStatus.OK/HttpStatus.NOT_FOUND
     */
    @PostMapping("/play")
    public ResponseEntity<String> playCurrent(@RequestParam(name = "filename") String filename) {
        return playerService.playOneSong(filename);
    }

    /**
     * Play/Resume current song
     *
     * @return HttpStatus.OK/HttpStatus.NOT_FOUND
     */
    @PostMapping("/playCurrent")
    public ResponseEntity<String> playCurrent() {
        return playerService.playCurrentSong();
    }

    /**
     * Pause current song
     *
     * @return HttpStatus.OK/HttpStatus.NOT_FOUND
     */
    @PostMapping("/pause")
    public ResponseEntity<String> pauseCurrent() {
        return playerService.pauseCurrentSong();
    }

    /**
     * Stop current song
     *
     * @return HttpStatus.OK/HttpStatus.NOT_FOUND
     */
    @PostMapping("/stop")
    public ResponseEntity<String> stopCurrent() {
        return playerService.stopCurrentSong();
    }

    /**
     * Play next song in the queue
     *
     * @return HttpStatus.OK/HttpStatus.NOT_FOUND
     */
    @PostMapping("/next")
    public ResponseEntity<String> nextTrack() {
        return playerService.playNextSong();
    }

    /**
     * Play previous song in the queue
     *
     * @return HttpStatus.OK/HttpStatus.NOT_FOUND
     */
    @PostMapping("/prev")
    public ResponseEntity<String> prevTrack() {
        return playerService.playPreviousSong();
    }

    /**
     * Shuffle the queue
     *
     * @return HttpStatus.OK/HttpStatus.NOT_FOUND
     */
    @PostMapping("/shuffle")
    public ResponseEntity<String> toggleShuffle() {
        return playerService.toggleShuffleState();
    }

    /**
     * Repeat current song in the queue
     *
     * @return HttpStatus.OK/HttpStatus.NOT_FOUND
     */
    @PostMapping("/repeat")
    public ResponseEntity<String> toggleRepeat() {
        return playerService.toggleRepeatState();
    }

    /**
     * Add a song to the queue
     *
     * @param id the id
     * @return Details of the newly added song
     */
    @PostMapping("/add/{id}")
    public ResponseEntity<String> addTrack(@PathVariable Long id) {
        SimpleTrack track = trackService.findSimpleTrackById(id).getBody();
        return playerService.addSongToQueue(track.getFilename());
    }

    /**
     * Add an entire playlist to the queue
     *
     * @param id the ID of the playlist to add
     * @return HttpStatus.NO_CONTENT/HttpStatus.OK/HttpStatus.BAD_REQUEST
     */
    @PostMapping("/add/playlist/{id}")
    public ResponseEntity<String> addPlaylist(@PathVariable Long id) {
        try {
            PlaylistWithTracks playlist = playlistService.findById(id).getBody();
            return playerService.addPlaylistToQueue(playlist.getTracks());
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't add playlist", ex);
        }
    }

    /**
     * Remove a song from the queue
     *
     * @param id the id
     * @return HttpStatus.OK/HttpStatus.NOT_FOUND/HttpStatus.BAD_REQUEST
     */
    @PostMapping("/remove/{id}")
    public ResponseEntity<String> deleteTrack(@PathVariable Long id) {
        SimpleTrack track = trackService.findSimpleTrackById(id).getBody();
        return playerService.removeSongFromQueue(track.getFilename());
    }

    /**
     * Get current songs in the queue
     *
     * @return The current song
     */
    @GetMapping("/queue")
    public ResponseEntity<List<Track>> getQueue() {
        return playerService.getQueue();
    }

    /**
     * Get track details of the current song
     *
     * @return Track details of the current song
     */
    @GetMapping(value = "/trackDetails")
    @ApiOperation(value = "Get track details of the current song")
    public ResponseEntity<Map<String, String>> getTackDetails() {
        return playerService.getTrackDetails();
    }

    /**
     * Get player status
     *
     * @return Player status details
     */
    @GetMapping(value = "/status", produces = "application/json")
    @ApiOperation(value = "Get player status")
    public ResponseEntity<Map<String, Object>> getStatus() {
        return playerService.getPlayerStatus();
    }

    /**
     * Get current song
     *
     * @return The current song
     */
    @GetMapping("/current")
    public ResponseEntity<Track> getCurrent() {
        return playerService.getCurrentSong();
    }

    /**
     * Set volume level of player
     *
     * @param volumeLevel the volume level
     * @return The new volume level
     */
    @PostMapping("/volume/{volumeLevel}")
    public ResponseEntity<String> setVolume(@PathVariable int volumeLevel) {
        return playerService.setPlayerVolume(volumeLevel);
    }

    /**
     * Get volume level of player
     *
     * @return The current volume level
     */
    @GetMapping("/volume")
    public ResponseEntity<Map<String, Integer>> getVolume() {
        return playerService.getPlayerVolume();
    }

    /**
     * Clear player queue.
     *
     * @return HttpStatus.OK
     */
    @PostMapping("/queue/clear")
    public ResponseEntity<String> clearQueue() {
        return playerService.clearQueue(true);
    }
}
