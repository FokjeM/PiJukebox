package com.pijukebox.player;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerStatus {

    private Status currStatus = Status.STOPPED;
    private String currSong;

    /**
     * Get player status string.
     *
     * @return A status string
     */
    public String GetPlayerStatus() {
        switch (this.currStatus) {
            case PLAYING:
                return "PLAYING";
            case PAUSED:
                return "PAUSED";
            case STOPPED:
                return "STOPPED";
            case INTERRUPTED:
                return "INTERRUPTED";
            default:
                return "ERROR";
        }
    }

    public enum Status {
        PLAYING, PAUSED, STOPPED, INTERRUPTED
    }
}
