package com.PiJukeboxPlayer;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Files;
import com.mpatric.mp3agic.*;
import java.io.InputStream;
import java.nio.file.FileSystemNotFoundException;
import java.util.Locale;

/**
 * Implementation of a Track Should just hold: - the filename of the track - the
 * path of the track - the metadata of the track
 *
 * Will use the mp3agic library to read the metadata
 *
 * @author Riven
 */
class Track {

    private final static String WINDOWS_DEFAULT_MEDIA_PATH = "C:\\Users\\Public\\Music\\";
    private final static String NIX_DEFAULT_MEDIA_PATH = "/media/music/";

    private final Path filepath;
    private final String title;
    private final int bitrate;
    private final String streamType;
    private final String duration;

    /**
     * Create a Track object with info from the DB and file metadata
     *
     * @param mediaDir The directory for this Track. The application must
     * provide this, or OS default is used. Windows: C:\Users\Public\Music\
     * *NIX-like: /media/music/ Anything else: Exceptions get thrown
     * @param filename The filename of the Track, this should come from the DB
     * @throws NonFatalException Something went wrong, but we can recover. Pass
     * this to the ErrorLogger and move on.
     * @throws FatalException Originates in getOSPath(), OS cannot be determined
     * and thus any OS specific things like separators and paths aren't
     * reliable. Pass this to the ErrorLogger and EXIT with a non-zero exit
     * code!
     */
    public Track(String mediaDir, String filename) throws NonFatalException, FatalException {
        if (mediaDir != null && !mediaDir.equals("") && filename != null && !filename.equals("")) {
            filepath = FileSystems.getDefault().getPath(mediaDir, filename);
            ///TODO: Handle file according to filetype
        } else if (mediaDir == null || mediaDir.equals("")) {
            filepath = FileSystems.getDefault().getPath(getOSPath(), filename);
        } else {
            //Empty string or null, not even a single character...
            throw new NonFatalException("No (valid) filename was given, like 'song.ext'\r\n\tInstead " + filename + " was given.", new java.nio.file.FileSystemException(mediaDir + filename), false, true);
        }
        streamType = checkFiletype();
        try {
            if (streamType.equals("mp3")) {
                Mp3File mp3file = mp3FromPath(filepath);
                if (mp3file.hasId3v2Tag()) {
                    title = mp3file.getId3v2Tag().getTitle();
                } else {
                    title = mp3file.getId3v1Tag().getTitle();
                }
                bitrate = mp3file.getBitrate();
                StringBuilder dur = new StringBuilder();
                dur.append(Long.toString(mp3file.getLengthInSeconds()));
                dur.append(".");
                long millis = mp3file.getLengthInMilliseconds() - (mp3file.getLengthInSeconds()*1000);
                dur.append(Long.toString(millis));
                duration = dur.toString();
            } else {
                title = ffprobe("-show-entries stream_tags=title");
                bitrate = Integer.parseInt(ffprobe("-show-entries stream=bit_rate"));
                duration = "-show-entries stream=duration";
            }
        } catch (IOException io) {
            throw new NonFatalException("The file could not be read and/or processed and an exception was thrown.", io);
        }

    }

    /**
     * Returns an mp3agic Mp3File as specified in the given FILEPATH.
     *
     * @param path The Path object of the file to open.
     * @return the mp3agick Mp3File object.
     * @throws NonFatalException When the Mp3File class can't handle the data
     * format of the file. This might mean a different MPeg encoding was used,
     * like MPeg2 or MPeg4.
     * @throws FatalException propagated from getOSPath() and
     * NonFatalException()
     * @throws IOException Propagated from Mp3File()
     */
    private Mp3File mp3FromPath(Path path) throws NonFatalException, FatalException, IOException {
        try {
            return new Mp3File(path);
        } catch (InvalidDataException ide) {
            throw new NonFatalException("Invalid data for an mp3 file for a file that was determined to be an mp3.", ide);
        } catch (UnsupportedTagException ut) {
            throw new NonFatalException("Mp3agic cannot handle this metadata tag. It's either really old, really new or non-standard.", ut);
        }
    }

