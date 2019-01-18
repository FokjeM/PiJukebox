package com.pijukebox.controller;

import com.pijukebox.controller.player.StartPlayer;
import javafx.embed.swing.JFXPanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Couldn't play track"), ex);
        }
    }

    @GetMapping("/pause")
    public ResponseEntity<String> pauseCurrent()
    {
        try{
            sp.pause();
            return new ResponseEntity<>("Playing...", HttpStatus.OK);
        }catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Couldn't play track"), ex);
        }
    }

    @GetMapping("/stop")
    public ResponseEntity<String> stopCurrent()
    {
        try{
            sp.stop();
            return new ResponseEntity<>("Playing...", HttpStatus.OK);
        }catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Couldn't play track"), ex);
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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Couldn't play track"), ex);
        }
    }

    @GetMapping("/prev")
    public ResponseEntity<String> prevTrack()
    {
        try{
            sp.next();
            return new ResponseEntity<>("Playing...", HttpStatus.OK);
        }catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Couldn't play track"), ex);
        }
    }
}
