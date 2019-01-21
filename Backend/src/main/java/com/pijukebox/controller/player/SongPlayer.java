package com.pijukebox.controller.player;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class SongPlayer{
    private String status = "stopped";
    private double volume = 0.5;
    private Media hit;
    private static MediaPlayer mediaPlayer;
    private String filePath;

    public SongPlayer() {

    }

    public void play() throws Exception{
        mediaPlayer.play();
        status = "playing";
    }

    public void pause() throws Exception{
        if (status.equals("paused")) {
            System.out.println("Song already paused");
            return;
        }
        mediaPlayer.pause();
        status = "paused";
    }

    public void stop() throws Exception{
        try {
            mediaPlayer.stop();
            status = "stopped";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void next(String filePath) throws Exception{
        this.filePath = filePath;
        mediaPlayer.stop();
        resetAudioStream();
    }

    // Method to reset audio stream
    public void resetAudioStream() throws Exception{
        try {
            File f = new File(filePath);
            if (f.exists()) {
                hit = new Media(f.toURI().toString());
                mediaPlayer = new MediaPlayer(hit);
            } else {
                System.out.println("Here");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Boolean isPlaying() {
        if (status.equals("playing")) {
            return true;
        } else if (status.equals("stopped") || status.equals("paused")) {
            return false;
        }
        return false;
    }

    public void setVolumeLevel(double volumeLevel) {
        mediaPlayer.setVolume(volumeLevel);
        this.volume = volumeLevel;
    }

    public double volumeLevel() {
        return volume;
    }
}