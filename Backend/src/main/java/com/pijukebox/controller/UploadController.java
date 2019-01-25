package com.pijukebox.controller;

import com.pijukebox.model.simple.SimpleTrack;
import com.pijukebox.service.ITrackService;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class UploadController {

    private final ITrackService trackService;

    //private String uploadDir = "C:\\Users\\rutge\\Desktop\\uploads\\";
    private String uploadDir = "D:\\Users\\Ruben\\Desktop\\uploads\\";

    //private String path = "D:\\Users\\rutge\\Desktop\\uploads\\check\\";
    private String dirToScan = "D:\\Users\\Ruben\\Desktop\\uploads\\check\\";
    private ArrayList<String> tracks = new ArrayList<>();

    @Autowired
    public UploadController(ITrackService trackService) {
        this.trackService = trackService;
    }

    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    public ResponseEntity<?> upload(@RequestBody MultipartFile[] file) {
        for (MultipartFile f : file) {
            try {
                SimpleTrack track = new SimpleTrack(null, FilenameUtils.removeExtension(f.getOriginalFilename()), null, f.getOriginalFilename());
                if (trackService.findAllSimpleTrackByName(track.getName()).isPresent()) {
                    return new ResponseEntity<>(HttpStatus.CONFLICT);
                } else {
                    uploadFile(f);
                    trackService.addSimpleTrack(track);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Something went wrong while uploading %s.", f.getName()), ex);
            }
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/upload/folder")
    public ResponseEntity<?> uploadFromFolder() {
        File[] files = new File(dirToScan).listFiles();
        showFiles(files);

        for (String t : tracks) {
            try {
                SimpleTrack track = new SimpleTrack(null, FilenameUtils.removeExtension(t), null, t);
                if (trackService.findAllSimpleTrackByName(track.getName()).isPresent()) {
                    return new ResponseEntity<>(HttpStatus.CONFLICT);
                } else {
                    addFileToFolder(dirToScan, uploadDir, t);
                    trackService.addSimpleTrack(track);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Something went wrong while uploading %s.", t), ex);
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void addFileToFolder(String oldDir, String newDir, String fileName) {
        File source = new File(oldDir + fileName);
        File destination = new File(newDir + fileName);
        try {
            if (!destination.exists()) {
                Files.move(source.toPath(), destination.toPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showFiles(File[] files) {
        for (File file : files) {
            if (file.isDirectory()) {
                showFiles(file.listFiles()); // Calls same method again.
            } else {
                tracks.add(file.getName());
            }
        }
    }

    private void uploadFile(MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadDir + file.getOriginalFilename());
            Files.write(path, bytes);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

