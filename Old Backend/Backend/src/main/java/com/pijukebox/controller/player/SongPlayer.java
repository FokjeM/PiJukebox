package com.pijukebox.controller.player;

import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import java.io.File;
import java.io.FileInputStream;

public class SongPlayer{

    private AdvancedPlayer mediaPlayer;
    private String filePath;
    private int pausedOnFrame;
    private String status;
    public SongPlayer() {
        status = "";
        pausedOnFrame = 0;
    }

    public void play() throws Exception
    {
        System.out.println(pausedOnFrame + "  ZZZZXXXXYY");
        mediaPlayer.play(pausedOnFrame, Integer.MAX_VALUE);
        status = "playing";
    }
    public void pause() throws Exception {
        if (status.equals("paused")) {
            System.out.println("Song already paused");
            return;
        }
        mediaPlayer.stop();
        status = "paused";
    }

    public void stop() throws Exception{
        try {

            mediaPlayer.stop();
            status = "stop";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void next(String filePath) throws Exception{
        this.filePath = filePath;
        resetAudioStream();
        mediaPlayer.stop();
        pausedOnFrame = 0;
        resetAudioStream();
    }

    // Method to reset audio stream
    public void resetAudioStream() {
        try {
            File fis = new File(filePath);
            if (fis.exists()) {
                FileInputStream f = new FileInputStream(filePath);
                mediaPlayer = new AdvancedPlayer(f);
                mediaPlayer.setPlayBackListener(new PlaybackListener() {
                    @Override
                    public void playbackFinished(PlaybackEvent event) {
                        pausedOnFrame = event.getFrame();
                        System.out.println("AKDNHKSJBJXZJCBJVCHLXKASJDBNSLJD " + pausedOnFrame);
                    }
                });
            } else {
                System.out.println("zasadbasdibgdusa");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
   /* private String status;
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
                System.out.println("abcdefgzzz");
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
    }*/
}