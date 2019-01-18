package com.pijukebox.controller.player;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class StartPlayer{

    private SongPlayer sp;
    private ArrayList<String> songs;
    private int current = 0;
    private final String pathToSong ="C:\\Users\\codru\\Desktop\\School\\Year 4\\Minor Java Assignments\\Final Java Assignment\\My Folder\\PiJukebox\\Backend\\songs\\";
;    public StartPlayer()
    {
//        System.out.println("Here startMain");
        songs = new ArrayList<>();
        songs.add(pathToSong+"B.U.G. Mafia - La Fel De Prost Ca Tine (feat. Bogdan Dima).mp3");
        songs.add(pathToSong+"Calum Scott - Dancing On My Own.mp3");
        songs.add(pathToSong+"Calum Scott - You Are The Reason (Official).mp3");
        songs.add(pathToSong+"Guess Who feat. Mitza - Decat sa minti.mp3");
        songs.add(pathToSong+"Jessie Ware - Say You Love Me.mp3");
        songs.add(pathToSong+"Kaleo - Way Down We Go (Official Video).mp3");
        songs.add(pathToSong+"Lawless feat. Sydney Wayser - Dear God ( Lyrics ).mp3");
        songs.add(pathToSong+"Nane - 911 (Videoclip Oficial).mp3");
        sp = new SongPlayer(songs.get(current));

    }

    public void addSong(String path)
    {
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
            sp.next(songs.get(current));
        }else{
            current = 0;
        }
    }
}
