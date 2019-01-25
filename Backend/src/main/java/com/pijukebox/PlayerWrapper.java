package com.pijukebox;

import jaco.mp3.player.MP3Player;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.nio.file.Path;

public class PlayerWrapper {

    // http://jacomp3player.sourceforge.net/guide.html

    private final MP3Player mp3Player = new MP3Player();
    private Path songDirPath;
    private Thread t;

    private PlayerStatus playerStatus = new PlayerStatus();

    public PlayerWrapper(Path songDirPath) {
        this.songDirPath = songDirPath;
    }

    public void playOne(String file) {
        clearQueue();
        mp3Player.addToPlayList(new File(songDirPath.toAbsolutePath() + "\\" + file));
        mp3Player.play();
        playerStatus.setCurrSong(FilenameUtils.removeExtension(file));
        playerStatus.setCurrStatus(PlayerStatus.Status.PLAYING);

        t = new Thread(() -> {
            boolean sw = true;
            while (sw) {
                if (mp3Player.isPaused() || mp3Player.isStopped()) {
                    sw = false;
                    if (playerStatus.getCurrStatus() != PlayerStatus.Status.PAUSED && playerStatus.getCurrStatus() != PlayerStatus.Status.STOPPED) {
                        playerStatus.setCurrStatus(PlayerStatus.Status.INTERRUPTED);
                    }
                }
            }
        });
        t.start();
    }

    public void playNext() {
        mp3Player.skipForward();
    }

    public void playPrev() {
        mp3Player.skipBackward();
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
        return playerStatus.getStatus();
    }

    public Boolean getRepeat() {
        return playerStatus.isRepeat();
    }

    public String getCurrentSong() {
        return playerStatus.getCurrSong();
    }

    public void toggleRepeat() {
        mp3Player.setRepeat(!mp3Player.isRepeat());
    }

    public void shuffle() {
        mp3Player.setShuffle(!mp3Player.isShuffle());
    }

//    public void playMultiple(String[] files) {
//        for (String f : files) {
//            File file = new File(f);
//            mp3Player.addToPlayList(file);
//        }
//        mp3Player.play();
//    }
//

    private void clearQueue() {
        mp3Player.stop();
        mp3Player.getPlayList().clear();
    }
}
