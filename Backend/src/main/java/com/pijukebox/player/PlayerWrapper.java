package com.pijukebox.player;

import jaco.mp3.player.MP3Player;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Player wrapper.
 */
public class PlayerWrapper {

    private static Player MP3PLAYER;
// http://jacomp3player.sourceforge.net/guide.html
    // https://sourceforge.net/p/jacomp3player/code/HEAD/tree/JACo%20MP3%20Player%20v3/
<<<<<<< HEAD
        private Path songDirPath;
=======

    private final MP3Player mp3Player = new MP3Player();
    private Path songDirPath;
>>>>>>> parent of 9e696a8... My player, implements everything but volume control. Please test it
    private List<File> queue;
    private int current;

    private PlayerStatus playerStatus = new PlayerStatus();
    private TrackDetails trackDetails = new TrackDetails();

    /**
     * Instantiates a new Player wrapper.
     *
     * @param songDirPath the song dir path
     */
    public PlayerWrapper(Path songDirPath) throws IOException, FatalException, NonFatalException {
        MP3PLAYER = new Player(true);
        this.songDirPath = songDirPath;
        this.queue = new ArrayList<>();
        this.current = 0;
    }
    
    public void playCurrentSong() {
        
    }

    /**
     * Play one song.
     *
     * @param filename the filename
     */
    public void playOneSong(String filename) {
<<<<<<< HEAD
<<<<<<< HEAD
        try {
            MP3PLAYER.playTrack(new Track(Track.getDefaultMediaPath(), filename));
            this.trackDetails = new TrackDetails(filename);
            playerStatus.setCurrSong(FilenameUtils.removeExtension(filename));
            playerStatus.setCurrStatus(PlayerStatus.Status.PLAYING);
            keepSongPlaying();
        } catch (FatalException | NonFatalException ex) {
            ex.printStackTrace();
        }
=======
=======
>>>>>>> parent of 9e696a8... My player, implements everything but volume control. Please test it
        mp3Player.add(new File(songDirPath.toAbsolutePath() + File.separator + filename), false);
        mp3Player.play();
        this.trackDetails = new TrackDetails(filename);
        playerStatus.setCurrSong(FilenameUtils.removeExtension(filename));
        playerStatus.setCurrStatus(PlayerStatus.Status.PLAYING);
        keepSongPlaying();
    }

    /**
     * Play current song.
     */
    public void playCurrentSong() {
        mp3Player.add(queue.get(current));
        mp3Player.play();
        this.trackDetails = new TrackDetails(queue.get(current).getName());
        playerStatus.setCurrStatus(PlayerStatus.Status.PLAYING);
        playerStatus.setCurrSong(FilenameUtils.removeExtension(queue.get(current).getName()));
        keepSongPlaying();
<<<<<<< HEAD
>>>>>>> parent of 9e696a8... My player, implements everything but volume control. Please test it
=======
>>>>>>> parent of 9e696a8... My player, implements everything but volume control. Please test it
    }

    /**
     * Play next song.
     */
    public void playNextSong() {
        current++;
        if (current >= queue.size()) {
            current = 0;
        }
<<<<<<< HEAD
<<<<<<< HEAD
        try {
        MP3PLAYER.next();
        } catch(FatalException | NonFatalException ex) {
            ex.printStackTrace();
        }
=======
        playCurrentSong();
>>>>>>> parent of 9e696a8... My player, implements everything but volume control. Please test it
=======
        playCurrentSong();
>>>>>>> parent of 9e696a8... My player, implements everything but volume control. Please test it
    }

    /**
     * Play previous song.
     */
    public void playPreviousSong() {
        current--;
        if (current < 0) {
            current = queue.size() - 1;
        }
<<<<<<< HEAD
<<<<<<< HEAD
        try {
        MP3PLAYER.previous();
        } catch(FatalException | NonFatalException ex) {
            ex.printStackTrace();
        }
=======
        playCurrentSong();
>>>>>>> parent of 9e696a8... My player, implements everything but volume control. Please test it
=======
        playCurrentSong();
>>>>>>> parent of 9e696a8... My player, implements everything but volume control. Please test it
    }

    /**
     * Pause song.
     */
    public void pauseSong() {
<<<<<<< HEAD
<<<<<<< HEAD
        MP3PLAYER.pause();
=======
        mp3Player.pause();
>>>>>>> parent of 9e696a8... My player, implements everything but volume control. Please test it
=======
        mp3Player.pause();
>>>>>>> parent of 9e696a8... My player, implements everything but volume control. Please test it
        playerStatus.setCurrStatus(PlayerStatus.Status.PAUSED);
    }

