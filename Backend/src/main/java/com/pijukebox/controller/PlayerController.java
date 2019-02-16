package com.pijukebox.controller;

import com.pijukebox.model.simple.SimpleTrack;
import com.pijukebox.model.track.Track;
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

import java.nio.file.Path;
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
     * Command: mvn install:install-file -Dfile='lib/jaco-mp3-player-0.10.2.jar' -DgroupId='jaco.mp3.player' -DartifactId=jacocontrol -Dversion='0.10.2' -Dpackaging=jar -DgeneratePom=false
     * */

    private static Path currentRelativePath = Paths.get("");
    private static Path songsDir = Paths.get(currentRelativePath.toAbsolutePath().toString(), "/songs");
    private final ITrackService trackService;
    private final PlayerWrapper playerWrapper;

    @Autowired
    public PlayerController(ITrackService trackService, IPlaylistService playlistService) {
        this.trackService = trackService;
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
     * @return Details of the newly added song
     */
    @GetMapping("/add/{id}")
    public ResponseEntity<String> addTrack(@PathVariable Long id) {
        if (!trackService.findSimpleTrackById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        SimpleTrack track = trackService.findSimpleTrackById(id).get();
        playerWrapper.addSongToPlaylist(track.getFilename());
        return new ResponseEntity<>("Song added", HttpStatus.OK);
    }

    /**
     * Remove a song from the queue
     *
     * @return HttpStatus.OK/HttpStatus.NOT_FOUND/HttpStatus.BAD_REQUEST
     */
    @GetMapping("/remove/{id}")
    public ResponseEntity<String> deleteTrack(@PathVariable Long id) {
        if (!trackService.findSimpleTrackById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        SimpleTrack track = trackService.findSimpleTrackById(id).get();
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
            if (trackService.findAllTracksByName(name).isPresent()) {
                queue.add(trackService.findAllTracksByName(name).get().get(0));
            }
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

        addMapValue(status, "title", playerWrapper.getCurrentSong());
        addMapValue(status, "artist", playerWrapper.getArtist());
        addMapValue(status, "genre", playerWrapper.getGenre());
        addMapValue(status, "album", playerWrapper.getAlbum());
        return status;
    }

    private void addMapValue(Map<String, String> map, String key, String value) {
        if (value != null && !value.isEmpty()) {
            map.put(key, value);
        }
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
            if (!trackService.findAllSimpleTrackByName(name).isPresent()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            SimpleTrack st = trackService.findAllSimpleTrackByName(name).get().get(0);

            if (!trackService.findTrackDetailsById(st.getId()).isPresent()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(trackService.findTrackDetailsById(st.getId()).get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
    }

    /**
     * Set volume level of player
     *
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

    @GetMapping("/queue/clear")
    public ResponseEntity<String> clearQueue() {
        playerWrapper.clearQueue(true);
        return new ResponseEntity<>("Queue cleared!", HttpStatus.OK);
    }
}
