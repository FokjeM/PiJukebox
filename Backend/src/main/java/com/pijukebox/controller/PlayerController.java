package com.pijukebox.controller;

import com.pijukebox.model.playlist.PlaylistWithTracks;
import com.pijukebox.model.simple.SimpleTrack;
import com.pijukebox.model.track.Track;
import com.pijukebox.model.playlist.PlaylistWithTracks;
import com.pijukebox.player.PlayerWrapper;
import com.pijukebox.service.IPlaylistService;
import com.pijukebox.service.ITrackService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/v1/player")
public class PlayerController {

    /*
     * Command: mvn install:install-file -Dfile=lib/jaco-mp3-player-0.10.2.jar -DgroupId=jaco.mp3.player -DartifactId=jacocontrol -Dversion=0.10.2 -Dpackaging=jar -DgeneratePom=false
     * */

    private final ITrackService trackService;
    private final IPlaylistService playlistService;
    private final PlayerWrapper playerWrapper;

    /**
     * Instantiates a new Player controller.
     *
     * @param trackService the track service
     */
    @Autowired
    public PlayerController(ITrackService trackService, IPlaylistService playlistService) {
        this.trackService = trackService;
        this.playlistService = playlistService;
//        this.playerWrapper = new PlayerWrapper(Paths.get("/media/music/"));
        this.playerWrapper = new PlayerWrapper(Paths.get("C:\\Users\\Public\\Music\\"));
    }

    /**
     * Play a song
     *
     * @param filename The filename of a song
     * @return HttpStatus.OK/HttpStatus.NOT_FOUND
     */
    @GetMapping("/play")
    public ResponseEntity<String> playCurrent(@RequestParam(name = "filename") String filename) {
        playerWrapper.playOneSong(filename);
        return new ResponseEntity<>("Playing...", HttpStatus.OK);
    }

    /**
     * Play/Resume current song
     *
     * @return HttpStatus.OK/HttpStatus.NOT_FOUND
     */
    @GetMapping("/playCurrent")
    public ResponseEntity<String> playCurrent() {
        playerWrapper.playCurrentSong();
        return new ResponseEntity<>("Playing...", HttpStatus.OK);
    }

    /**
     * Pause current song
     *
     * @return HttpStatus.OK/HttpStatus.NOT_FOUND
     */
    @GetMapping("/pause")
    public ResponseEntity<String> pauseCurrent() {
        playerWrapper.pauseSong();
        return new ResponseEntity<>("Paused...", HttpStatus.OK);
    }

    /**
     * Stop current song
     *
     * @return HttpStatus.OK/HttpStatus.NOT_FOUND
     */
    @GetMapping("/stop")
    public ResponseEntity<String> stopCurrent() {
        playerWrapper.stopSong();
        return new ResponseEntity<>("Stopped...", HttpStatus.OK);
    }

    /**
     * Play next song in the queue
     *
     * @return HttpStatus.OK/HttpStatus.NOT_FOUND
     */
    @GetMapping("/next")
    public ResponseEntity<String> nextTrack() {
        playerWrapper.playNextSong();
        return new ResponseEntity<>("Next...", HttpStatus.OK);
    }

    /**
     * Play previous song in the queue
     *
     * @return HttpStatus.OK/HttpStatus.NOT_FOUND
     */
    @GetMapping("/prev")
    public ResponseEntity<String> prevTrack() {
        playerWrapper.playPreviousSong();
        return new ResponseEntity<>("Previous...", HttpStatus.OK);
    }

    /**
     * Shuffle the queue
     *
     * @return HttpStatus.OK/HttpStatus.NOT_FOUND
     */
    @GetMapping("/shuffle")
    public ResponseEntity<String> toggleShuffle() {
        playerWrapper.toggleShuffleState();
        return new ResponseEntity<>("Shuffling...", HttpStatus.OK);
    }

    /**
     * Repeat current song in the queue
     *
     * @return HttpStatus.OK/HttpStatus.NOT_FOUND
     */
    @GetMapping("/repeat")
    public ResponseEntity<String> toggleRepeat() {
        playerWrapper.toggleRepeatState();
        return new ResponseEntity<>("Changed toggleRepeatState state...", HttpStatus.OK);
    }

