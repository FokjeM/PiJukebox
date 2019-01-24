package com.pijukebox.controller.player;

import com.pijukebox.model.simple.SimpleTrack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

public class StartPlayer {

    private SongPlayer sp;
    private ArrayList<SimpleTrack> songs;
    private int current = 0;
    private boolean repeatState = true;
    // DON'T FORGET TO ADD \\ TO THE END OF "pathToSong" ;)
    // private final String pathToSong ="C:\\Users\\codru\\Desktop\\School\\Year 4\\Minor Java Assignments\\Final Java Assignment\\My Folder\\PiJukebox\\Backend\\songs\\";
    //private final String pathToSong = "G:\\music\\";
    private final String pathToSong = "D:\\Users\\Ruben\\Google Drive TheWheelz14\\Java Minor\\Royalty Free Music\\";


    public StartPlayer() {
        songs = new ArrayList<>();

        //File[] files = new File(pathToSong).listFiles();
        //showFiles(files);

        //sp = new SongPlayer(songs.get(current));
        sp = new SongPlayer();
    }

    public void addSong(SimpleTrack path) throws Exception {
        path.setFilename(pathToSong + path.getFilename());
        songs.add(path);
    }

    public void deleteSong(Long id) throws Exception {
        songs.remove(Integer.parseInt(id.toString()));
    }

    public void clearQueue() {
        songs.clear();
    }

    public void moveSongUp(int index) {
        if (index > 0) {
            SimpleTrack simpleTrack = songs.get(index);
            songs.remove(index);
            songs.add(index - 1, simpleTrack);
        }
    }

    public void moveSongDown(int index) {
        if (index < (songs.size() - 1)) {
            SimpleTrack simpleTrack = songs.get(index);
            songs.remove(index);
            songs.add(index + 1, simpleTrack);
        }
    }

    public void addPlaylistToQueue(Set<SimpleTrack> playlist) throws Exception {
        for (SimpleTrack track : playlist) {
            track.setFilename(track.getFilename());
            addSong(track);
        }
    }

    //public void startMain()
    //{
    //    //System.out.println("Press 1 to: play");
    //    //System.out.println("Press 2 to: pause");
    //    //System.out.println("Press 3 to: stop");
    //    //System.out.println("Press 4 to: next");
    //    sp = new SongPlayer(songs.get(current));
    //    //sp.play();
    //    //songs.add("songs/");
    //}

    public void pause() throws Exception {
        sp.pause();
    }

    public void stop() throws Exception {
        sp.stop();
    }

    public void play() throws Exception {
        sp.setCurrent(songs.get(current).getFilename());
        sp.play();
    }

    public void next() throws Exception {
        sp.stop();
        current++;
        if (current < songs.size()) {
            sp.next(songs.get(current).getFilename());
        } else {
            if (repeatState) {
                current = 0;
            } else {
                current = (songs.size() - 1);
            }
        }
    }

    public void prev() throws Exception {
        current--;
        sp.stop();
        if (current <= 0) {
            if (repeatState) {
                current = (songs.size() - 1);
                sp.next(songs.get(current).getFilename());
            } else {
                current = 0;
            }
        } else {
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

    public void repeat() {
        this.repeatState = !this.repeatState;
    }

    public SimpleTrack getCurrent() throws Exception {
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

    public boolean getRepeatState() {
        return this.repeatState;
    }

    public void setVolume(int volume) {
        double volumeLevel = ((double) volume / 10);
        sp.setVolumeLevel(volumeLevel);
    }
}
