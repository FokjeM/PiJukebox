package com.PiJukeboxPlayer;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
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
    /**
     * The QUEUE_FILE "stateful_queue.out" which should be located in the same
     * directory as this class or its corresponding jar/war file
     */
    private final static Path QUEUE_FILE = FileSystems.getDefault().getPath("stateful_queue.out");
    /**
     * The ErrorLogger object for this Player.
     * 
     * When recovering from a previous instance, the ErrorLogger from that
     * instance should be passed as the ErrorLogger argument 
     */
    public final ErrorLogger log;
    private final Queue queue;
    private List statefulQueue;
    private Track currentTrack;
    private int trackNum;
    private boolean repeat;
    private boolean repeatOne;
    private Process proc;
    private boolean procKill;
    //You can't be playing when you start
    private boolean playing = false;
    private final Thread procExitCode;
    private final Thread procOutput;
    private final OutputStreamWriter procInput = new OutputStreamWriter(proc.getOutputStream());
    
    /**
     * Instantiate a new Player. This constructor sets no default options.
     * 
     * If recovering from a previous instance, do NOT pass a new ErrorLogger,
     * instead pass the ErrorLogger from that instance of Player.
     * <ul>
     * <li>Repeat: ON (true) or OFF (false) depending on rep</li>
     * <li>Repeat One: ON (true) or OFF (false) depending on repOne</li>
     * </ul>
     * @param rep Set repeating the entire Queue indefinitely.
     * @param repOne Set repeating this Track indefinitely.
     * @param q The Queue to instantiate with.
     * @param el the ErrorLogger for this instance of Player.
     */
    public Player(boolean rep, boolean repOne, Queue q, ErrorLogger el){
        log = el;
        queue = new Queue(q);
        currentTrack = null;
        trackNum = 0;
        if(checkQueueFile()) {
            restoreQueue();
        } else {
            updateStatefulQueue();
        }
        this.repeat = rep;
        this.repeatOne = repOne;
        procExitCode = new Thread(() -> {
            int exitCode;
            try {
                exitCode= proc.waitFor();
                if(exitCode != 0){
                    writeLog(new NonFatalException("FFPlay returned a non-zero exit code!", new IOException()));
                } else {
                    try {
                        this.onSongEnd();
                    } catch(NonFatalException | FatalException ex) {
                        this.writeLog(ex);
                    }
                }
            } catch(InterruptedException ie) {
                writeLog(new NonFatalException("FFPlay exit code listener was interrupted waiting for an exit code!", ie));
            }
        });
        
        procOutput = new Thread(() -> {
            InputStream in = proc.getInputStream();
            Scanner scan = new Scanner(new InputStreamReader(in));
            //FFPlay doesn't output to stdout (this InputStream)
            //FFPlay does output to stderr (this InputStream because error is redirected)
            while((scan.hasNextLine() || playing || proc.isAlive()) && !procKill){
                log.writeFFPlayLog(scan.next());
            }   try {
                this.onSongEnd();
            } catch (NonFatalException | FatalException ex) {
                this.writeLog(ex);
            }
        });
    }
    
    /**
     * Instantiate a new Player. This constructor sets no default options.
     * 
     * Creates a new instance of {@link com.PiJukeboxPlayer.ErrorLogger ErrorLogger} to log this object.
     * This logger should be retrieved by the instantiating class to pass to the
     * constructor in case of a recovery.
     * <ul>
     * <li>Repeat: ON (true) or OFF (false) depending on rep</li>
     * <li>Repeat One: ON (true) or OFF (false) depending on repOne</li>
     * </ul>
     * @param rep Set repeating the entire Queue indefinitely.
     * @param repOne Set repeating this Track indefinitely.
     * @param q The Queue to instantiate with.
     */
    public Player(boolean rep, boolean repOne, Queue q) {
        this(rep, repOne, q, new ErrorLogger(LocalDateTime.now()));
    }
    
    /**
     * Instantiate a new Player. This constructor sets no default options.
     * <ul>
     * <li>Repeat: ON (true) or OFF (false) depending on rep</li>
     * <li>Repeat One: ON (true) or OFF (false) depending on repOne</li>
     * </ul>
     * @param rep Set repeating the entire Queue indefinitely.
     * @param repOne Set repeating this Track indefinitely.
     */
    public Player(boolean rep, boolean repOne){
        this(rep, repOne, new Queue());
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
     * Checks whether or not the QUEUE_FILE exists so the stateful queue can be
 restored in this iteration of the application.
     * @return 
     */
    private boolean checkQueueFile() {
        return Files.exists(QUEUE_FILE);
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
                Files.write(QUEUE_FILE, new byte[0], StandardOpenOption.CREATE);
            }
            //Save current track number
            statefulQueue.add(0, Integer.toString(trackNum));
            //WRITE without saving previous data.
            Files.write(QUEUE_FILE, statefulQueue, StandardOpenOption.WRITE);
            return true;
        } catch (IOException ex) {
            writeLog(new NonFatalException("Could not save the Stateful Queue to file.", ex));
            System.out.print("Running in non-stateful mode; cannot resume this queue on exit.");
            return false;
        }
    }
    
    /**
     * Restores the stateful queue, assuming the file specified in QUEUE_FILE
 exists. Please only call this function after calling {@link #checkQueueFile() checkQueueFile}
     */
    private void restoreQueue() {
        try {
            List<String> qf = Files.readAllLines(QUEUE_FILE);
            //Sets trackNum to the previous state and shifts the tracks back up.
            trackNum = Integer.parseInt(qf.remove(0));
            //Restores the queue
            queue.putAll(new Queue(qf));
        } catch (IOException ex) {
            //Immediately handle the the exception so we don't have to throw stuff
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
        //Assume the exception is fatal.
        boolean fatal = true;
        //A NonFatalException automatically prepends "NON-FATAL: " to a string.
        if(ex.getMessage().substring(0, 11).equals("NON-FATAL: ".substring(0, 11))) {
            //Thus fatal becomes false.
            fatal = false;
        }
        this.log.writeLog(ex, fatal);
    }
    
    /**
     * Plays the next song in queue using {@link #playTrack(com.PiJukebox.Track) playTrack}.
     * @throws FatalException propagated from {@link  #playTrack(com.PiJukebox.Track) playTrack}
     * @throws com.PiJukeboxPlayer.NonFatalException propagated from {@link #playTrack(com.PiJukebox.Track) playTrack}
     */
    public void next() throws FatalException, NonFatalException{
        if(trackNum == 0){
            //As long as there is still a track in there, we'll play it.
            while(!queue.containsKey(trackNum) && trackNum <= queue.size()){
                trackNum++;
            }
        } else {
            //If this was the last Track in the Queue
            //pre-increment. Forces the JVM to increment before anything else
            if(!queue.containsKey(++trackNum) && repeat){
                //Go back to 0
                trackNum = 0;
                //And prevent causing errors, playing null or duplicating code
                next();
                //Also, prevent calling playTrack twice
                return;
            } else {
                trackNum = 0;
                queue.clear();
                updateStatefulQueue();
                return;
            }
        }
        currentTrack = queue.get(trackNum);
        playTrack(currentTrack);
    }
    
    /**
     * Moves back to the previous Track, or wraps back to the last when repeating.
     * @throws FatalException propagated from {@link #playTrack(com.PiJukebox.Track) playTrack}
     * @throws NonFatalException propagated from {@link #playTrack(com.PiJukebox.Track) playTrack}
     */
    public void previous() throws FatalException, NonFatalException {
        if(trackNum > 1) {
            //pre-decrement. Forces the JVM to decrement before doing anything else
            currentTrack = queue.get(--trackNum);
        } else if(!repeat) {
            //Don't do ANYTHING we don't need to do.
            return;
        } else {
            trackNum = queue.size();
            currentTrack = queue.get(trackNum);
        }
        updateStatefulQueue();
        playTrack(currentTrack);
    }
    
    /**
     * Plays the specified Track
     * @param t the Track to play
     * @throws com.PiJukeboxPlayer.FatalException When FFPlay can't be started
     * @throws com.PiJukeboxPlayer.NonFatalException propagated from {@link #stop() stop()}
     */
    public void playTrack(Track t) throws FatalException, NonFatalException{
        stop(); //Make sure we stop playing so we can do our standard set of tasks
        updateStatefulQueue();
        //Setup the arguments we need for this Track
        StringBuilder cmd = new StringBuilder();
        //Don't print file info, exit when done and don't display anything
        cmd.append("-nostats -autoexit -nodisp ");
        //Disable video and subtitles
        cmd.append("-vn -sn ");
        //Set the codec for all streams
        cmd.append("-codec:a ");
        cmd.append(t.getStreamType()); //Get the encoding, thus codec for this Track
        //Set the track duration so we can tell FFPlay how long it should run
        cmd.append(" -t ");
        cmd.append(t.getDuration());//Get the duration for this Track
        //Set the file we want to play
        cmd.append(" -i ");
        cmd.append(t.getPath().toAbsolutePath().toString()); //Get the full, absolute path
        try {
            //the process shouldn't get killed on starting
            procKill = false;
            //ProcessBuilder expects the executable or command to be seperate from all arguments
            proc = new ProcessBuilder("ffplay", cmd.toString()).redirectErrorStream(true).start();
            //start listening for the exit code and any output without hanging
            procExitCode.start();
            procOutput.start();
        } catch (IOException ex) {
            procKill = true; //Stop the threads again
            throw new FatalException("Could not start FFPlay!", ex);
        }
        playing = true;
    }
    
    /**
     * Add a single Track to this Queue
     * @param t the track to add to this Queue
     */
    public void addToQueue(Track t){
        queue.put(queue.size()+1, t);
        updateStatefulQueue();
    }
    
    /**
     * Add an entire Queue to this Queue.
     * @param q the Queue to add to this Queue
     */
    public void addToQueue(Queue q){
        queue.putAll(q);
        updateStatefulQueue();
    }
    
    /**
     * Pause the currently playing track. Does nothing when not playing.
     * @throws NonFatalException when writing to the FFPlay fails
     */
    public void pause() throws NonFatalException {
        if(!playing){
            return;
        }
        try{
            procInput.write("p");
        } catch (IOException ex) {
            proc.destroy();
            procKill = true;
            throw new NonFatalException("Could not pause FFPlay; Force-quit it", ex);
        }
        playing = false;
    }
    
    /**
     * Resume playing the currently paused track. Does nothing when playing.
     * @throws NonFatalException when writing to the FFPlay fails
     */
    public void resume() throws NonFatalException{
        if(playing){
            return;
        }
        try{
            procInput.write("p");
            playing = true;
        } catch (IOException ex) {
            proc.destroy();
            procKill = true;
            throw new NonFatalException("Could not resume FFPlay; Force-quit it", ex);
        }
    }
    
    /**
     * Stop the currently playing song by sending an exit signal to FFPlay.
     * @throws NonFatalException when FFPlay does not stop by itself
     */
    public void stop() throws NonFatalException {
        if(!proc.isAlive()) {
            //If the process has already exited, or FFPlay wasn't started yet
            return;
        }
        //Try to leverage the threads to exit by themselves
        playing = false;
        procKill = true;
        try{
            //Can't tell a dead process to stop
            if(proc.isAlive()) {
                procInput.write("q");
            }
        } catch (IOException ex) {
            proc.destroy();
            throw new NonFatalException("Could not stop FFPlay; Force-quit it", ex);
        }
        proc.destroy();
    }
    
    /**
     * Handle what happens when a song ends (and FFPlay exits)
     * @throws FatalException propagated from {@link #playTrack(com.PiJukebox.Track) playTrack}
     * @throws NonFatalException propagated from {@link #playTrack(com.PiJukebox.Track) playTrack}
     */
    protected void onSongEnd() throws FatalException, NonFatalException{
        if(repeatOne){
            this.playTrack(currentTrack);
        } else {
            next();
        }
    }
    
    /**
     * Get the {@link com.PiJukeboxPlayer.ErrorLogger ErrorLogger} that belongs to this Player.
     * This method should be the first one called if planning to allow recovery
     * from a {@link com.PiJukeboxPlayer.FatalException FatalException}, to ensure retrieval.
     * @return 
     */
    public ErrorLogger getLogger(){
        return this.log;
    }
    
    /**
     * Get the current repeat state.
     * This does NOT give a correct indication of repeatOne.
     * @return true if in repeat mode, false otherwise.
     */
    public boolean getRepeat(){
        return this.repeat;
    }
    
    /**
     * Toggles repeat between true and false.
     * If this turns off repeat, it will also turn off repeatOne.
     */
    public void toggleRepeat(){
        if(repeat){ //If we're going to false, we should still be true
            if(repeatOne){ //If repeatOne is also true, we flip it
                toggleRepeatOne();
            }
        }
        this.repeat = !repeat; //Flip repeat, this'll change it both ways.
    }
    
    /**
     * Get the current repeatOne state.
     * If {@link #toggleRepeatOne()   this variable's toggle method} has been called at least once,
     * this is a decent indication of repeat.
     * @return true if in repeatOne mode, false otherwise.
     */
    public boolean getRepeatOne(){
        return this.repeatOne;
    }
    
    /**
     * Toggles repeatOne between true and false.
     * Has no effect on any other value
     */
    public void toggleRepeatOne(){
        this.repeatOne = !repeatOne;
    }
    
    public void dequeue(Track t) {
        this.queue.dequeue(t);
    }
    
    public String getCurrentTrack(){
        return this.currentTrack.getTitle();
    }
    
    public boolean isShuffled() {
        return this.queue.isShuffled();
    }
    
    public void shuffle() {
        this.queue.shuffle();
    }
    
    public boolean inQueue(Track t) {
        return this.queue.containsValue(t);
    }
    
    public boolean isPlaying() {
        return this.playing;
    }
}
