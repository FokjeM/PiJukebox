package com.PiJukebox;
import java.util.*;

public class Player {
    
    /*
     * This class is supposed to handle the playing of files.
     * It exposes a few methods that leave little to no room for external mutations.
    */
    
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
        
    }
}
