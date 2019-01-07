package com.PiJukebox;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import com.mpatric.mp3agic;

/**
 * Implementation of a Track
 * Should just hold:
 * - the filename of the track
 * - the path of the track
 * - the metadata of the track
 * 
 * Will use the MP3agick library to read the metadata
 * @author Riven
 */
class Track {
    
    private final Path filepath;
    private final String title;
    private final int bitrate;
    private final String filetype;
    private static mp3agic.Mp3File mp3;
    
    public Track(){
        filepath = null;
        title = "";
        bitrate = 0;
        filetype = "";
    }
}
