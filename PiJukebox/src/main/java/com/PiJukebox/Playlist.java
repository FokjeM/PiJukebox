/*
 * Copyright (C) 2018 Riven
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
package com.PiJukebox;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Playlist class to hold a collection of Tracks in a specific order. Builds
 * upon a Map<Integer, Track> and thus Map.Entry<Integer,Track>
 *
 * --REPLACE String VALUE WITH Track VALUE ONCE IMPLEMENTED--
 */
public class Playlist implements Map<Integer, String>, Cloneable, Serializable {

    /**
     * The array holding the actual PlaylistEntry objects
     */
    private PlaylistEntry<Integer, String>[] entries;
    /**
     * The current size of this Playlist
     */
    private int size;

    /**
     * Instantiates a playlist using an iterable object
     *
     * @param list MUST be iterable
     */
    public Playlist(Iterable<?> list) {
        int length = 0;
        Iterator li = list.iterator();
        while (li.hasNext()) {
            length++;
            li.next();
        }
        this.entries = new PlaylistEntry[length];
        this.size = length;
    }


    /**
     * This implementation of size() for the Map
     *
     * @return returns the amount of PlaylistEntry objects in this Playlist
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * This implementation of isEmpty() for the Map
     *
     * @return false if any PlaylistEntry objects are present, true otherwise
     */
    @Override
    public boolean isEmpty() {
        return this.size <= 0;
    }