    /**
     * Add a song to the queue
     *
     * @param id the id
     * @return Details of the newly added song
     */
    @GetMapping("/add/{id}")
    public ResponseEntity<String> addTrack(@PathVariable Long id) {
        SimpleTrack track = trackService.findSimpleTrackById(id).getBody();
        playerWrapper.addSongToPlaylist(track.getFilename());
        return new ResponseEntity<>("Song added", HttpStatus.OK);
    }

    /**
     * Add an entire playlist to the queue
     *
     * @param id the ID of the playlist to add
     * @return HttpStatus.NO_CONTENT/HttpStatus.OK/HttpStatus.BAD_REQUEST
     */
    @GetMapping("/add/playlist/{id}")
    public ResponseEntity<String> addPlaylist(@PathVariable Long id) {
        try {
            PlaylistWithTracks playlist = playlistService.findById(id).getBody();
            for (SimpleTrack track : playlist.getTracks()) {
                playerWrapper.addSongToPlaylist(track.getFilename());
            }
            return new ResponseEntity<>("Playlist added!", HttpStatus.OK);
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
    @GetMapping("/remove/{id}")
    public ResponseEntity<String> deleteTrack(@PathVariable Long id) {
        SimpleTrack track = trackService.findSimpleTrackById(id).getBody();
        playerWrapper.removeSongFromPlaylist(track.getFilename());
        return new ResponseEntity<>("Song removed", HttpStatus.OK);
    }

    /**
     * Get current songs in the queue
     *
     * @return The current song
     */
    @GetMapping("/queue")
    public ResponseEntity<Object> getQueue() {
        List<String> songs = playerWrapper.getQueue();
        List<Track> queue = new ArrayList<>();
        for (String song : songs) {
            String name = FilenameUtils.removeExtension(song);
            queue.add(trackService.findAllTracksByName(name).getBody().get(0));
        }
        return new ResponseEntity<>(queue, HttpStatus.OK);
    }

    /**
     * Get track details of the current song
     *
     * @return Track details of the current song
     */
    @GetMapping(value = "/trackDetails")
    @ApiOperation(value = "Get track details of the current song")
    public Map<String, String> getTackDetails() {
        Map<String, String> status = new HashMap<>();

        status.put("title", playerWrapper.getCurrentSong());
        status.put("artist", playerWrapper.getArtist());
        status.put("genre", playerWrapper.getGenre());
        status.put("album", playerWrapper.getAlbum());
        return status;
    }

    /**
     * Get player status
     *
     * @return Player status details
     */
    @GetMapping(value = "/status", produces = "application/json")
    @ApiOperation(value = "Get player status")
    public String getStatus() {
        boolean isPlaying = false;
        if (playerWrapper.getPlayerStatus().equals("PLAYING")) {
            isPlaying = true;
        }
        return String.format("{\"isPlaying\": %s, \"volumeLevel\": %d, \"repeatState\": %b}", isPlaying, playerWrapper.getPlayerVolume(), playerWrapper.getRepeatState());
    }

    /**
     * Get current song
     *
     * @return The current song
     */
    @GetMapping("/current")
    public ResponseEntity<Track> getCurrent() {
        if (!playerWrapper.getQueue().isEmpty()) {

            String name = FilenameUtils.removeExtension(playerWrapper.getCurrentSong());
            SimpleTrack st = trackService.findAllSimpleTrackByName(name).getBody().get(0);
            return new ResponseEntity<>(trackService.findTrackDetailsById(st.getId()).getBody(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
    }

    /**
     * Set volume level of player
     *
     * @param volumeLevel the volume level
     * @return The new volume level
     */
    @GetMapping("/volume/{volumeLevel}")
    public ResponseEntity<String> setVolume(@PathVariable int volumeLevel) {
        playerWrapper.setPlayerVolume((volumeLevel));
        return new ResponseEntity<>(String.format("Volume is %s", playerWrapper.getPlayerVolume()), HttpStatus.OK);
    }

    /**
     * Get volume level of player
     *
     * @return The current volume level
     */
    @GetMapping("/volume")
    public ResponseEntity<String> getVolume() {
        return new ResponseEntity<>(String.format("Volume is %s", playerWrapper.getPlayerVolume()), HttpStatus.OK);
    }

    /**
     * Clear player queue.
     *
     * @return HttpStatus.OK
     */
    @GetMapping("/queue/clear")
    public ResponseEntity<String> clearQueue() {
        playerWrapper.clearQueue(true);
        return new ResponseEntity<>("Queue cleared!", HttpStatus.OK);
    }
}
