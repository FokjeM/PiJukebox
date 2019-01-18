package com.pijukebox.controller.player;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class SongPlayer {
    private String status;
    private Media hit;
    private static MediaPlayer mediaPlayer;
    private String filePath;

    public SongPlayer(String filePath) {
        this.filePath = filePath;
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

    public void play() {
        mediaPlayer.play();
        status = "playing";
    }

    public void pause() {
        if (status.equals("paused")) {
            System.out.println("Song already paused");
            return;
        }
        mediaPlayer.pause();
        status = "paused";
    }

    public void stop() {
        try {
            mediaPlayer.stop();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void next(String filePath) {
        this.filePath = filePath;
        mediaPlayer.stop();
        resetAudioStream();
    }

    // Method to reset audio stream
    public void resetAudioStream() {
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
}