/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pijukebox.player;

import org.junit.Test;
import static org.junit.Assert.*;

public class TrackDetailsTest {
    
    private final TrackDetails target;
    
    public TrackDetailsTest() {
        target = new TrackDetails("Phyrnna - Raindrops of a Dream.mp3");
    }

    /**
     * Test of getTitle method, of class TrackDetails.
     */
    @Test
    public void testGetTitle() {
        String expResult = "Raindrops of a Dream";
        String result = target.getTitle();
        assertEquals(expResult, result);
    }

    /**
     * Test of getArtist method, of class TrackDetails.
     */
    @Test
    public void testGetArtist() {
        String expResult = "Phyrnna";
        String result = target.getArtist();
        assertEquals(expResult, result);
    }

    /**
     * Test of getGenre method, of class TrackDetails.
     */
    @Test
    public void testGetGenre() {
        String expResult = "Neo-classical";
        String result = target.getGenre();
        assertEquals(expResult, result);
    }

    /**
     * Test of getAlbum method, of class TrackDetails.
     */
    @Test
    public void testGetAlbum() {
        String expResult = "The Music of Epic Battle Fantasy: Bullet Heaven & Adventure Story";
        String result = target.getAlbum();
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class TrackDetails.
     */
    @Test
    public void testEquals() {
        Object o = null;
        TrackDetails instance = new TrackDetails("Phyrnna - Shelter [piano ver].mp3");
        boolean expResult = false;
        assertEquals(expResult, target.equals(o));
        assertEquals(expResult, target.equals(instance));
    }
}
