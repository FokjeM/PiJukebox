package com.pijukebox.controller;

import com.pijukebox.PlayerWrapper;
import com.pijukebox.model.simple.SimpleTrack;
import com.pijukebox.model.track.Track;
import com.pijukebox.service.ITrackService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/v1/player")
public class PlayerController {

    /*
     * Command: mvn install:install-file -Dfile='lib/jaco-mp3-player-0.9.4.jar' -DgroupId='jaco.mp3.player' -DartifactId=jacocontrol -Dversion='0.9.4' -Dpackaging=jar -DgeneratePom=false
     * */

    private static Path currentRelativePath = Paths.get("");
    private static Path songsDir = Paths.get(currentRelativePath.toAbsolutePath().toString(), "/songs");
    private final ITrackService trackService;
    private final PlayerWrapper playerWrapper;

    @Autowired
    public PlayerController(ITrackService trackService) {
        this.trackService = trackService;
        this.playerWrapper = new PlayerWrapper(Paths.get("/media/music/"));
    }

    @GetMapping("/play")
    public ResponseEntity<String> playCurrent(@RequestParam(name = "filename") String filename) {
        try {
            playerWrapper.playOne(filename);
            return new ResponseEntity<>("Playing...", HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't play track! /play", ex);
        }
    }

    @GetMapping("/pause")
    public ResponseEntity<String> pauseCurrent() {
        try {
            playerWrapper.pauseSong();
            return new ResponseEntity<>("Paused...", HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't play track /pause", ex);
        }
    }

    @GetMapping("/stop")
    public ResponseEntity<String> stopCurrent() {
        try {
            playerWrapper.stopSong();
            return new ResponseEntity<>("Stopped...", HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't play track /stop", ex);
        }
    }

    @GetMapping("/next")
    public ResponseEntity<String> nextTrack() {
        try {
            playerWrapper.playNext();
            return new ResponseEntity<>("Next...", HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't play track /next", ex);
        }
    }

    @GetMapping("/prev")
    public ResponseEntity<String> prevTrack() {
        try {
            playerWrapper.playPrev();
            return new ResponseEntity<>("Previous...", HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't play track /prev", ex);
        }
    }

    @GetMapping("/shuffle")
    public ResponseEntity<String> shuffle() {
        try {
            playerWrapper.shuffle();
            return new ResponseEntity<>("Shuffling...", HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't shuffle current queue", ex);
        }
    }

    @GetMapping("/repeat")
    public ResponseEntity<String> repeat() {
        try {
            playerWrapper.toggleRepeat();
            return new ResponseEntity<>("Changed toggleRepeat state...", HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't change toggleRepeat state", ex);
        }
    }

//    @GetMapping("/add/{id}")
//    public ResponseEntity<SimpleTrack> addTrack(@PathVariable Long id) {
//        try {
//            System.out.println(id);
//            if (!trackService.findSimpleTrackById(id).isPresent()) {
//                System.out.println("Not found with id: " + id);
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            }
//            SimpleTrack track = trackService.findSimpleTrackById(id).get();
//            System.out.println("Not found with id: " + id);
//            playerWrapper.addSong(track);
//            return new ResponseEntity<>(track, HttpStatus.OK);
//        } catch (Exception ex) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't add song", ex);
//        }
//    }
//
//    @GetMapping("/remove/{id}")
//    public ResponseEntity<String> deleteTrack(@PathVariable Long id) {
//        try {
//            playerWrapper.deleteSong(id);
//            return new ResponseEntity<>("Song removed", HttpStatus.OK);
//        } catch (Exception ex) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't remove song", ex);
//        }
//    }
//
//    @GetMapping("/queue")
//    public ResponseEntity<ArrayList<SimpleTrack>> getQueue() {
//        try {
//            return new ResponseEntity<>(playerWrapper.getQueue(), HttpStatus.OK);
//        } catch (Exception ex) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't get queue", ex);
//        }
//    }
//
//    @GetMapping("/volume/{volumeLevel}")
//    public ResponseEntity<String> volume(@PathVariable int volumeLevel) {
//        try {
//            playerWrapper.setVolume(volumeLevel);
//            return new ResponseEntity<>("Volume is " + volumeLevel, HttpStatus.OK);
//        } catch (Exception ex) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't change volume", ex);
//        }
//    }

    @GetMapping("/current")
    public ResponseEntity<Track> current() {
        try {
            if (!trackService.findAllSimpleTrackByName(playerWrapper.getCurrentSong()).isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            SimpleTrack st = trackService.findAllSimpleTrackByName(playerWrapper.getCurrentSong()).get().get(0);
            if (!trackService.findTrackDetailsById(st.getId()).isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(trackService.findTrackDetailsById(st.getId()).get(), HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't get the current track or it doesn't exists", ex);
        }
    }

    @GetMapping(value = "/status", produces = "application/json")
    @ApiOperation(value = "Get player getStatus")
    public Map<String, String> status() {
        Map<String, String> status = new HashMap<>();
        status.put("playerStatus", playerWrapper.getStatus());
        status.put("repeatState", playerWrapper.getRepeat().toString());
        return status;
    }
}
