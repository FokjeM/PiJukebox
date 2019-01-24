package com.pijukebox.controller;

import com.google.protobuf.Int32Value;
import com.pijukebox.controller.player.StartPlayer;
import com.pijukebox.model.simple.SimpleTrack;
import com.pijukebox.model.track.Track;
import com.pijukebox.model.playlist.PlaylistWithTracks;
import com.pijukebox.service.ITrackService;
import com.pijukebox.service.IPlaylistService;
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
import java.util.ArrayList;
import java.util.Optional;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/v1/player")
public class PlayerController {


    private StartPlayer sp;
    private final ITrackService trackService;
    private final IPlaylistService playlistService;

    @Autowired
    public PlayerController(ITrackService trackService, IPlaylistService playlistService)
    {
        this.trackService = trackService;
        this.playlistService = playlistService;
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
            return new ResponseEntity<>("Play...", HttpStatus.OK);
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
            return new ResponseEntity<>("Pause...", HttpStatus.OK);
        }catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Couldn't play track /pause"), ex);
        }
    }

    @GetMapping("/stop")
    public ResponseEntity<String> stopCurrent()
    {
        try{
            sp.stop();
            return new ResponseEntity<>("Stop...", HttpStatus.OK);
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
            return new ResponseEntity<>("Next...", HttpStatus.OK);
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

    @GetMapping("/shuffle")
    public ResponseEntity<String> shuffle()
    {
        try{
            sp.shuffle();
            return new ResponseEntity<>("Shuffling...", HttpStatus.OK);
        }
        catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Couldn't shuffle current queue"), ex);
        }
    }

    @GetMapping("/repeat")
    public ResponseEntity<String> repeat()
    {
        try{
            sp.repeat();
            return new ResponseEntity<>("Changed repeat state...", HttpStatus.OK);
        }
        catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Couldn't change repeat state"), ex);
        }
    }

    @GetMapping("/add/{id}")
    public ResponseEntity<SimpleTrack> addTrack(@PathVariable Long id)
    {
        try{
            System.out.println(id);
            if(!trackService.findSimpleTrackById(id).isPresent())
            {
                System.out.println("Not found with id: " + id);
                return new ResponseEntity<>( HttpStatus.NOT_FOUND);
            }
            SimpleTrack track = trackService.findSimpleTrackById(id).get();
            System.out.println("Not found with id: " + id);
            sp.addSong(track);
            return new ResponseEntity<>(track, HttpStatus.OK);
        }
        catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Couldn't add song"), ex);
        }
    }

    @GetMapping("/remove/{id}")
    public ResponseEntity<String> deleteTrack(@PathVariable Long id)
    {
        try{
            sp.deleteSong(id);
            return new ResponseEntity<>("Song removed", HttpStatus.OK);
        }
        catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Couldn't remove song"), ex);
        }
    }

    @GetMapping("/add/playlist/{id}")
    public ResponseEntity<String> addPlaylistToQueue(@PathVariable Long id) {

        try {
            Optional<PlaylistWithTracks> playlist = playlistService.findById(id);
            if(playlist.isPresent()) {
                sp.addPlaylistToQueue(playlist.get().getTracks());
                return new ResponseEntity<>("Playlist added: " , HttpStatus.OK);
            }
            return new ResponseEntity<>("Playlist not found!",  HttpStatus.NOT_FOUND);
        }
        catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Couldn't add Playlist to Queue"), ex);
        }

    }

    @GetMapping("/queue")
    public ResponseEntity<ArrayList<SimpleTrack>> getQueue()
    {
        try{
            return new ResponseEntity<>(sp.getQueue(), HttpStatus.OK);
        }
        catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Couldn't get queue"), ex);
        }
    }

    @GetMapping("/queue/clear")
    public ResponseEntity<String> clearQueue()
    {
        try {
            sp.clearQueue();
            return new ResponseEntity<>("queue cleared" , HttpStatus.OK);
        }
        catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, String.format("Couldn't clear the queue"), ex);
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

    @GetMapping("/current")
    public ResponseEntity<Track> current()
    {
        try {
            SimpleTrack st = sp.getCurrent();
            if(!trackService.findTrackDetailsById(st.getId()).isPresent())
            {
                return new ResponseEntity<>( HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(trackService.findTrackDetailsById(st.getId()).get(), HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Couldn't get the current track or it doesn't exists"), ex);
        }
    }

    @GetMapping("/move/track/up/{index}")
    public ResponseEntity<String> moveQueueItemUp(@PathVariable int index)
    {
        try{
            sp.moveSongUp(index);
            return new ResponseEntity<>("track moved up ", HttpStatus.OK);
        }
        catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, String.format("track can't be moved up"), ex);
        }

    }

    @GetMapping("/move/track/down/{index}")
    public ResponseEntity<String> moveQueueItemDown(@PathVariable int index)
    {
        try{
            sp.moveSongDown(index);
            return new ResponseEntity<>("track moved up ", HttpStatus.OK);
        }
        catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, String.format("track can't be moved down"), ex);
        }

    }

    @GetMapping(value = "/status", produces = "application/json")
    @ApiOperation(value = "Get player status")
    public String status() {
        Boolean playerStatus = sp.isPlaying();
        Double volumeLevel = sp.getVolumeLevel();
        Double intVol = volumeLevel * 10;
        int playerVolume = intVol.intValue();
        Boolean repeatState = sp.getRepeatState();

        return String.format("{\"isPlaying\": %s, \"volumeLevel\": %d, \"repeatState\": %b}", playerStatus, playerVolume, repeatState);
    }
}
