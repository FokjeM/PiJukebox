package com.pijukebox.player;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerStatus {

    private Status currStatus = Status.STOPPED;
    private boolean repeat;
    private String currSong;

    /**
     * Get player status string.
     *
     * @return A status string
     */
    public String GetPlayerStatus() {
        switch (this.currStatus) {
            case PLAYING:
                return Status.PLAYING.name();
            case PAUSED:
                return Status.PAUSED.name();
            case STOPPED:
                return Status.STOPPED.name();
            case INTERRUPTED:
                return Status.INTERRUPTED.name();
            default:
                return "NO VALID STATUS DETECTED.";
        }
    }

    public enum Status {
        PLAYING, PAUSED, STOPPED, INTERRUPTED
    }
}
