package com.pijukebox.player;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrackDetails {

    private String title;
    private String artist;
    private String genre;
    private String album;

    private Mp3File mp3file;

    private Path absolutePath = Paths.get("C:/Users/Public/Music");

    public TrackDetails(String filename) {
        try {
            filename = filename.trim();
            String mainPath = Paths.get(absolutePath.toString(), filename).toString();
            this.mp3file = new Mp3File(new File(mainPath));
            setTagValuesToFields();

            System.out.println("Length of this mp3 is: " + mp3file.getLengthInSeconds() + " seconds");
            System.out.println("Has ID3v1 tag?: " + (mp3file.hasId3v1Tag() ? "YES" : "NO"));
            System.out.println("Has ID3v2 tag?: " + (mp3file.hasId3v2Tag() ? "YES" : "NO"));
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public TrackDetails(String filename, String path) {
        try {

            this.mp3file = new Mp3File(Paths.get(path) + "\\" + filename);
            setTagValuesToFields();

            System.out.println("Length of this mp3 is: " + mp3file.getLengthInSeconds() + " seconds");
            System.out.println("Has ID3v1 tag?: " + (mp3file.hasId3v1Tag() ? "YES" : "NO"));
            System.out.println("Has ID3v2 tag?: " + (mp3file.hasId3v2Tag() ? "YES" : "NO"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void setTagValuesToFields() {
        if (mp3file.hasId3v2Tag()) {
            ID3v2 id3v2Tag = mp3file.getId3v2Tag();
            this.title = id3v2Tag.getTitle();
            this.artist = id3v2Tag.getArtist() == null ? "unknown" : id3v2Tag.getArtist();
            this.genre = id3v2Tag.getGenreDescription() == null ? "unknown" : id3v2Tag.getGenreDescription();
            this.album = id3v2Tag.getAlbum() == null ? "unknown" : id3v2Tag.getAlbum();
        } else if (mp3file.hasId3v1Tag()) {
            ID3v1 id3v1Tag = mp3file.getId3v1Tag();
            this.title = id3v1Tag.getTitle();
            this.artist = id3v1Tag.getArtist() == null ? "unknown" : id3v1Tag.getArtist();
            this.genre = id3v1Tag.getGenreDescription() == null ? "unknown" : id3v1Tag.getGenreDescription();
            this.album = id3v1Tag.getAlbum() == null ? "unknown" : id3v1Tag.getAlbum();
        }
    }
}
