package com.pijukebox.player;

import jaco.mp3.player.MP3Player;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
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
    private Thread t;

    private PlayerStatus playerStatus;
    private TrackDetails trackDetails;

    /**
     * Instantiates a new Player wrapper.
     */
    @Autowired
    public PlayerWrapper(PlayerStatus playerStatus, TrackDetails trackDetails) {
        this.playerStatus = playerStatus;
        this.trackDetails = trackDetails;
        mp3Player = new MP3Player();
        mp3Player.setVolume(playerStatus.getVolume());
        mp3Player.setRepeat(playerStatus.isRepeat());
        this.queue = new ArrayList<>();
        this.current = 0;
    }

    /**
     * Sets the path for the music folder
     *
     * @param songDirPath the song dir path
     */
    public void setMusicPath(Path songDirPath) {
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
        if (!playerStatus.GetPlayerStatus().equals(PlayerStatus.Status.PAUSED.name()) && !playerStatus.GetPlayerStatus().equals(PlayerStatus.Status.PLAYING.name())) {
            mp3Player = new MP3Player();
            mp3Player.setVolume(playerStatus.getVolume());
            mp3Player.setRepeat(playerStatus.isRepeat());
            mp3Player.add(queue.get(current));
        }
        if (!playerStatus.GetPlayerStatus().equals(PlayerStatus.Status.PLAYING.name())) {
            String songName = queue.get(current).getName();
            mp3Player.play();
            this.trackDetails = new TrackDetails(songName);
            setCurrentSong(songName);
            keepSongPlaying();
            playerStatus.setCurrStatus(PlayerStatus.Status.PLAYING);
        }
    }

    /**
     * Play next song.
     */
    public void playNextSong() {
        current = getQueueWithoutExtentions().indexOf(playerStatus.getCurrSong());
        current++;
        if (current >= queue.size()) {
            current = 0;
        }
        if (t != null) {
            t.interrupt();
            stopSong();
        }
        playerStatus.setCurrStatus(PlayerStatus.Status.STOPPED);
        playCurrentSong();

    }

    /**
     * Play previous song.
     */
    public void playPreviousSong() {
        current = getQueueWithoutExtentions().indexOf(playerStatus.getCurrSong());
        current--;
        if (current < 0) {
            current = queue.size() - 1;
        }
        if (t != null) {
            t.interrupt();
            stopSong();
        }
        playerStatus.setCurrStatus(PlayerStatus.Status.STOPPED);
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
        return playerStatus.GetPlayerStatus();
    }

    /**
     * Gets player volume.
     *
     * @return the player volume
     */
    public int getPlayerVolume() {
        return playerStatus.getVolume();
    }

    /**
     * Sets player volume.
     *
     * @param volume the volume
     */
    public void setPlayerVolume(int volume) {
        try {
            playerStatus.setVolume(Math.round(volume));
            mp3Player.setVolume(playerStatus.getVolume());
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

        return playerStatus.getCurrSong();
    }

    private void setCurrentSong(String name) {
        if (!queue.isEmpty()) {
            playerStatus.setCurrSong(FilenameUtils.removeExtension(name));
        } else {
            playerStatus.setCurrSong("No song available");
        }
    }

    public boolean getShuffleState() {
        return mp3Player.isShuffle();
    }

    /**
     * Toggle repeat state.
     */
    public void toggleRepeatState() {
        boolean sw = !playerStatus.isRepeat();
        playerStatus.setRepeat(sw);
        System.out.println("The repeat has been set to: " + sw);
        System.out.println("The repeat has been set to: " + sw);
        System.out.println("The repeat has been set to: " + sw);
        System.out.println("The repeat has been set to: " + sw);
        System.out.println("The repeat has been set to: " + sw);
        mp3Player.setRepeat(playerStatus.isRepeat());
    }

    /**
     * Toggle shuffle state.
     */
    public void toggleShuffleState() {
        boolean sw = !mp3Player.isShuffle();
        Collections.shuffle(queue);
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
        t = new Thread(() -> {
            boolean sw = true;
            while (sw) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("fasdfsafasdgfsfgserrtqwes " + !mp3Player.isPlaying());
                if (mp3Player.isPaused() || mp3Player.isStopped() || !mp3Player.isPlaying()) {
                    sw = false;
                    if ((playerStatus.getCurrStatus() != PlayerStatus.Status.PAUSED &&
                         playerStatus.getCurrStatus() != PlayerStatus.Status.STOPPED) &&
                         !playerStatus.isRepeat()) {
                        playerStatus.setCurrStatus(PlayerStatus.Status.INTERRUPTED);
                    }
                }
            }
        });
        t.start();
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

    /**
     * @return Songs in queue without extentions
     */
    public List<String> getQueueWithoutExtentions()
    {
        List<String> songs = new ArrayList<>();
        for(String song : getQueue())
        {
            songs.add(FilenameUtils.removeExtension(song));
        }
        return songs;
    }
}