    /**
     * Get the hard-coded default path or a FatalError for this OS.
     *
     * @return the ${OS}_DEFAULT_PATH for most OS'es known to man
     * @throws FatalException This OS does not identify with anything useful.
     * Much like a Syrian passport, the origin cannot be guaranteed.
     */
    private static String getOSPath() throws FatalException {
        String osName = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);
        if (osName.contains("windows")) {
            return WINDOWS_DEFAULT_MEDIA_PATH;
        } else if (osName.contains("linux") || osName.contains("irix") || osName.contains("mpe/ix") || osName.contains("bsd") || osName.contains("unix") || osName.contains("mac") || osName.contains("darwin") || osName.contains("sun") || osName.contains("solaris") || osName.contains("ux") || osName.contains("nix") || osName.contains("ix")) {
            //There are more OS'es than y'all might think, most based on UNIX or
            //Linux. Most are POSIX-compliant even when not *NIX based. And most
            //Share some common filesystem logic.
            return NIX_DEFAULT_MEDIA_PATH;
        } else {
            //And then there are the REALLY special options. They have no common
            //filesystem logic, no simple routines or useful OS APIs for access
            //and offer no clear method of navigating the system. Screw those.
            throw new FatalException("OS Name could not be read or recognized! This system is not a Windows-, Linux- or Unix-based, or an otherwise POSIX-compliant system!\r\n\tReported name: " + osName, new FileSystemNotFoundException());
        }
    }

    /**
     * Checks the filetype as returned by FFProbe, a tool in the FFMpeg suite.
     * If file access is denied, a NonFatalException is thrown. Keep in mind
     * that any class that depends on Track objects MUST check if any Tracks
     * could be instantiated, or throw a FatalException, log and exit.
     *
     * @param path the FILEPATH of the file to check.
     * @return the filetype typically associated with this file.
     */
    private String checkFiletype() throws NonFatalException {
        try {
            //Check the MIME Type first, to prevent unnecessary work
            String MIMEtype = Files.probeContentType(filepath).toLowerCase(Locale.ENGLISH);
            if (!MIMEtype.contains("audio/")) {
                //Not an audio MIME Type
                throw new NonFatalException("This was not an audio file!", new Exception());
            }
            return ffprobe("-select_streams a:0 -show_entries stream=codec_name");
        } catch (IOException ex) {
            throw new NonFatalException("File could not be read! Skipping this track!", ex);
        }
    }

    /**
     * Call onto FFProbe to check something about this track.
     * This function was built with the idea that only a single property is
     * requested. If multiple properties are requested, this must be handled by
     * the implementing or extending method.
     * Headers, Footers, Wrappers and Keys for values are NOT PRINTED.
     * 
     * @param command The internals for the command to execute
     * @return The information returned by FFProbe as queried in COMMAND
     * @throws IOException Propagated from Runtime.Exec(String command)
     */
    public String ffprobe(String command) throws IOException {
        //Set up the ffprobe command to check the stream type
        StringBuilder s = new StringBuilder();
        s.append("ffprobe -v error ");
        s.append(command);
        s.append(" -of default=noprint_wrappers=1:nokey=1 -i ");
        s.append(filepath.toAbsolutePath().toString());
        int extChar;
        InputStream ffprobe = Runtime.getRuntime().exec(s.toString()).getInputStream();
        StringBuilder ext = new StringBuilder();
        while ((extChar = ffprobe.read()) != -1) {
            ext.append((char) extChar);
        }
        ffprobe.close();
        return ext.toString();
    }

    /**
     * Get the title for this Track as specified in its metadata.
     * @return The String title for this Track
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Get the bitrate for this Track as specified in its metadata.
     * @return The Integer bitrate for this Track
     */
    public int getBitrate() {
        return this.bitrate;
    }

    /**
     * Get the Stream Type / encoding for this Track as specified in its metadata.
     * @return The String Stream Type for this Track
     */
    public String getStreamType() {
        return this.streamType;
    }
    
    /**
     * Get the duration for this Track as probed by FFProbe.
     * @return the String Duration for this Track.
     */
    public String getDuration() {
        return this.duration;
    }

    /**
     * Get the FILEPATH for this Track as specified during instantiation.
     * @return The Path filepath for this Track
     */
    public Path getPath() {
        return this.filepath;
    }

    /**
     * Get the default Media path for music for this OS.
     * @return The default Media path for this OS
     */
    private static String getDefaultMediaPath() throws FatalException {
        return getOSPath();
    }
}
