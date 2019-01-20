package com.pijukebox.controller.player;

import com.pijukebox.model.simple.SimpleTrack;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class StartPlayer{

    private SongPlayer sp;
    private ArrayList<SimpleTrack> songs;
    private int current = 0;
    // private final String pathToSong ="C:\\Users\\codru\\Desktop\\School\\Year 4\\Minor Java Assignments\\Final Java Assignment\\My Folder\\PiJukebox\\Backend\\songs\\";
    // DON'T FORGET TO ADD \\ TO THE END OF "pathToSong" ;)
    private final String pathToSong = "D:\\Users\\Ruben\\Google Drive TheWheelz14\\Java Minor\\Royalty Free Music\\";

    public StartPlayer()
    {
        songs = new ArrayList<>();

        //File[] files = new File(pathToSong).listFiles();
        //showFiles(files);

        //sp = new SongPlayer(songs.get(current));
        sp = new SongPlayer(songs.get(current));
    }

    public void addSong(SimpleTrack path)
    {
        path.setFilename(pathToSong + path.getFilename());
        songs.add(path);
    }


    public void deleteSong(Long id)
    {
        songs.remove(Integer.parseInt(id.toString()));
    }

//    public void startMain()
//    {
//
////        System.out.println("Press 1 to: play");
////        System.out.println("Press 2 to: pause");
////        System.out.println("Press 3 to: stop");
////        System.out.println("Press 4 to: next");
//        sp = new SongPlayer(songs.get(current));
////        sp.play();
////        songs.add("songs/");
//
//    }

    public void pause()
    {
        sp.next(songs.get(current).getFilename());
        sp.pause();
    }
    public void stop()
    {
        sp.stop();
    }
    public void play()
    {
        sp.play();
    }

    public void next()
    {
        current++;
        if(current < songs.size())
        {
            sp.next(songs.get(current).getFilename());
        }else{
            current = 0;
        }
    }

    public void prev()
    {
        current--;
        if(current <= 0)
        {
            current = 0;
            sp.next(songs.get(current).getFilename());
        }else{
            current = 0;
        }
    }

    public ArrayList<SimpleTrack> getQueue() {
        return songs;
    }

    public Boolean isPlaying() {
        return sp.isPlaying();
    }

    public double getVolumeLevel() {
        return sp.volumeLevel();
    }

    public void setVolume(int volume) {
        double volumeLevel = ((double)volume / 10);
        sp.setVolumeLevel(volumeLevel);
    }
}
