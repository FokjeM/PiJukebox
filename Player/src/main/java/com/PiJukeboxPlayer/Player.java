package com.PiJukeboxPlayer;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

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

public class Player extends Application {
    /**
     * The QUEUE_FILE "stateful_queue.out" which should be located in a
     * subdirectory of this application or its corresponding jar/war file
     */
    private final static Path QUEUE_FILE = FileSystems.getDefault().getPath(ErrorLogger.initPath("Logs").toString(), "stateful.queue");
    /**
     * The ErrorLogger object for this Player.
     * 
     * When recovering from a previous instance, the ErrorLogger from that
     * instance should be passed as the ErrorLogger argument 
     */
    public final ErrorLogger log;
    private final Queue queue;
    private List<String> statefulQueue;
    private Track currentTrack;
    private int trackNum;
    private boolean repeat;
    private boolean repeatOne;
    //You can't be playing when you start, this is always false on init.
    private boolean playing = false;
    private AudioPlayer player;
    
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
     * @throws java.lang.Exception propagated from the MediaPlayer
     */
    public Player(boolean rep, boolean repOne, Queue q, ErrorLogger el) throws Exception {
        this.log = el;
        this.queue = new Queue(q);
        this.currentTrack = null;
        this.trackNum = 0;
        this.statefulQueue = new ArrayList<>();
        if(checkQueueFile()) {
            restoreQueue();
        } else {
            updateStatefulQueue();
        }
        this.repeat = rep;
        this.repeatOne = repOne;
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
    public Player(boolean rep, boolean repOne, Queue q) throws Exception {
        this(rep, repOne, q, new ErrorLogger());
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
    public Player(boolean rep, boolean repOne) throws Exception{
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
    public Player(boolean rep) throws Exception{
        this(rep, false);
    }
    
    /**
     * Instantiate a new Player. This constructor sets the following defaults:
     * <ul>
     * <li>Repeat: OFF (false)</li>
     * <li>Repeat One: OFF (false)</li>
     * </ul>
     */
    public Player() throws Exception{
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
     * or failure in a non-breaking manner.
     * 
     * By default, failure to write the file is regarded and treated as a
     * NonFatalError. If this behavior is not desirable, either handle false as
     * an error, or override this method. The easiest solution is throwing an
     * error (which could be a {@link com.PiJukeboxPlayer.NonFatalException NonFatalException} or {@link com.PiJukeboxPlayer.FatalException FatalException)
     * and handling that as would be applicable to the use case.
     * 
     * This method may be called at any point in time, as it allows for a better
     * user experience. It is called internally at several points in time as well.
     * 
     * This method and its class do not ensure the default directory exists.
     * Classes that use a {@link com.PiJukeboxPlayer.Player Player} are encouraged
     * to set a mechanism in place that creates the missing directories.
     * 
     * If the specified directory exists, the existence of the file is guaranteed
     * so long as the JVM instance executing this method has read and write
     * permissions on the directory. Starting this with elevated privileges is
     * therefore strongly recommended.
     * 
     * @return A boolean value set to true on success, or to false on failure.
     */
    public boolean updateStatefulQueue() {
            statefulQueue.clear();
        try {
            System.out.println("Updating Queue");
            this.statefulQueue.addAll(queue.valueStrings());
            System.out.println("StatefulQueue size: " + statefulQueue.size());
            //We need to double check this, as calling create on a file that
            //already exists *will* throw an IOException.
            //However, the file might have been removed during runtime.
            if(!Files.exists(QUEUE_FILE)) {
                Files.createFile(QUEUE_FILE);
                System.out.println("Created Queue file");
            }
            if(!statefulQueue.isEmpty()) {
                System.out.println("Queue wasn't empty");
                //Save current track number
                statefulQueue.add(0, Integer.toString(trackNum));
                System.out.println("Added trackNum");
            } else {
                System.out.println("Queue was empty");
            }
            //WRITE without saving any previous data.
            Files.write(QUEUE_FILE, statefulQueue, StandardOpenOption.WRITE);
            System.out.println("Wrote to the file!");
            return true;
        } catch (IOException ex) {
            writeLog(new NonFatalException("Could not save the Stateful Queue to file.", ex));
            System.err.println("Running in non-stateful mode; cannot resume this queue on exit.");
            return false;
        }
    }
    
    /**
     * Restores the stateful queue, assuming the file specified in QUEUE_FILE
     * exists. Please only call this function after calling {@link #checkQueueFile() checkQueueFile}
     * if the existence of the file is not guaranteed.
     */
    private void restoreQueue() {
        try {
            List<String> qf = Files.readAllLines(QUEUE_FILE);
            if(qf.isEmpty()) {
                System.out.println("Read the queue file, but it was empty.");
                return;
            }
            //Sets trackNum to the previous state and shifts the tracks back up.
            trackNum = Integer.parseInt(qf.remove(0));
            //Restores the queue
            queue.putAll(new Queue(qf));
        } catch (IOException ex) {
            //Immediately handle the the exception so we don't have to throw stuff
            writeLog(new NonFatalException("Queuefile exists, but access was not granted. Will continue as if the queuefile didn't exist", ex));
        } catch (NonFatalException | FatalException ex) {
            writeLog(ex);
        }
        this.statefulQueue.addAll(this.queue.valueStrings());
        System.out.println("Queue restored!");
    }
    
    /**
     * Lets the {@link com.PiJukeboxPlayer.ErrorLogger ErrorLogger} handle logging and exiting where
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
        //If this was the last Track in the Queue
        if(!queue.containsKey(trackNum+1)){
            if(!repeat) {
                //Repeat is off and the queue seems to be played through.
                trackNum = 0;
                queue.clear();
                updateStatefulQueue();
                return;
            }
            //Repeat is on; go back to 0 and play the next file
            trackNum = 0;
            next();
            //Also, prevent calling playTrack twice
            return;
        } else {
            trackNum++; 
            currentTrack = queue.get(trackNum);
        }
        playTrack(currentTrack);
    }
    
    /**
     * Moves back to the previous Track, or wraps back to the last when repeating.
     * @throws FatalException propagated from {@link #playTrack(com.PiJukebox.Track) playTrack}
     * @throws NonFatalException propagated from {@link #playTrack(com.PiJukebox.Track) playTrack}
     */
    public void previous() throws FatalException, NonFatalException {
        if(trackNum > 1) {
            trackNum--;
            currentTrack = queue.get(trackNum);
        } else if(!repeat) {
            //Don't do ANYTHING we don't need to do.
            return;
        } else {
            trackNum = queue.size();
            currentTrack = queue.get(trackNum);
        }
        playTrack(currentTrack);
    }
    
    /**
     * Plays the specified Track
     * @param t the Track to play
     * @throws com.PiJukeboxPlayer.FatalException When the MediaPlayer can't be started
     * @throws com.PiJukeboxPlayer.NonFatalException propagated from {@link #stop() stop()}
     */
    public void playTrack(Track t) throws FatalException, NonFatalException{
        if(t == null) {
            System.out.println("Track given was null. We're not doing this!");
            next();
        }
        stop(); //Make sure we stop playing so we can do our standard set of tasks
        updateStatefulQueue();
        //Set up the player for this Track
        player = new AudioPlayer(t.getMedia());
        playerInit(player);
        player.play();
        this.playing = true;
    }
    
    /**
     * A method that has to be called for a new AudioPlayer. It sets up what
     * action to take when the AudioPlayer reaches the EOF-marker.
     */
    private void playerInit(AudioPlayer a) {
        a.audioPlayer.setOnEndOfMedia(() -> {
            try {
                this.onSongEnd();
            } catch (NonFatalException | FatalException ex) {
                this.writeLog(ex);
            }
        });
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
     */
    public void pause() {
        if(!playing || player == null){
            return;
        }
        player.pause();
        playing = false;
        
    }
    
    /**
     * Resume playing the currently paused track. Does nothing when playing.
     */
    public void resume() {
        if(playing || player == null){
            return;
        }
        player.play();
        playing = true;
    }
    
    /**
     * Stop the currently playing song by disposing the MediaPlayer.
     * This resets the current Track, to prevent errors when resuming playback.
     */
    @Override
    public void stop() {
        if(player == null) {
            return;
        }
        if(player.stop()) {
            //Allows for calling resume() after stop() without errors.
            player = new AudioPlayer(currentTrack.getMedia());
            playerInit(player);
        }
        playing = false;
    }
    
    /**
     * Handle what happens when a song ends
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

    @Override
    public void start(Stage stage) throws Exception {
        Application.launch();
    }
    
    /**
     * Internal class for playing music.
     * This is the audio player, handles the actual {@link javafx.scene.media.MediaPlayer MediaPlayer}.
     */
    final class AudioPlayer {
        
        private final MediaPlayer audioPlayer;
        private final Media track;
        
        public AudioPlayer(Media m){
            this.track = m;
            audioPlayer = new MediaPlayer(track);
        }
        
        public boolean play() {
            if(!(audioPlayer.getStatus().equals(MediaPlayer.Status.PLAYING) || audioPlayer.getStatus().equals(MediaPlayer.Status.DISPOSED))) {
                audioPlayer.play();
                return true;
            }
            return false;
        }
        
        public boolean pause() {
            if(audioPlayer.getStatus().equals(MediaPlayer.Status.PLAYING)) {
                audioPlayer.pause();
                return true;
            }
            return false;
        }
        
        public boolean stop() {
            if(!(audioPlayer.getStatus().equals(MediaPlayer.Status.STOPPED))) {
                audioPlayer.stop();
            }
            if(!audioPlayer.getStatus().equals(MediaPlayer.Status.DISPOSED)) {
                audioPlayer.dispose();
                return true;
            }
            return false;
        }
    }
}

