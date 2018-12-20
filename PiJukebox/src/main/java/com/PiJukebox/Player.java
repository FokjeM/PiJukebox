package com.PiJukebox;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import javax.sound.sampled.*;

public class Player {
    
    /*
     * This class is supposed to handle the playing of files.
     * It exposes a few methods that leave little to no room for external mutations.
    */
    
    private static Path queueFile;
    private static Path logFile;
    private List playlist;
    private boolean repeat;
    private boolean repeatOne;
    
    /*
     * @param rep Sets initial repeat on/off
     * @param shuffle Sets wether or not to shuffle the playlist initially
     * @param list the playlist to play. This moves into a list to prevent
     *      accidentally messing up the DB entry for the Playlist
    */
    public Player(boolean rep, boolean shuffle, Playlist list) {
        queueFile = FileSystems.getDefault().getPath("stateful_queue.out");
        logFile = FileSystems.getDefault().getPath("logs", "javaPlayer.log");
        if(checkQueueFile()){
            restoreQueue();
        } else {
            updateStatefulQueue();
        }
    }
    
    private boolean checkQueueFile() {
        return Files.exists(queueFile);
    }
    
    private boolean updateStatefulQueue() {
        try {
            if(!checkQueueFile()) {
                Files.write(queueFile, new byte[0], StandardOpenOption.CREATE);
            }
            Files.write(queueFile, playlist, StandardOpenOption.WRITE);
            return true;
        } catch (IOException ex) {
            String log = "Caught an error updating the queue!\n\tError: " + ex.toString() + "\n\tMessage:" + ex.getMessage();
            writeLog(log);
            return false;
        }
    }
    
    private void restoreQueue() {
        try {
            playlist = Files.readAllLines(queueFile);
        } catch (IOException ex) {
            String log = "Caught an error updating the queue!\n\tError: " + ex.toString() + "\n\tMessage:" + ex.getMessage();
            writeLog(log);
        }
    }
    
    private void writeLog(String log) {
        List logList;
        logList = new ArrayList<>();
        logList.add(log.subSequence(0, log.length()-1));
        try {
            if(Files.exists(logFile)) {
                Files.write(logFile, new byte[0], StandardOpenOption.CREATE);
            }
            Files.write(logFile, logList, StandardOpenOption.WRITE);
        } catch (IOException ex) {
            System.out.println(log);
        }
    }
}
