package com.pijukebox.controller.player;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class StartPlayer{

    private SongPlayer sp;
    private ArrayList<String> songs;
    private int current = 0;
//    private final String pathToSong ="C:\\Users\\codru\\Desktop\\School\\Year 4\\Minor Java Assignments\\Final Java Assignment\\My Folder\\PiJukebox\\Backend\\songs\\";
    // DON'T FORGET TO ADD \\ TO THE END OF "pathToSong" ;)
    private final String pathToSong = "D:\\Users\\Ruben\\Google Drive TheWheelz14\\Java Minor\\Royalty Free Music\\";

    public StartPlayer()
    {
        songs = new ArrayList<>();

        File[] files = new File(pathToSong).listFiles();
        showFiles(files);
        
        sp = new SongPlayer(songs.get(current));
    }

    private void showFiles(File[] files) {
        for (File file : files) {
            if (file.isDirectory()) {
                System.out.println("Directory: " + file.getName());
                showFiles(file.listFiles()); // Calls same method again.
            } else {
                System.out.println("File: " + file.getName());
                songs.add((pathToSong+file.getName()));
            }
        }
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
