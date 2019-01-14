package com.PiJukebox;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Scanner;

/**
 * The Player object will create an ErrorLogger,
     * Queue and PlayerTrack from the components within its own package.
     * It requires 0, 2 or 3 Boolean values for setting the options
     * <ul>
     * <li>2: Repeat and Repeat One.</li>
     * <li>1: Repeat and Repeat One=>false</li>
     * <li>0: Repeat=>false and Repeat One=>false</li>
     * </ul>
 * @author Martin
 */

public class Player {
    
    private final static Path QUEUEFILE = FileSystems.getDefault().getPath("stateful_queue.out");
    private final Queue queue;
    private List statefulQueue;
    public ErrorLogger log;
    private Track currentTrack;
    private int trackNum;
    private boolean repeat;
    private boolean repeatOne;
    private Process proc;
    private final Thread procOutput = new Thread(new Runnable() {
        InputStream in = proc.getInputStream();

        @Override
        public void run() {
            Scanner scan = new Scanner(new InputStreamReader(in));
            //FFPlay doesn't output to stdout (this InputStream)
            //FFPlay does output to stderr (this InputStream because error is redirected)
            while(scan.hasNextLine()){
                log.writeFFPlayLog(scan.next());
            }
        }
    });
    private final Thread procInput = new Thread(new Runnable() {
        OutputStream out = proc.getOutputStream();
        
        @Override
        public void run() {
            
        }
    });
    
    /**
     * Instantiate a new Player. This constructor sets no default options.
     * <ul>
     * <li>Repeat: ON (true) or OFF (false) depending on rep</li>
     * <li>Repeat One: ON (true) or OFF (false) depending on repOne</li>
     * </ul>
     * @param rep Set repeating the entire Queue indefinitely.
     * @param repOne Set repeating this Track indefinitely.
     */
    public Player(boolean rep, boolean repOne) {
        queue = new Queue();
        currentTrack = null;
        trackNum = 0;
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
    }
    
    /**
     * Instantiate a new Player. This constructor sets the following defaults:
     * <ul>
     * <li>Repeat: ON (true) or OFF (false) depending on rep</li>
     * <li>Repeat One: OFF (false)</li>
     * </ul>
     * @param rep Set repeating the entire Queue indefinitely.
     */
    public Player(boolean rep){
        this(rep, false);
    }
    
    /**
     * Instantiate a new Player. This constructor sets the following defaults:
     * <ul>
     * <li>Repeat: OFF (false)</li>
     * <li>Repeat One: OFF (false)</li>
     * </ul>
     */
    public Player(){
        this(false, false);
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
            Files.write(QUEUEFILE, statefulQueue, StandardOpenOption.WRITE);
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
            queue.putAll(new Queue(Files.readAllLines(QUEUEFILE)));
        } catch (IOException ex) {
            writeLog(new NonFatalException("Queuefile exists, but access was not granted. Will continue as if the queuefile didn't exist", ex));
        }
        this.statefulQueue = (List) this.queue.valueStrings();
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
     * Plays the next song in queue using {@link #play(com.PiJukebox.Track) play}.
     */
    public void play() throws FatalException{
        if(currentTrack == null || trackNum == 0){
            //As long as there is still a track in there, we'll play it.
            while(!queue.containsKey(trackNum) && trackNum <= queue.size()){
                trackNum++;
            }
            currentTrack = queue.get(trackNum);
            play(currentTrack);
        }
    }
    
    /**
     * Plays the specified Track
     * @param t 
     */
    public void play(Track t) throws FatalException{
        StringBuilder cmd = new StringBuilder();
        cmd.append("-nostats -autoexit -nodisp -vn -sn ");
        if(repeatOne){
            cmd.append("-loop 0 ");
        }
        cmd.append("-codec:a "); cmd.append(t.getStreamType());
        cmd.append(" -t "); cmd.append(t.getDuration());
        cmd.append(" -i "); cmd.append(t.getPath().toAbsolutePath().toString());
        try {
            proc = new ProcessBuilder("ffplay", cmd.toString()).redirectErrorStream(true).start();
        } catch (IOException ex) {
            throw new FatalException("Could not start FFMPEG!", ex);
        }
    }
    
}