    /**
     * This implementation of containsKey() for the Map
     *
     * @param key Integer key object to search for
     * @return true if the key is present at least once, false otherwise
     */
    public boolean containsKey(Integer key) {
        for (PlaylistEntry e : this.entries) {
            if (e.getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This implementation of containsValue() for the Map
     *
     * @param value the String FILEPATH to search for
     * @return true if the value is present at least once, false otherwise
     */
    public boolean containsValue(String value) {
        for (PlaylistEntry e : this.entries) {
            if (e.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the value belonging to an Integer key object
     *
     * @param key the key to search for in this Playlist
     * @return the value associated with this key
     */
    public String get(Integer key) {
        for (PlaylistEntry e : this.entries) {
            if (e.getKey().equals(key)) {
                //Cast to string due to IDE errors; test without cast later
                return (String) e.getValue();
            }
        }
        return null;
    }

    /**
     * Edits or adds a PlaylistEntry in the Playlist containing the specified
     * key-value pair
     *
     * @param key the Integer key object for the PlaylistEntry
     * @param value the String FILEPATH for the PlaylistEntry
     * @return The previous value for this key, or null if it didn't exist yet
     */
    @Override
    public String put(Integer key, String value) {
        if (this.containsKey(key)) { //If the key is present, return the result of setValue(value)
            for (PlaylistEntry e : this.entries) {
                if (e.getKey().equals(key)) {
                    //Cast to string due to IDE errors; test without cast later
                    return (String) e.setValue(value);
                }
            }
        } else { //Else, create a new array with the necessary Length
            PlaylistEntry<Integer, String>[] newEntries = new PlaylistEntry[key];
            //copy the current array into it
            for (PlaylistEntry e : entries) {
                newEntries[(Integer) e.getKey()] = e;
            }
            //Add the new entry
            newEntries[key] = new PlaylistEntry(key, value);
            //and change the reference pointer of entries
            this.entries = newEntries;
            this.size = key;
        }
        //If nothing was returned yet, the key did not exist
        return null;
    }

    /**
     * Remove an item from this Playlist specified by a key
     *
     * @param key The key for the item to be removed
     * @return Returns the value of the item to be removed
     */
    public String remove(Integer key) {
        String val = null;
        for (PlaylistEntry e : this.entries) {
            if (e.getKey().equals(key)) {
                val = (String) e.getValue();
                e = null;
            }
        }
        return val;
    }

    /**
     * Add an entire map to to this Playlist, as long as the map shares the same
     * data types for its key-value pairs
     *
     * @param m the map to add
     */
    @Override
    public void putAll(Map<? extends Integer, ? extends String> m) {
        int newSize = this.size + m.size();
        PlaylistEntry<Integer, String>[] newEntries = new PlaylistEntry[newSize];
        //copy the current array into it
        for (PlaylistEntry e : this.entries) {
            newEntries[(Integer) e.getKey()] = e;
        }
        //Add the new entries
        Set<? extends Entry<? extends Integer, ? extends String>> additions = m.entrySet();
        additions.forEach((e) -> {
            newEntries[e.getKey()] = (PlaylistEntry<Integer, String>) e;
        });
        //Update the pointer
        this.entries = newEntries;
    }

    /**
     * Clears the playlist and sets the size to 0
     */
    @Override
    public void clear() {
        this.entries = new PlaylistEntry[0];
        this.size = 0;
    }

    /**
     * Get a set containing all of the keys
     *
     * @return a TreeSet with the keys in this Playlist
     */
    @Override
    public Set<Integer> keySet() {
        Set<Integer> set = new TreeSet<>();
        for (PlaylistEntry e : entries) {
            set.add((Integer) e.getKey());
        }
        return set;
    }

    /**
     * Get a collection containing all of the values in this Playlist
     *
     * @return an ArrayList with all of the String FILEPATHS
     */
    @Override
    public Collection<String> values() {
        ArrayList<String> coll = new ArrayList<>();
        for (PlaylistEntry e : entries) {
            coll.add((String) e.getValue());
        }
        return coll;
    }

    /**
     * Get the PlaylistEntry objects in this Playlist as a Set
     *
     * @return a TreeSet containing all of the PlaylistEntry objects
     */
    @Override
    public Set<Entry<Integer, String>> entrySet() {
        Set<Entry<Integer, String>> set = new TreeSet<>();
        set.addAll(Arrays.asList(entries));
        return set;
    }

    /**
     * Alias for the required override of containsKey
     *
     * @param key the Integer key to check for
     * @return the result of the Integer variant of this method
     */
    @Override
    public boolean containsKey(Object key) {
        if (key.getClass() == Integer.class) {
            //Cast to ensure the correct variant of this method is called
            return containsKey((Integer) key);
        } else {
            throw new UnsupportedOperationException("Whoops, we use Integer keys here!");
        }
    }

    /**
     * Alias for the required override of containsValue
     *
     * @param value the String value to check for
     * @return the result of the String variant of this method
     */
    @Override
    public boolean containsValue(Object value) {
        if (value.getClass() == String.class) {
            //Cast to ensure the correct variant of this method is called
            return containsValue((String) value);
        } else {
            throw new UnsupportedOperationException("Whoops, we use Integer keys here!");
        }
    }

    /**
     * Alias for the required override of get
     *
     * @param key the key to get the value from
     * @return the String FILEPATH that belongs to this key
     */
    @Override
    public String get(Object key) {
        if (key.getClass() == Integer.class) {
            //Cast to ensure the correct variant of this method is called
            return get((Integer) key);
        } else {
            throw new UnsupportedOperationException("Whoops, we use Integer keys here!");
        }
    }

    /**
     * Alias for the required override of remove
     *
     * @param key the key of the value to remove
     * @return the removed value
     */
    @Override
    public String remove(Object key) {
        if (key.getClass() == Integer.class) {
            //Cast to ensure the correct variant of this method is called
            return remove((Integer) key);
        } else {
            throw new UnsupportedOperationException("Whoops, we use Integer keys here!");
        }
    }
    /**
     * Internal class to return the matching implementation of Map.Entry
     *
     * @param <Integer> an Integer key (key must be an object)
     * @param <String> The value to map to the key; location path for the music
     * file
     */
    public class PlaylistEntry<Integer, String> implements Map.Entry<Integer, String> {
        
        private final Integer key;
        private String value;
        
        /**
         * Instantiate a PlaylistEntry
         *
         * @param key The Integer key object
         * @param value the String path to the music file
         */
        public PlaylistEntry(Integer key, String value) {
            this.key = key;
            this.value = value;
        }
        
        /**
         * Get the key for this PlaylistEntry
         *
         * @return returns the key as an Integer object
         */
        @Override
        public Integer getKey() {
            return this.key;
        }
        
        /**
         * Get the value of this PlaylistEntry
         *
         * @return The FILEPATH for the target file
         */
        @Override
        public String getValue() {
            return this.value;
        }
        
        /**
         * Set the value of this PlaylistEntry
         *
         * @param value The value to set it to. Must be a FILEPATH
         * @return Returns the old value for this key
         */
        @Override
        public String setValue(String value) {
            String oldValue = this.value;
            this.value = value;
            return oldValue;
        }
        
    }

}
