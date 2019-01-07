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
 * Queue class to hold a collection of Tracks in a specific order. Builds
 * upon a Map<Integer, Track> and thus Map.Entry<Integer,Track>
 */
public class Queue implements Map<Integer, Track>, Cloneable, Serializable {

    /**
     * The array holding the actual QueueEntry objects
     */
    private QueueEntry<Integer, Track>[] entries;
    /**
     * The current size of this Queue
     */
    private int size;

    /**
     * Instantiates a playlist using an iterable object
     *
     * @param list MUST be iterable
     */
    public Queue(Iterable<?> list) {
        int length = 0;
        Iterator li = list.iterator();
        while (li.hasNext()) {
            length++;
            li.next();
        }
        this.entries = new QueueEntry[length];
        this.size = length;
    }


    /**
     * This implementation of size() for the Map
     *
     * @return returns the amount of QueueEntry objects in this Queue
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * This implementation of isEmpty() for the Map
     *
     * @return false if any QueueEntry objects are present, true otherwise
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
        for (QueueEntry e : this.entries) {
            if (e.getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This implementation of containsValue() for the Map
     *
     * @param value the Track to search for
     * @return true if the value is present at least once, false otherwise
     */
    public boolean containsValue(Track value) {
        for (QueueEntry e : this.entries) {
            if (e.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the value belonging to an Integer key object
     *
     * @param key the key to search for in this Queue
     * @return the value associated with this key
     */
    public Track get(Integer key) {
        for (QueueEntry e : this.entries) {
            if (e.getKey().equals(key)) {
                //Cast to Track due to IDE errors; test without cast later
                return (Track) e.getValue();
            }
        }
        return null;
    }

    /**
     * Edits or adds a QueueEntry in the Queue containing the specified
 key-value pair
     *
     * @param key the Integer key object for the QueueEntry
     * @param value the Track for the QueueEntry
     * @return The previous value for this key, or null if it didn't exist yet
     */
    @Override
    public Track put(Integer key, Track value) {
        if (this.containsKey(key)) { //If the key is present, return the result of setValue(value)
            for (QueueEntry e : this.entries) {
                if (e.getKey().equals(key)) {
                    //Cast to Track due to IDE errors; test without cast later
                    return (Track) e.setValue(value);
                }
            }
        } else { //Else, create a new array with the necessary Length
            QueueEntry<Integer, Track>[] newEntries = new QueueEntry[key];
            //copy the current array into it
            for (QueueEntry e : entries) {
                newEntries[(Integer) e.getKey()] = e;
            }
            //Add the new entry
            newEntries[key] = new QueueEntry(key, value);
            //and change the reference pointer of entries
            this.entries = newEntries;
            this.size = key;
        }
        //If nothing was returned yet, the key did not exist
        return null;
    }

    /**
     * Remove an item from this Queue specified by a key
     *
     * @param key The key for the item to be removed
     * @return Returns the value of the item to be removed
     */
    public Track remove(Integer key) {
        Track val = null;
        for (QueueEntry e : this.entries) {
            if (e.getKey().equals(key)) {
                val = (Track) e.getValue();
                e = null;
            }
        }
        return val;
    }

    /**
     * Add an entire map to to this Queue, as long as the map shares the same
 data types for its key-value pairs
     *
     * @param m the map to add
     */
    @Override
    public void putAll(Map<? extends Integer, ? extends Track> m) {
        int newSize = this.size + m.size();
        QueueEntry<Integer, Track>[] newEntries = new QueueEntry[newSize];
        //copy the current array into it, ensuring equal indices
        System.arraycopy(this.entries, 0, newEntries, 0, this.entries.length);
        //Add the new entries
        System.arraycopy(m.values().toArray(), 0, newEntries, this.entries.length, newSize);
        //Update the pointer and size
        this.entries = newEntries;
        this.size = newSize;
    }

    /**
     * Clears the playlist and sets the size to 0
     */
    @Override
    public void clear() {
        this.entries = new QueueEntry[0];
        this.size = 0;
    }

    /**
     * Get a set containing all of the keys
     *
     * @return a TreeSet with the keys in this Queue
     */
    @Override
    public Set<Integer> keySet() {
        Set<Integer> set = new TreeSet<>();
        for (QueueEntry e : entries) {
            set.add((Integer) e.getKey());
        }
        return set;
    }

    /**
     * Get a collection containing all of the values in this Queue
     *
     * @return an ArrayList with all of the Tracks
     */
    @Override
    public Collection<Track> values() {
        ArrayList<Track> coll = new ArrayList<>();
        //Make sure the returned ArrayList uses the order specified by the user
        for(int i = 1; i < size; i++) {
            this.get(i);
        }
        return coll;
    }

    /**
     * Get the QueueEntry objects in this Queue as a Set
     *
     * @return a TreeSet containing all of the QueueEntry objects
     */
    @Override
    public Set<Entry<Integer, Track>> entrySet() {
        Set<Entry<Integer, Track>> set = new TreeSet<>();
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
     * @param value the Track value to check for
     * @return the result of the Track variant of this method
     */
    @Override
    public boolean containsValue(Object value) {
        if (value.getClass() == Track.class) {
            //Cast to ensure the correct variant of this method is called
            return containsValue((Track) value);
        } else {
            throw new UnsupportedOperationException("Whoops, we use Integer keys here!");
        }
    }

    /**
     * Alias for the required override of get
     *
     * @param key the key to get the value from
     * @return the Track that belongs to this key
     */
    @Override
    public Track get(Object key) {
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
    public Track remove(Object key) {
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
     * @param <Track> The value to map to the key; location path for the music
     * file
     */
    public class QueueEntry<Integer, Track> implements Map.Entry<Integer, Track> {
        
        private final Integer key;
        private Track value;
        
        /**
         * Instantiate a PlaylistEntry
         *
         * @param key The Integer key object
         * @param value the Track path to the music file
         */
        public QueueEntry(Integer key, Track value) {
            this.key = key;
            this.value = value;
        }
        
        /**
         * Get the key for this QueueEntry
         *
         * @return returns the key as an Integer object
         */
        @Override
        public Integer getKey() {
            return this.key;
        }
        
        /**
         * Get the value of this QueueEntry
         *
         * @return The Track to play
         */
        @Override
        public Track getValue() {
            return this.value;
        }
        
        /**
         * Set the value of this QueueEntry
         *
         * @param value The value to set it to. Must be a Track
         * @return Returns the old value for this key
         */
        @Override
        public Track setValue(Track value) {
            Track oldValue = this.value;
            this.value = value;
            return oldValue;
        }
        
    }

}
