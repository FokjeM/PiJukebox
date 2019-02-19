package com.pijukebox.player;

import jaco.mp3.player.MP3Player;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Player wrapper.
 */
@Component
public class PlayerWrapper {

    // http://jacomp3player.sourceforge.net/guide.html
    // https://sourceforge.net/p/jacomp3player/code/HEAD/tree/JACo%20MP3%20Player%20v3/

    private MP3Player mp3Player;
    private Path songDirPath;
    private List<File> queue;
    private int current;

    @Autowired
    private PlayerStatus playerStatus;
    @Autowired
    private TrackDetails trackDetails;

    /**
     * Instantiates a new Player wrapper.
     *
     */
    public PlayerWrapper() {
        mp3Player = new MP3Player();
        this.queue = new ArrayList<>();
        this.current = 0;
    }

    /**
     * Sets the path for the music folder
     *
     * @param songDirPath the song dir path
     *
     */
    public void setMusicPath(Path songDirPath)
    {
        this.songDirPath = songDirPath;
    }
    /**
     * Play one song.
     *
     * @param filename the filename
     */
    public void playOneSong(String filename) {
        mp3Player.add(new File(songDirPath.toAbsolutePath() + "\\" + filename), false);
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
        mp3Player = new MP3Player();
        System.out.println("The song playing is: " + queue.get(current).getName());
        mp3Player.add(queue.get(current));
        mp3Player.play();
        this.trackDetails = new TrackDetails(queue.get(current).getName());
        playerStatus.setCurrStatus(PlayerStatus.Status.PLAYING);
        playerStatus.setCurrSong(FilenameUtils.removeExtension(queue.get(current).getName()));
        keepSongPlaying();
    }

    /**
     * Play next song.
     */
    public void playNextSong() {
        current++;
        if (current >= queue.size()) {
            current = 0;
        }
        stopSong();
        playCurrentSong();

    }

    /**
     * Play previous song.
     */
    public void playPreviousSong() {
        current--;
        if (current < 0) {
            current = queue.size() - 1;
        }
        stopSong();
        playCurrentSong();
    }

    /**
     * Pause song.
     */
    public void pauseSong() {
        mp3Player.pause();
        playerStatus.setCurrStatus(PlayerStatus.Status.PAUSED);
    }

    /**
     * Stop song.
     */
    public void stopSong() {
        mp3Player.stop();
        playerStatus.setCurrStatus(PlayerStatus.Status.STOPPED);
    }

    /**
     * Add song to playlist.
     *
     * @param filename the filename
     */
    public void addSongToPlaylist(String filename) {
        if (!inPlaylist(filename)) {
            queue.add(new File(songDirPath.toAbsolutePath() + "\\" + filename));
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
    public int getPlayerVolume() {
        return mp3Player.getVolume();
    }

    /**
     * Sets player volume.
     *
     * @param volume the volume
     */
    public void setPlayerVolume(int volume) {
        try {
            mp3Player.setVolume(Math.round(volume));
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
        return playerStatus.isRepeat();
    }

    /**
     * Gets current song.
     *
     * @return the current song
     */
    public String getCurrentSong() {
        if (!queue.isEmpty()) {
            playerStatus.setCurrSong(queue.get(current).getName());
        } else {
            playerStatus.setCurrSong("No song available");
        }
        return playerStatus.getCurrSong();
    }

    /**
     * Toggle repeat state.
     */
    public void toggleRepeatState() {
        boolean sw = !mp3Player.isRepeat();
        mp3Player.setRepeat(sw);
    }

    /**
     * Toggle shuffle state.
     */
    public void toggleShuffleState() {
        boolean sw = !mp3Player.isShuffle();
        mp3Player.setShuffle(sw);
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
        String artist = trackDetails.getArtist();
        if (artist != null && artist.isEmpty()) {
            return artist;
        }
        return "No artist tag found.";
    }

    /**
     * Gets genre of current song.
     *
     * @return the genre
     */
    public String getGenre() {
        String genre = trackDetails.getGenre();
        if (genre != null && genre.isEmpty()) {
            return genre;
        }
        return "No genre tag found.";
    }

    /**
     * Gets album of current song.
     *
     * @return the album
     */
    public String getAlbum() {
        String album = trackDetails.getAlbum();
        if (album != null && album.isEmpty()) {
            return album;
        }
        return "No album tag found.";
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
        return exists;
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
                if (mp3Player.isPaused() || mp3Player.isStopped()) {
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
            mp3Player.stop();
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
