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
        this.playerWrapper = new PlayerWrapper(Paths.get("D:\\Java minor\\Royalty Free Music\\"));

    }

    /**
     * Play a song
     *
     * @param filename The filename of a song
     * @return HttpStatus.OK/HttpStatus.NOT_FOUND
     */
    @GetMapping("/play")
    public ResponseEntity<String> playCurrent(@RequestParam(name = "filename") String filename) {
        try {
            playerWrapper.playOneSong(filename);
            return new ResponseEntity<>("Playing...", HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't play track! /play", ex);
        }
    }

    /**
     * Play/Resume current song
     *
     * @return HttpStatus.OK/HttpStatus.NOT_FOUND
     */
    @GetMapping("/playCurrent")
    public ResponseEntity<String> playCurrent() {
        try {
            playerWrapper.playCurrentSong();
            return new ResponseEntity<>("Playing...", HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't play track! /playCurrentSong", ex);
        }
    }

    /**
     * Pause current song
     *
     * @return HttpStatus.OK/HttpStatus.NOT_FOUND
     */
    @GetMapping("/pause")
    public ResponseEntity<String> pauseCurrent() {
        try {
            playerWrapper.pauseSong();
            return new ResponseEntity<>("Paused...", HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't play track /pause", ex);
        }
    }

    /**
     * Stop current song
     *
     * @return HttpStatus.OK/HttpStatus.NOT_FOUND
     */
    @GetMapping("/stop")
    public ResponseEntity<String> stopCurrent() {
        try {
            playerWrapper.stopSong();
            return new ResponseEntity<>("Stopped...", HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't play track /stop", ex);
        }
    }

    /**
     * Play next song in the queue
     *
     * @return HttpStatus.OK/HttpStatus.NOT_FOUND
     */
    @GetMapping("/next")
    public ResponseEntity<String> nextTrack() {
        try {
            playerWrapper.playNextSong();
            return new ResponseEntity<>("Next...", HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't play track /next", ex);
        }
    }

    /**
     * Play previous song in the queue
     *
     * @return HttpStatus.OK/HttpStatus.NOT_FOUND
     */
    @GetMapping("/prev")
    public ResponseEntity<String> prevTrack() {
        try {
            playerWrapper.playPreviousSong();
            return new ResponseEntity<>("Previous...", HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't play track /prev", ex);
        }
    }

    /**
     * Shuffle the queue
     *
     * @return HttpStatus.OK/HttpStatus.NOT_FOUND
     */
    @GetMapping("/shuffle")
    public ResponseEntity<String> toggleShuffle() {
        try {
            playerWrapper.toggleShuffleState();
            return new ResponseEntity<>("Shuffling...", HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't toggleShuffleState current queue", ex);
        }
    }

    /**
     * Repeat current song in the queue
     *
     * @return HttpStatus.OK/HttpStatus.NOT_FOUND
     */
    @GetMapping("/repeat")
    public ResponseEntity<String> toggleRepeat() {
        try {
            playerWrapper.toggleRepeatState();
            return new ResponseEntity<>("Changed toggleRepeatState state...", HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't change toggleRepeatState state", ex);
        }
    }

    /**
     * Add a song to the queue
     *
     * @return Details of the newly added song
     */
    @GetMapping("/add/{id}")
    public ResponseEntity<String> addTrack(@PathVariable Long id) {
        try {
            if (!trackService.findSimpleTrackById(id).isPresent()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            SimpleTrack track = trackService.findSimpleTrackById(id).get();
            playerWrapper.addSongToPlaylist(track.getFilename());
            return new ResponseEntity<>("Song added", HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't add song", ex);
        }
    }

    /**
     * Remove a song from the queue
     *
     * @return HttpStatus.OK/HttpStatus.NOT_FOUND/HttpStatus.BAD_REQUEST
     */
    @GetMapping("/remove/{id}")
    public ResponseEntity<String> deleteTrack(@PathVariable Long id) {
        try {
            if (!trackService.findSimpleTrackById(id).isPresent()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            SimpleTrack track = trackService.findSimpleTrackById(id).get();
            playerWrapper.removeSongFromPlaylist(track.getFilename());
            return new ResponseEntity<>("Song removed", HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't remove song", ex);
        }
    }

    /**
     * Get current songs in the queue
     *
     * @return The current song
     */
    @GetMapping("/queue")
    public ResponseEntity<Object> getQueue() {
        try {
            List<String> songs = playerWrapper.getQueue();
            List<Track> queue = new ArrayList<>();
            for (String song : songs) {
                String name = FilenameUtils.removeExtension(song);
                if (trackService.findAllTracksByName(name).isPresent()) {
                    queue.add(trackService.findAllTracksByName(name).get().get(0));
                }
            }
            return new ResponseEntity<>(queue, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't get queue", ex);
        }
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
        try {
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
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't get the current track or it doesn't exists", ex);
        }
    }

    /**
     * Set volume level of player
     *
     * @return The new volume level
     */
    @GetMapping("/volume/{volumeLevel}")
    public ResponseEntity<String> setVolume(@PathVariable int volumeLevel) {
        try {
            playerWrapper.setPlayerVolume((volumeLevel));
            return new ResponseEntity<>(String.format("Volume is %s", playerWrapper.getPlayerVolume()), HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't change volume", ex);
        }
    }

    /**
     * Get volume level of player
     *
     * @return The current volume level
     */
    @GetMapping("/volume")
    public ResponseEntity<String> getVolume() {
        try {
            return new ResponseEntity<>(String.format("Volume is %s", playerWrapper.getPlayerVolume()), HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't change volume", ex);
        }
    }

    @GetMapping("/queue/clear")
    public ResponseEntity<String> clearQueue() {
        try {
            playerWrapper.clearQueue(true);
            return new ResponseEntity<>("Queue cleared!", HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't clear queue", ex);
        }
    }
}
