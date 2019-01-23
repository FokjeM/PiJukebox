package com.pijukebox.controller;

import com.pijukebox.model.simple.SimpleTrack;
import com.pijukebox.service.ITrackService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class UploadController {

    private final ITrackService trackService;

    private String uploadDir = "C:\\Users\\rutge\\Desktop\\uploads\\";

    @Autowired
    public UploadController(ITrackService trackService) {
        this.trackService = trackService;
    }

    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    public ResponseEntity<SimpleTrack> upload(@RequestBody MultipartFile file) {
        try {
            SimpleTrack track = new SimpleTrack(null, FilenameUtils.removeExtension(file.getOriginalFilename()), null, file.getOriginalFilename());
            if (trackService.findAllSimpleTrackByName(track.getName()).isPresent()) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            } else {
                uploadFile(file);
                return new ResponseEntity<>(trackService.addSimpleTrack(track), HttpStatus.CREATED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Something went wrong while uploading %s.", file.getName()), ex);
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

