/*
 * Copyright (C) 2019 Riven
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.PiJukeboxPlayer;

import java.io.IOException;
import com.mpatric.mp3agic.*;

/**
 * Quick and dirty testing with a PiJukeboxPlayer class!
 * @author Riven
 */
public class PiJukeboxPlayer {
    public static Player player;
    public static Queue queue;
    private static Track track;
    public static ErrorLogger log;

    public static void main(String[] args) throws IOException {
        PiJukeboxPlayer m = new PiJukeboxPlayer();
        /*try {
            m.play();
        } catch (FatalException | NonFatalException ex) {
            boolean writeLog = log.writeLog(ex, true);
        }*/
    }
    
    public PiJukeboxPlayer() throws IOException {
        try {
            queue = new Queue();
            player = new Player(true, true, queue);//if this works, annoy the shit out of EVERYONE.
            log = player.getLogger();
            track = new Track("", "Phyrnna - Shelter [piano ver].mp3");
            queue.put(1, track);
            player.addToQueue(track);
            player.playTrack(track);
        } catch (NonFatalException | FatalException ex) {
            log.writeLog(ex, true);
        }
    }
    
    public void play() throws FatalException, NonFatalException{
        player.playTrack(track);
    }
}