    /**
     * Stop song.
     */
    public void stopSong() {
<<<<<<< HEAD
<<<<<<< HEAD
        MP3PLAYER.stop();
=======
        mp3Player.stop();
>>>>>>> parent of 9e696a8... My player, implements everything but volume control. Please test it
=======
        mp3Player.stop();
>>>>>>> parent of 9e696a8... My player, implements everything but volume control. Please test it
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
<<<<<<< HEAD
<<<<<<< HEAD
            try {
                MP3PLAYER.addToQueue(new Track(null, filename));
            } catch(FatalException | NonFatalException ex) {
                ex.printStackTrace();
            }
=======
>>>>>>> parent of 9e696a8... My player, implements everything but volume control. Please test it
=======
>>>>>>> parent of 9e696a8... My player, implements everything but volume control. Please test it
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
<<<<<<< HEAD
<<<<<<< HEAD
        try {
        MP3PLAYER.dequeue(new Track("", filename));
        } catch(FatalException | NonFatalException ex) {
            ex.printStackTrace();
        }
=======
>>>>>>> parent of 9e696a8... My player, implements everything but volume control. Please test it
=======
>>>>>>> parent of 9e696a8... My player, implements everything but volume control. Please test it
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

    /**
     * Gets player volume.
     *
     * @return the player volume
     */
<<<<<<< HEAD
<<<<<<< HEAD
/*    public int getPlayerVolume() {
        return MP3PLAYER.getVolume();
=======
=======
>>>>>>> parent of 9e696a8... My player, implements everything but volume control. Please test it
    public int getPlayerVolume() {
        return mp3Player.getVolume();
>>>>>>> parent of 9e696a8... My player, implements everything but volume control. Please test it
    }

    /**
     * Sets player volume.
     *
     * @param volume the volume
     */
    public void setPlayerVolume(int volume) {
        try {
            MP3PLAYER.setVolume(Math.round(volume));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Gets repeat state.
     *
     * @return the repeat state
     */
    public Boolean getRepeatState() {
<<<<<<< HEAD
<<<<<<< HEAD
        return MP3PLAYER.getRepeat();
=======
        return playerStatus.isRepeat();
>>>>>>> parent of 9e696a8... My player, implements everything but volume control. Please test it
=======
        return playerStatus.isRepeat();
>>>>>>> parent of 9e696a8... My player, implements everything but volume control. Please test it
    }

    /**
     * Gets current song.
     *
     * @return the current song
     */
    public String getCurrentSong() {
        if (!queue.isEmpty()) {
<<<<<<< HEAD
<<<<<<< HEAD
            playerStatus.setCurrSong(MP3PLAYER.getCurrentTrack());
=======
            playerStatus.setCurrSong(queue.get(current).getName());
>>>>>>> parent of 9e696a8... My player, implements everything but volume control. Please test it
=======
            playerStatus.setCurrSong(queue.get(current).getName());
>>>>>>> parent of 9e696a8... My player, implements everything but volume control. Please test it
        } else {
            playerStatus.setCurrSong("No song available");
        }
        return playerStatus.getCurrSong();
    }

    /**
     * Toggle repeat state.
     */
    public void toggleRepeatState() {
<<<<<<< HEAD
<<<<<<< HEAD
        MP3PLAYER.toggleRepeat();
=======
        boolean sw = !mp3Player.isRepeat();
        mp3Player.setRepeat(sw);
>>>>>>> parent of 9e696a8... My player, implements everything but volume control. Please test it
=======
        boolean sw = !mp3Player.isRepeat();
        mp3Player.setRepeat(sw);
>>>>>>> parent of 9e696a8... My player, implements everything but volume control. Please test it
    }

    /**
     * Toggle shuffle state.
     */
    public void toggleShuffleState() {
<<<<<<< HEAD
<<<<<<< HEAD
        MP3PLAYER.shuffle();
=======
        boolean sw = !mp3Player.isShuffle();
        mp3Player.setShuffle(sw);
>>>>>>> parent of 9e696a8... My player, implements everything but volume control. Please test it
=======
        boolean sw = !mp3Player.isShuffle();
        mp3Player.setShuffle(sw);
>>>>>>> parent of 9e696a8... My player, implements everything but volume control. Please test it
    }

    /**
     * Gets the player's queue.
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
        boolean exists = false;
        if (queue.size() > 0) {
            for (File f : queue) {
                if (f.getName().equals(filename)) {
                    exists = true;
                    break;
                }
            }
        }
<<<<<<< HEAD
<<<<<<< HEAD
        return MP3PLAYER.inQueue(t);
=======
        return exists;
>>>>>>> parent of 9e696a8... My player, implements everything but volume control. Please test it
=======
        return exists;
>>>>>>> parent of 9e696a8... My player, implements everything but volume control. Please test it
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
<<<<<<< HEAD
<<<<<<< HEAD
                if (!MP3PLAYER.isPlaying()) {
=======
                if (mp3Player.isPaused() || mp3Player.isStopped()) {
>>>>>>> parent of 9e696a8... My player, implements everything but volume control. Please test it
=======
                if (mp3Player.isPaused() || mp3Player.isStopped()) {
>>>>>>> parent of 9e696a8... My player, implements everything but volume control. Please test it
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
<<<<<<< HEAD
<<<<<<< HEAD
                MP3PLAYER.stop();
=======
            mp3Player.stop();
>>>>>>> parent of 9e696a8... My player, implements everything but volume control. Please test it
=======
            mp3Player.stop();
>>>>>>> parent of 9e696a8... My player, implements everything but volume control. Please test it
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
