package com.PiJukebox;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;

/**
 * This class is supposed to handle the playing of files.
 * It exposes a few methods that leave little to no room for external mutation,
 * while allowing normal media player behavior.
*/
public class Player {
    
    private final Path queueFile = FileSystems.getDefault().getPath("stateful_queue.out");
    private List playlist;
    public ErrorLogger log;
    private boolean repeat;
    private boolean repeatOne;
    
    /*
     * @param rep Sets initial repeat on/off
     * @param shuffle Sets wether or not to shuffle the playlist initially
     * @param list the playlist to play. This moves into a list to prevent
     *      accidentally messing up the DB or source object for the Playlist
    */
    public Player(boolean rep, boolean repOne, boolean shuffle, Playlist list, ErrorLogger log) {
        if(checkQueueFile()) {
            restoreQueue();
        } else {
            updateStatefulQueue();
        }
        this.log = log;
        if(rep) {
            this.repeat = rep;
            if(repOne) {
                this.repeatOne = repOne;
            }
        }
        if(shuffle) {
            this.playlist = shuffle();
        }
    }
    
    private boolean checkQueueFile() {
        return Files.exists(queueFile);
    }
    
    private boolean updateStatefulQueue() {
        try {
            //We need to double check this, as calling create on a file that
            //already exists *will* throw an IOException.
            //However, the file might have been removed during runtime.
            if(!checkQueueFile()) {
                Files.write(queueFile, new byte[0], StandardOpenOption.CREATE);
            }
            //WRITE without saving previous data.
            Files.write(queueFile, playlist, StandardOpenOption.WRITE);
            return true;
        } catch (IOException ex) {
            writeLog(ex, false);
            System.out.print("Running in non-stateful mode; cannot resume this queue on exit.");
            return false;
        }
    }
    
    private void restoreQueue() {
        try {
            Playlist pl = new Playlist(Files.readAllLines(queueFile));
            playlist = (List)pl.values();
        } catch (IOException ex) {
            writeLog(ex, false);
        }
    }
    
    private void writeLog(Exception ex, boolean fatal) {
        log.writeLog(ex, fatal);
    }
    
    private List<String> shuffle() {
        //TODO: Shuffle the playlist
        return playlist;
    }
}
