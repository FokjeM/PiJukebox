package com.pijukebox.player;

//import jaco.mp3.player.MP3Player;
import com.PiJukeboxPlayer.*;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Player wrapper.
 */
public class PlayerWrapper {

    // http://jacomp3player.sourceforge.net/guide.html
    // https://sourceforge.net/p/jacomp3player/code/HEAD/tree/JACo%20MP3%20Player%20v3/

    private final Player mp3Player = new Player(true);
    private Path songDirPath;
    private List<File> queue;
    private int current;

    private PlayerStatus playerStatus = new PlayerStatus();
    private TrackDetails trackDetails = new TrackDetails();

    /**
     * Instantiates a new Player wrapper.
     *
     * @param songDirPath the song dir path
     */
    public PlayerWrapper(Path songDirPath) {
        this.songDirPath = songDirPath;
        this.queue = new ArrayList<>();
        this.current = 0;
    }

    /**
     * Play one song.
     *
     * @param filename the filename
     */
    public void playOneSong(String filename) {
        try {
            mp3Player.playTrack(new Track(Track.getDefaultMediaPath(), filename));
            this.trackDetails = new TrackDetails(filename);
            playerStatus.setCurrSong(FilenameUtils.removeExtension(filename));
            playerStatus.setCurrStatus(PlayerStatus.Status.PLAYING);
            keepSongPlaying();
        } catch (FatalException | NonFatalException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Play next song.
     */
    public void playNextSong() {
        current++;
        if (current >= queue.size()) {
            current = 0;
        }
        try {
        mp3Player.next();
        } catch(FatalException | NonFatalException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Play previous song.
     */
    public void playPreviousSong() {
        current--;
        if (current < 0) {
            current = queue.size() - 1;
        }
        try {
        mp3Player.previous();
        } catch(FatalException | NonFatalException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Pause song.
     */
    public void pauseSong() {
        try {
        mp3Player.pause();
        } catch(NonFatalException ex) {
            ex.printStackTrace();
        }
        playerStatus.setCurrStatus(PlayerStatus.Status.PAUSED);
    }

    /**
     * Stop song.
     */
    public void stopSong() {
        try {
        mp3Player.stop();
        } catch(NonFatalException ex) {
            ex.printStackTrace();
        }
        playerStatus.setCurrStatus(PlayerStatus.Status.STOPPED);
    }

    /**
     * Add song to playlist.
     *
     * @param filename the filename
     */
    public void addSongToPlaylist(String filename) {
        if (!inPlaylist(filename)) {
            queue.add(new File(songDirPath.toAbsolutePath() + File.separator + filename));
            try {
                mp3Player.addToQueue(new Track(null, filename));
            } catch(FatalException | NonFatalException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Remove song from playlist.
     *
     * @param filename the filename
     */
    public void removeSongFromPlaylist(String filename) {
        if (inPlaylist(filename)) {
            removeSongFromQueue(filename);
        }
        try {
        mp3Player.dequeue(new Track("", filename));
        } catch(FatalException | NonFatalException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Gets player status.
     *
     * @return the player status
     */
    public String getPlayerStatus() {
        if (!getCurrentSong().isEmpty()) {
            return playerStatus.GetPlayerStatus();
        }
        return "";
    }
//Volume controls are currently broken
    /**
     * Gets player volume.
     *
     * @return the player volume
     */
/*    public int getPlayerVolume() {
        return mp3Player.getVolume();
    }

    /**
     * Sets player volume.
     *
     * @param volume the volume
     */
 /*   public void setPlayerVolume(int volume) {
        try {
            mp3Player.setVolume(Math.round(volume));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }*/

    /**
     * Gets repeat state.
     *
     * @return the repeat state
     */
    public Boolean getRepeatState() {
        return mp3Player.getRepeat();
    }

    /**
     * Gets current song.
     *
     * @return the current song
     */
    public String getCurrentSong() {
        if (!queue.isEmpty()) {
            playerStatus.setCurrSong(mp3Player.getCurrentTrack());
        } else {
            playerStatus.setCurrSong("No song available");
        }
        return playerStatus.getCurrSong();
    }

    /**
     * Toggle repeat state.
     */
    public void toggleRepeatState() {
        mp3Player.toggleRepeat();
    }

    /**
     * Toggle shuffle state.
     */
    public void toggleShuffleState() {
        mp3Player.shuffle();
    }

    /**
     * Gets the player's queue.
     * Should suffice, my code was only adding it to my own queue as well as this.
     *
     * @return the queue
     */
    public List<String> getPlayerQueue() {
        List<String> currQueue = new ArrayList<>();
        for (File file : queue) {
            currQueue.add(file.getName());
        }
        return currQueue;
    }

    /**
     * Gets artist of current song.
     *
     * @return the artist
     */
    public String getArtist() {
        return trackDetails.getArtist();
    }

    /**
     * Gets genre of current song.
     *
     * @return the genre
     */
    public String getGenre() {
        return trackDetails.getGenre();
    }

    /**
     * Gets album of current song.
     *
     * @return the album
     */
    public String getAlbum() {
        return trackDetails.getAlbum();
    }

    private boolean inPlaylist(String filename) {
        Track t = null;
        try {
            t = new Track("", filename);
        } catch (FatalException | NonFatalException ex) {
            ex.printStackTrace();
        }
        return mp3Player.inQueue(t);
    }

    /**
     * Removes a song from the queue
     *
     * @param filename the filename
     */
    private void removeSongFromQueue(String filename) {
        for (int i = 0; i < queue.size(); i++) {
            if (queue.get(i).getName().equals(filename)) {
                queue.remove(i);
                break;
            }
        }
    }

    /**
     * Method to have the player keep track of the player status.
     */
    private void keepSongPlaying() {
        new Thread(() -> {
            boolean sw = true;
            while (sw) {
                if (!mp3Player.isPlaying()) {
                    sw = false;
                    if (playerStatus.getCurrStatus() != PlayerStatus.Status.PAUSED && playerStatus.getCurrStatus() != PlayerStatus.Status.STOPPED) {
                        playerStatus.setCurrStatus(PlayerStatus.Status.INTERRUPTED);
                    }
                }
            }
        }).start();
    }

    /**
     * Clear the player´s queue.
     *
     * @param stopCurrentSong stop current song
     */
    public void clearQueue(Boolean stopCurrentSong) {
        if (stopCurrentSong) {
            try {
                mp3Player.stop();
            } catch (NonFatalException ex) {
                ex.printStackTrace();
            }
        }
        queue.clear();
    }

    /**
     * @return Songs in queue
     */
    public List<String> getQueue() {
        List<String> stringQueue = new ArrayList<>();
        for (File song : queue) {
            stringQueue.add(song.getName());
        }
        return stringQueue;
    }
}
