package com.PiJukebox;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import com.mpatric.mp3agic.*;

/**
 * Implementation of a Track
 * Should just hold:
 * - the filename of the track
 * - the path of the track
 * - the metadata of the track
 * 
 * Will use the mp3agic library to read the metadata
 * @author Riven
 */
class Track {
    
    private final Path filepath;
    private final String title;
    private final int bitrate;
    private final String filetype;
    private final FileWrapper mp3file;
    
    /**
     * Create a Track object with info from the database and application
     * @param mediaDir The general media directory for PiJukebox.
     *          The application must provide this
     * @param filename The filename of the Track, this should come from the DB
     * @throws NonFatalException Something went wrong, but we can recover.
     */
    public Track(String mediaDir, String filename) throws NonFatalException{
        filepath = FileSystems.getDefault().getPath(mediaDir, filename);
        try {
            //Wrap the file, the library handles the hardships
            mp3file = new FileWrapper(filepath);
        } catch (NullPointerException e) {
            throw new NonFatalException("Path was set to NULL; message given:\r\n" + e.getMessage(), e, false, false);
        } catch (IOException e) {
            throw new NonFatalException("File doesn't exist; message given:\r\n" + e.getMessage(), e, false, false);
        }
        title = "";
        bitrate = 0;
        filetype = "";
    }
}
