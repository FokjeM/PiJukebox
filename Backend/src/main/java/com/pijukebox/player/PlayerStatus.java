package com.pijukebox.player;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class PlayerStatus {

    private Status currStatus = PlayerStatus.Status.STOPPED;
    private boolean repeat = false;
    private String currSong = "";
    private int volume = 50;


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
