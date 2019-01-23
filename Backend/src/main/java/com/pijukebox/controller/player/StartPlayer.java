package com.pijukebox.controller.player;

import com.pijukebox.model.playlist.PlaylistWithTracks;
import com.pijukebox.model.simple.SimpleTrack;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

public class StartPlayer{

    private SongPlayer sp;
    private ArrayList<SimpleTrack> songs;
    private int current = 0;
    private boolean repeatState = true;
    // private final String pathToSong ="C:\\Users\\codru\\Desktop\\School\\Year 4\\Minor Java Assignments\\Final Java Assignment\\My Folder\\PiJukebox\\Backend\\songs\\";
    // DON'T FORGET TO ADD \\ TO THE END OF "pathToSong" ;)
    private final String pathToSong = "D:\\Java minor\\Royalty Free Music\\";


    public StartPlayer()
    {
        songs = new ArrayList<>();

        //File[] files = new File(pathToSong).listFiles();
        //showFiles(files);

        //sp = new SongPlayer(songs.get(current));
        sp = new SongPlayer();
    }

    public void addSong(SimpleTrack path) throws Exception
    {
        path.setFilename(pathToSong + path.getFilename());
        songs.add(path);
    }

    public void addPlaylistToQueue(Set<SimpleTrack> playlist) throws Exception
    {
        //ga door playlist
        //haal tracks op
        //queue is arraylist met simpletracks

        songs.addAll(playlist);
    }


    public void deleteSong(Long id) throws Exception
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

    public void pause() throws Exception
    {
        sp.pause();
    }
    public void stop() throws Exception
    {
        sp.stop();
    }
    public void play() throws Exception
    {
        sp.setCurrent(songs.get(current).getFilename());
        sp.play();
    }

    public void next() throws Exception
    {
        sp.stop();
        current++;
        if(current < songs.size())
        {
            sp.next(songs.get(current).getFilename());
        }else{
            current = 0;
        }
    }

    public void prev() throws Exception
    {
        current--;
        sp.stop();
        if(current <= 0)
        {
            current = 0;
            sp.next(songs.get(current).getFilename());
        }else{
            sp.next(songs.get(current).getFilename());
        }
    }

    public void shuffle() throws Exception {
        SimpleTrack currentTrack = songs.get(current);
        songs.remove(current);
        Collections.shuffle(songs);
        songs.add(0, currentTrack);
        current = 0;
    }

    public void repeat(){
        this.repeatState = !this.repeatState;
    }

    public SimpleTrack getCurrent() throws Exception
    {
        return songs.get(current);
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

    public boolean getRepeatState(){
        return this.repeatState;
    }

    public void setVolume(int volume) {
        double volumeLevel = ((double)volume / 10);
        sp.setVolumeLevel(volumeLevel);
    }
}
