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
    private String uploadDir = "C:\\Users\\Public\\Music\\";

    private String dirToScan = "C:\\Users\\Public\\Downloads\\";
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
    public ResponseEntity<SimpleTrack> uploadFromFolder() throws IOException {
        File[] files = new File(dirToScan).listFiles();
        if (files != null) {
            showFiles(files);

            for (String t : tracks) {
                if (!t.equals("desktop.ini")) {
                    SimpleTrack track = new SimpleTrack(null, FilenameUtils.removeExtension(t), null, t);
                    TrackDetails trackDetails = new TrackDetails(t, dirToScan);
                    SimpleGenre genre = new SimpleGenre(null, trackDetails.getGenre());
                    SimpleArtist artist = new SimpleArtist(null, trackDetails.getArtist());
                    if (!trackService.findAllSimpleTrackByName(track.getName()).hasBody()) {
                        addFileToFolder(dirToScan, uploadDir, t);
                        trackService.addSimpleTrack(track);
                    }
                    genreService.addSimpleGenre(genre);
                    artistService.addSimpleArtist(artist);
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
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return null;
    }

    private void addFileToFolder(String oldDir, String newDir, String fileName) throws IOException {
        File source = new File(oldDir + fileName);
        File destination = new File(newDir + fileName);
        if (!destination.exists()) {
            Files.move(source.toPath(), destination.toPath());
        }
    }

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

    private void uploadFile(MultipartFile file) throws Exception {
        byte[] bytes = file.getBytes();
        Path path = Paths.get(uploadDir + file.getOriginalFilename());
        Files.write(path, bytes);
    }
}

