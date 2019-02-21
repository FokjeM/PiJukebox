package com.pijukebox.player;

import com.mpatric.mp3agic.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class TrackDetails {

    private final static Path ABSOLUTE_PATH = Paths.get(com.pijukebox.configuration.ApplicationInitializer.getMediaPath());
    private String title;
    private String artist;
    private String genre;
    private String album;
    private Mp3File mp3file;

    /**
     * Instantiates a new Track details object.
     * Takes the information from the file with the specified name at the
     * specified location.
     *
     * @param filename the filename
     * @param path     the path
     */
    public TrackDetails(String filename, String path) {
        try {

            this.mp3file = new Mp3File(Paths.get(path.trim()) + File.separator + filename.trim());
            setTagValuesToFields();

            System.out.println("Length of this mp3 is: " + mp3file.getLengthInSeconds() + " seconds");
            System.out.println("Has ID3v1 tag?: " + (mp3file.hasId3v1Tag() ? "YES" : "NO"));
            System.out.println("Has ID3v2 tag?: " + (mp3file.hasId3v2Tag() ? "YES" : "NO"));
        } catch (InvalidDataException | UnsupportedTagException | IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Instantiates a new Track details object.
     * Takes the information from the file with the specified name in the
     * default file location.
     *
     * @param filename the filename
     */
    public TrackDetails(String filename) {
        this(filename.trim(), Paths.get(TrackDetails.ABSOLUTE_PATH.toString()).toString());
    }

    /**
     * Set metadata of the current Track file to the fields of current object
     */
    private void setTagValuesToFields() {
        if (mp3file.hasId3v2Tag()) {
            ID3v2 id3v2Tag = mp3file.getId3v2Tag();
            this.title = id3v2Tag.getTitle();
            this.artist = (id3v2Tag.getArtist() == null || id3v2Tag.getArtist().isEmpty())  ? "unknown" : id3v2Tag.getArtist();
            this.genre = (id3v2Tag.getGenreDescription() == null || id3v2Tag.getGenreDescription().isEmpty()) ? "unknown" : id3v2Tag.getGenreDescription();
            this.album = (id3v2Tag.getAlbum() == null || id3v2Tag.getAlbum().isEmpty()) ? "unknown" : id3v2Tag.getAlbum();
        } else if (mp3file.hasId3v1Tag()) {
            ID3v1 id3v1Tag = mp3file.getId3v1Tag();
            this.title = id3v1Tag.getTitle();
            this.artist = (id3v1Tag.getArtist() == null || id3v1Tag.getArtist().isEmpty()) ? "unknown" : id3v1Tag.getArtist();
            this.genre = (id3v1Tag.getGenreDescription() == null || id3v1Tag.getGenreDescription().isEmpty()) ? "unknown" : id3v1Tag.getGenreDescription();
            this.album = (id3v1Tag.getAlbum() == null || id3v1Tag.getAlbum().isEmpty()) ? "unknown" : id3v1Tag.getAlbum();
        }
    }
}
