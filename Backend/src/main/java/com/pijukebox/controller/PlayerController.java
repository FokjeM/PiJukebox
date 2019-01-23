package com.pijukebox.controller;

import com.pijukebox.player.Player;
import com.pijukebox.player.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLDecoder;

@RestController
@RequestMapping("/api/v1/player")
public class PlayerController {

    @Autowired
    public PlayerController() {
    }

    @GetMapping("/play/{path}")
    public void playSong(@PathVariable String path) {
        try {
            Player player = new Player();
            Track track = new Track(URLDecoder.decode(path));
            player.playTrack(track);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
