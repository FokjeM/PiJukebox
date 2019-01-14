package com.PiJukebox;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;

/**
 * The Player object will create an ErrorLogger,
     * Queue and PlayerTrack from the components within its own package.
     * It requires 0, 2 or 3 Boolean values for setting the options
     *      3: Repeat, Repeat One and Shuffle.
     *      2: Repeat, Repeat One=>false, Shuffle.
     *      0: Repeat=>false, Repeat One=>false, Shuffle=>false.
     * 
     * If the application closes and a StatefulQueue has been saved, this will
     * be marked as not-shuffled. This is not a bug, it is intended behavior
     * designed to allow the backend to attempt recovering in the fastest manner
     * possible. This means Repeat and Repeat One will be given by Front-End.
     * The Shuffle state DOES NOT CHANGE.
 * @author Martin
 */

public class Player {
    
    private final static Path QUEUEFILE = FileSystems.getDefault().getPath("stateful_queue.out");
    private final Queue playQueue;
    private List queue;
    public ErrorLogger log;
    private Track currentTrack;
    private boolean repeat;
    private boolean repeatOne;
    
    /**
     * Instantiate a new Player. This constructor sets no default options.
     * <ul>
     * <li>Repeat: ON (true) or OFF (false) depending on rep</li>
     * <li>Repeat One: ON (true) or OFF (false) depending on repOne</li>
     * <li>Shuffle: ON (true) or OFF (false) depending on shuffle</li>
     * </ul>
     * @param rep Set repeating the entire Queue indefinitely.
     * @param repOne Set repeating this Track indefinitely.
     * @param shuffle Shuffle the Queue when populating, modifying or loading it.
     */
    public Player(boolean rep, boolean repOne, boolean shuffle) {
        playQueue = new Queue();
        if(checkQueueFile()) {
            restoreQueue();
        } else {
            updateStatefulQueue();
        }
        if(rep) {
            this.repeat = rep;
            if(repOne) {
                this.repeatOne = repOne;
            }
        }
        if(shuffle) {
            this.queue = shuffle();
        }
    }
    
    /**
     * Instantiate a new Player. This constructor sets the following defaults:
     * <ul>
     * <li>Repeat: ON (true) or OFF (false) depending on rep</li>
     * <li>Repeat One: OFF (false)</li>
     * <li>Shuffle: ON (true) or OFF (false) depending on shuffle</li>
     * </ul>
     * @param rep Set repeating the entire Queue indefinitely.
     * @param shuffle Shuffle the Queue when populating, modifying or loading it.
     */
    public Player(boolean rep, boolean shuffle){
        this(rep, false, shuffle);
    }
    
    /**
     * Instantiate a new Player. This constructor sets the following defaults:
     * <ul>
     * <li>Repeat: OFF (false)</li>
     * <li>Repeat One: OFF (false)</li>
     * <li>Shuffle: OFF (false)</li>
     * </ul>
     */
    public Player(){
        this(false, false, false);
    }
    
    /**
     * Checks whether or not the QUEUEFILE exists so the stateful queue can be
     * restored in this iteration of the application.
     * @return 
     */
    private boolean checkQueueFile() {
        return Files.exists(QUEUEFILE);
    }
    
    /**
     * Update the stateful queue. This should be called after EVERY change in
     * the queue (So not when on Repeat One).
     * It should be noted that the return value informs the caller of success
     * or failure in a non-breaking mode. By default, failure to write the file
     * is regarded and treated as a NonFatalError. If this behavior is not
     * desirable, either throw an error on failure, or override this class.
     * @return 
     */
    private boolean updateStatefulQueue() {
        try {
            //We need to double check this, as calling create on a file that
            //already exists *will* throw an IOException.
            //However, the file might have been removed during runtime.
            if(!checkQueueFile()) {
                Files.write(QUEUEFILE, new byte[0], StandardOpenOption.CREATE);
            }
            //WRITE without saving previous data.
            Files.write(QUEUEFILE, queue, StandardOpenOption.WRITE);
            return true;
        } catch (IOException ex) {
            writeLog(new NonFatalException("Could not save the Stateful Queue to file.", ex));
            System.out.print("Running in non-stateful mode; cannot resume this queue on exit.");
            return false;
        }
    }
    
    /**
     * Restores the stateful queue, assuming the file specified in QUEUEFILE
     * exists. Please only call this function after calling {@link #checkQueueFile() checkQueueFile}
     */
    private void restoreQueue() {
        try {
            playQueue.putAll(new Queue(Files.readAllLines(QUEUEFILE)));
        } catch (IOException ex) {
            writeLog(new NonFatalException("Queuefile exists, but access was not granted. Will continue as if the queuefile didn't exist", ex));
        }
        this.queue = (List) this.playQueue.valueStrings();
    }
    
    /**
     * Lets the {@link ErrorLogger ErrorLogger} handle logging and exiting where
     * appropriate.
     * @param ex The exception to log. Should be an instance of the included
     * FatalException or a NonFatalException Exception classes.
     */
    private void writeLog(Exception ex) {
        boolean fatal = false;
        if(ex.getMessage().substring(0, 11).equals("NON-FATAL: ".substring(0, 11))) {
            fatal = true;
        }
        this.log.writeLog(ex, fatal);
    }
    
    /**
     * Shuffles the current queue List.
     * @return The shuffled list.
     */
    public List<String> shuffle() {
        //TODO: Shuffle the queue
        
        return queue;
    }
    
    /**
     * Undo Shuffling by reading the original Queue back into the application.
     * @return The original list provided by playQueue.
     */
    public List<String> unShuffle(){
        return (List)playQueue.valueStrings();
    }
}
