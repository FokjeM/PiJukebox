package com.pijukebox.controller;

import com.google.protobuf.Int32Value;
import com.pijukebox.controller.player.StartPlayer;
import io.swagger.annotations.ApiOperation;
import javafx.embed.swing.JFXPanel;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.*;
import java.sql.ResultSet;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/v1/player")
public class PlayerController {

    private StartPlayer sp;
    public PlayerController()
    {

        JFrame frame = new JFrame("FX");
        JFXPanel fxPanel = new JFXPanel();
        frame.add(fxPanel);
        frame.setVisible(false);
        this.sp = new StartPlayer();
    }

    @GetMapping("/play")
    public ResponseEntity<String> playCurrent()
    {
        try{
            sp.play();
            return new ResponseEntity<>("Playing...", HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Couldn't play track /play"), ex);
        }
    }

    @GetMapping("/pause")
    public ResponseEntity<String> pauseCurrent()
    {
        try{
            sp.pause();
            return new ResponseEntity<>("Playing...", HttpStatus.OK);
        }catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Couldn't play track /pause"), ex);
        }
    }

    @GetMapping("/stop")
    public ResponseEntity<String> stopCurrent()
    {
        try{
            sp.stop();
            return new ResponseEntity<>("Playing...", HttpStatus.OK);
        }catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Couldn't play track /stop"), ex);
        }
    }

    @GetMapping("/next")
    public ResponseEntity<String> nextTrack()
    {
        try{
            sp.next();
            sp.play();
            return new ResponseEntity<>("Playing...", HttpStatus.OK);
        }catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Couldn't play track /next"), ex);
        }
    }

    @GetMapping("/prev")
    public ResponseEntity<String> prevTrack()
    {
        try{
            sp.prev();
            sp.play();
            return new ResponseEntity<>("Playing...", HttpStatus.OK);
        }catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Couldn't play track /prev"), ex);
        }
    }

    @GetMapping("/volume/{volumeLevel}")
    public ResponseEntity<String> volume(@PathVariable int volumeLevel) {
        try {
            sp.setVolume(volumeLevel);
            return new ResponseEntity<>("Volume is " + volumeLevel, HttpStatus.OK);
        }
        catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Couldn't change volume"), ex);
        }
    }

    @GetMapping(value = "/status", produces = "application/json")
    @ApiOperation(value = "Get player status")
    public String status() {
        Boolean playerStatus = sp.isPlaying();
        Double volumeLevel = sp.getVolumeLevel();
        Double intVol = volumeLevel * 10;
        int playerVolume = intVol.intValue();

        return String.format("{\"isPlaying\": %s, \"volumeLevel\": %d}", playerStatus, playerVolume);
    }
}
