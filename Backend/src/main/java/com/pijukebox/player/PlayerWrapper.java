package com.pijukebox.player;

import jaco.mp3.player.MP3Player;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PlayerWrapper {

    // http://jacomp3player.sourceforge.net/guide.html
    // https://sourceforge.net/p/jacomp3player/code/HEAD/tree/JACo%20MP3%20Player%20v3/

    private final MP3Player mp3Player = new MP3Player();
    private Path songDirPath;
    private List<File> queue;
    private int current;

    private PlayerStatus playerStatus = new PlayerStatus();
    private TrackDetails trackDetails = new TrackDetails();

    public PlayerWrapper(Path songDirPath) {
        this.songDirPath = songDirPath;
        this.queue = new ArrayList<>();
        this.current = 0;
    }

    public void playOne(String filename) {
        mp3Player.add(new File(songDirPath.toAbsolutePath() + "\\" + filename), false);
        mp3Player.play();
        this.trackDetails = new TrackDetails(filename);
        playerStatus.setCurrSong(FilenameUtils.removeExtension(filename));
        playerStatus.setCurrStatus(PlayerStatus.Status.PLAYING);
        keepSongPlaying();
    }

    public void playCurrent() {
        mp3Player.add(queue.get(current));
        mp3Player.play();
        this.trackDetails = new TrackDetails(queue.get(current).getName());
        playerStatus.setCurrStatus(PlayerStatus.Status.PLAYING);
        playerStatus.setCurrSong(FilenameUtils.removeExtension(queue.get(current).getName()));
        keepSongPlaying();
    }

    public void playNext() {
        current++;
        if (current >= queue.size()) {
            current = 0;
        }
        playCurrent();
    }

    public void playPrev() {
        current--;
        if (current < 0) {
            current = queue.size() - 1;
        }
        playCurrent();
    }

    public void pauseSong() {
        mp3Player.pause();
        playerStatus.setCurrStatus(PlayerStatus.Status.PAUSED);
    }

    public void stopSong() {
        mp3Player.stop();
        playerStatus.setCurrStatus(PlayerStatus.Status.STOPPED);
    }

    public String getStatus() {
        if (!getCurrentSong().isEmpty()) {
            return playerStatus.getStatus();
        }
        return "";
    }

    public void setVolume(Float volume) {
        try {
            mp3Player.setVolume(Math.round(volume));
//            Audio.setMasterOutputVolume(volume);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Boolean getRepeat() {
        return playerStatus.isRepeat();
    }

    public String getCurrentSong() {
        return playerStatus.getCurrSong();
    }

    public void toggleRepeat() {
        boolean sw = !mp3Player.isRepeat();
        mp3Player.setRepeat(sw);
    }

    public void shuffle() {
        boolean sw = !mp3Player.isShuffle();
        mp3Player.setShuffle(sw);
    }

    public List<String> getQueue() {
        List<String> currQueue = new ArrayList<>();
        for (File file : queue) {
            currQueue.add(file.getName());
        }
        return currQueue;
    }

    public String getArtist() {
        return trackDetails.getArtist();
    }

    public String getGenre() {
        return trackDetails.getGenre();
    }

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
        return exists;
    }

    private void removeSongFromQueue(String filename) {
        for (int i = 0; i < queue.size(); i++) {
            if (queue.get(i).getName().equals(filename)) {
                queue.remove(i);
                break;
            }
        }
    }

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
}
