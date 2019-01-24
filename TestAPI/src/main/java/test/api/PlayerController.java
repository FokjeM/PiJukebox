package test.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import test.player.Player;
import test.player.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLDecoder;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestParam;
import test.player.FatalException;
import test.player.NonFatalException;


@RestController
@RequestMapping("/api/v1/player")
public class PlayerController {
    
    private final Player player;
    private Track track;

    @Autowired
    public PlayerController() throws FatalException {
        try {
            player = new Player();
        } catch (IOException | NonFatalException ex) {
            ex.printStackTrace(System.err);
            throw new FatalException("Couldn't instantiate Player!", ex);
        } catch (FatalException fe) {
            fe.printStackTrace(System.err);
            throw fe;
        }
    }

    /**
     * Creates a {@link Track Track} from the specified filename and proceeds to
     * call {@link Player#playTrack(Track) playTrack(Track t)} with the track.
     * @param filename
     * @return The title of the currently playing track
     */
    @GetMapping("/play/track/filename")
    public String playTrackByFilename(@RequestParam("filename") String filename) throws FatalException {
        try {
            track = new Track(URLDecoder.decode(filename, "UTF-8"));
            player.playTrack(track);
        } catch (UnsupportedEncodingException | NonFatalException ex) {
            ex.printStackTrace(System.err);
        } catch (FatalException fe) {
            fe.printStackTrace(System.err);
            throw fe;
        }
        return track.getTitle();
    }
    /*Current implementation in my controller class (MartinWithController branch) is prepared to implement ID as well as filename. I recommend either escaping filenames or prefferring ID fields.
Also added an empty method that can add all tracks in an album, but I haven't implemented any database calls yet*/
    /**
     * Play a track based on its ID
     * @param id the track ID to play
     * @return The title of the currently playing track
     */
    @GetMapping("/play/track/id")
    public String playTrackByID(@RequestParam("id") Long id) throws FatalException {
        try {
            /*Perform the correct calls on the JPA for getting the filename by ID
            The SQL would be:
                SELECT filename FROM track WHERE track.id = id
            The SimpleTrack contains this info*/
            String filename = "";
            track = new Track(URLDecoder.decode(filename, "UTF-8"));
            player.playTrack(track);
        } catch (UnsupportedEncodingException | NonFatalException e) {
            e.printStackTrace(System.err);
        } catch (FatalException fe) {
            fe.printStackTrace(System.err);
            throw fe;
        }
        return track.getTitle();
    }
    
    /**
     * Add a Track to the Queue for autoplaying
     * @param filename the filename of the track to add
     * @return true if the track could be added, false otherwise.
     */
    @GetMapping("/add/track/filename")
    public boolean addTrackByFilename(@RequestParam("filename") String filename) throws FatalException {
        try {
            track = new Track(URLDecoder.decode(filename, "UTF-8"));
            player.addToQueue(track);
        } catch (UnsupportedEncodingException | NonFatalException ex) {
            ex.printStackTrace(System.err);
            return false;
        } catch (FatalException fe) {
            fe.printStackTrace(System.err);
            throw fe;
        }
        return true;
    }
    
    /**
     * Add a track to the queue for autoplaying
     * @param id the ID to find the track by in the database
     * @return true if the track could be added, false otherwise
     */
    @GetMapping("/add/track/id")
    public boolean addTrackByID(@RequestParam("id") Long id) throws FatalException {
        try {
            /*Perform the correct calls on the JPA for getting the filename by ID
            The SQL would be:
                SELECT filename FROM track WHERE track.id = id
            The SimpleTrack contains this info*/
            String filename = "";
            track = new Track(URLDecoder.decode(filename, "UTF-8"));
            player.addToQueue(track);
        } catch (UnsupportedEncodingException | NonFatalException ex) {
            ex.printStackTrace(System.err);
            return false;
        } catch (FatalException fe) {
            fe.printStackTrace(System.err);
            throw fe;
        }
        return true;
    }
    
    /**
     * Play an entire album
     * @param filenames the filenames to play per song
     * @return the track listing of the currently playing album
     */
    @GetMapping("/play/tracks/filenames")
    public String playTracks(@RequestParam Map<String,String> filenames) throws FatalException {
        //Referencing from a lambda function, so it has to be effectively final
        StringBuilder ret = new StringBuilder();
        filenames.forEach((String key, String value) -> {
            try {
                Track t = new Track(value);
                player.addToQueue(t);
                //Thus we append a newline and the track title
                ret.append("\r\n");
                ret.append(t.getTitle());
            } catch (NonFatalException | FatalException ex) {
                ex.printStackTrace(System.err);
            }
        });
        try {
            player.next();
        } catch (NonFatalException nfe) {
            nfe.printStackTrace(System.err);
        } catch (FatalException fe) {
            fe.printStackTrace(System.err);
            throw fe;
        }
        return ret.toString();
    }
    
    /**
     * Play an entire album
     * @param id the ID of the album to play
     * @return the track listing of the currently playing album
     */
    @GetMapping("/play/album/id")
    public String playAlbumByID(@RequestParam("id") long id) throws FatalException {
        //Referencing from a lambda function, so it has to be effectively final
        StringBuilder ret = new StringBuilder();
        /*Perform some database magic to get a list of tracks here*/
        
        /*Assuming something similar to the HashSet is given from the query:
        tracks.forEach((SimpleTrack track) -> {
            try {
                Track t = new Track(track.filename);
                player.addToQueue(t);
                //Thus we append a newline and the track title
                ret.append("\r\n");
                ret.append(t.getTitle());
            } catch (NonFatalException | FatalException ex) {
                ex.printStackTrace(System.err);
            }
        });*/
        try {
            player.next();
        } catch (NonFatalException nfe) {
            nfe.printStackTrace(System.err);
        } catch (FatalException fe) {
            fe.printStackTrace(System.err);
            throw fe;
        }
        return ret.toString();
    }
    
    @GetMapping("/next")
    public void next() throws FatalException{
        try {
            player.next();
        } catch (NonFatalException nfe) {
            nfe.printStackTrace(System.err);
        } catch (FatalException fe) {
            fe.printStackTrace(System.err);
            throw fe;
        }
    }
    
    @GetMapping("/previous")
    public void previous() throws FatalException{
        try {
            player.previous();
        } catch (NonFatalException nfe) {
            nfe.printStackTrace(System.err);
        } catch (FatalException fe) {
            fe.printStackTrace(System.err);
            throw fe;
        }
    }
    
    @GetMapping("/pause")
    public void pause() {
        player.pause();
    }
    
    @GetMapping("resume")
    public void resume() {
        player.resume();
    }
    
    @GetMapping("/stop")
    public void stop() {
        player.stop();
    }
    
    @GetMapping("/get/r")
    public boolean getRepeat() {
        return player.getRepeat();
    }
    
    @GetMapping("/get/ro")
    public boolean getRepeatOne() {
        return player.getRepeatOne();
    }
    
    @GetMapping("/toggle/r")
    public boolean toggleRepeat() {
        return player.toggleRepeat();
    }
    
    @GetMapping("/toggle/ro")
    public boolean toggleRepeatOne() {
        return player.toggleRepeatOne();
    }
}
