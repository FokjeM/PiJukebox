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
import java.util.Scanner;

/**
 * Quick and dirty testing with a PiJukeboxPlayer class!
 *
 * @author Riven
 */
public class PiJukeboxPlayer {

    public static Player player;
    public static Queue queue;
    private static Track track;
    public static ErrorLogger log;

    public static void main(String[] args) throws IOException {
        PiJukeboxPlayer pjb = new PiJukeboxPlayer();
        pjb.run();
        System.exit(0);
    }

    public PiJukeboxPlayer() throws IOException {
        log = new ErrorLogger();
    }

    public void run() {
        try {
            queue = new Queue();
            player = new Player(true, false, queue, log);
            track = new Track("", "Phyrnna - Shelter [piano ver].mp3");
            player.addToQueue(new Track("D:\\School\\Advanced Java\\PiJukebox\\Player\\Music", "Phyrnna - Raindrops of a Dream.mp3"));
            System.out.println("Added Raindrops to queue");
            player.addToQueue(track);
            System.out.println("Added Shelter to queue");
            player.next();//Should handle automatically playing both from here, endlessly. Otherwise, the Thread has exited... But this should get logged
            System.out.println("Hit next, playing track 1");
            Thread.sleep(2000);
            player.pause();
            System.out.println("Paused");
            Thread.sleep(2000);
            player.resume();
            System.out.println("Resumed");
            Thread.sleep(2000);
            player.previous();
            System.out.println("Playing previous");
            Thread.sleep(2000);
            player.next();
            System.out.println("Next pre-wraparound");
            Thread.sleep(2000);
            player.next();
            System.out.println("Next, wrapped around");
            new Scanner(System.in).next("quit");
            System.out.println("Stopped, total success!");
        } catch (NonFatalException nfe) {
            log.writeLog(nfe, false);
        } catch (FatalException fe) {
            log.writeLog(fe, true);
        } catch (Exception ex) {
            log.writeLog(new FatalException("MediaPlayer crashed!", ex), true);
        }
    }
}
