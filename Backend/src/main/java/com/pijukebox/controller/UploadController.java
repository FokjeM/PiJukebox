package com.pijukebox.controller;

import com.pijukebox.model.simple.SimpleArtist;
import com.pijukebox.model.simple.SimpleGenre;
import com.pijukebox.model.simple.SimpleTrack;
import com.pijukebox.player.TrackDetails;
import com.pijukebox.service.IArtistService;
import com.pijukebox.service.IGenreService;
import com.pijukebox.service.ITrackService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class UploadController {

    private final ITrackService trackService;
    private final IGenreService genreService;
    private final IArtistService artistService;
    private String musicDir = com.pijukebox.configuration.ApplicationInitializer.getMediaPath();
    private String uploadDir = com.pijukebox.configuration.ApplicationInitializer.getMediaPath().concat("uploads").concat(File.separator);
    private ArrayList<String> tracks = new ArrayList<>();

    @Autowired
    public UploadController(ITrackService trackService, IGenreService genreService, IArtistService artistService) {
        this.trackService = trackService;
        this.genreService = genreService;
        this.artistService = artistService;
    }

    /**
     * Upload a file to the Raspberry Pi
     *
     * @param file a file to upload
     * @return a track
     */
    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    public ResponseEntity<SimpleTrack> upload(@RequestBody MultipartFile[] file) throws Exception {
        for (MultipartFile f : file) {
            SimpleTrack track = new SimpleTrack(null, FilenameUtils.removeExtension(f.getOriginalFilename()), null, f.getOriginalFilename());
            // If track exists, do not upload
            if (trackService.findSimpleTrackByName(track.getName()).hasBody()) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            } else {
                uploadFile(f);
                trackService.addSimpleTrack(track);
            }


        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Upload all files in a folder to the Raspberry Pi
     *
     * @return a track
     */
    @PostMapping(value = "/upload/folder")
    public ResponseEntity<Object> uploadFromFolder() throws IOException {
        File[] files = new File(uploadDir).listFiles();
        if (files != null) {
            showFiles(files);

            for (String t : tracks) {
                if (t.toLowerCase().endsWith(".mp3")) {
                    SimpleTrack track = new SimpleTrack(null, FilenameUtils.removeExtension(t), null, t);
                    TrackDetails trackDetails = new TrackDetails(t, uploadDir);
                    SimpleGenre genre = new SimpleGenre(null, trackDetails.getGenre());
                    SimpleArtist artist = new SimpleArtist(null, trackDetails.getArtist());
                    
                    if (!trackService.findSimpleTrackByName(track.getName()).hasBody()) {
                        addFileToFolder(uploadDir, musicDir, t);
                        trackService.addSimpleTrack(track);
                    }

                    if(!genreService.findGenresByNameContaining(genre.getName()).hasBody())
                    {
                        genreService.addSimpleGenre(genre);
                    }

                    if(!artistService.findSimpleArtistsByNameContaining(artist.getName()).hasBody())
                    {
                        artistService.addSimpleArtist(artist);
                    }
                    Long addToArtistId = artistService.findSimpleArtistsByNameContaining(artist.getName()).getBody().get(0).getId();
                    Long addToGenreId = genreService.findGenresByNameContaining(genre.getName()).getBody().get(0).getId();

                    if (trackService.findTrackByArtistId(addToArtistId).hasBody()) {
                        trackService.addArtistToTrack(trackService.findTrackByArtistId(addToArtistId).getBody());
                    }

                    if (trackService.findTrackByGenreId(addToGenreId).hasBody()) {
                        trackService.addGenreToTrack(trackService.findTrackByGenreId(addToGenreId).getBody());

                    }

                }
            }
            return new ResponseEntity<>("Song added successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Nothing to display",HttpStatus.NO_CONTENT);
    }

    /**
     * Move a file from one directory to another, without overwriting.
     *
     * @param oldDir   The directory where the currently file resides.
     * @param newDir   The directory to move the file to.
     * @param fileName The name the file has, including the type suffix, if present.
     */
    private void addFileToFolder(String oldDir, String newDir, String fileName) throws IOException {
        File source = new File(oldDir + fileName);
        File destination = new File(newDir + fileName);
        if (!destination.exists()) {
            Files.move(source.toPath(), destination.toPath());
        }
    }

    /**
     * Show all files in the given array, or any subdirectories of files in the array.
     *
     * @param files an array with File objects, which may be files and directories.
     */
    private void showFiles(File[] files) {
        tracks.clear();
        for (File file : files) {
            if (file.isDirectory()) {
                showFiles(Objects.requireNonNull(file.listFiles())); // Calls same method again.
            } else {
                tracks.add(file.getName());
            }
        }
    }

    /**
     * Save a file to the music directory.
     *
     * @param file the file to upload.
     */
    private void uploadFile(MultipartFile file) throws Exception {
        byte[] bytes = file.getBytes();
        Path path = Paths.get(musicDir  + file.getOriginalFilename());
        Files.write(path, bytes);
    }
}

