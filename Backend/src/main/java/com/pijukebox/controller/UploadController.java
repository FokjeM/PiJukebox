package com.pijukebox.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class UploadController {

    //private String uploadDir = "C:\\Users\\rutge\\Desktop\\uploads\\";
    private String uploadDir = "D:\\Users\\Ruben\\Desktop\\uploads\\";

    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    public ResponseEntity<?> uploadFile(@RequestBody MultipartFile file) {
        try {
            uploadThisFile(file);
            return ResponseEntity.ok().body(null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ResponseEntity.notFound().build();
    }

    private void uploadThisFile(MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadDir + file.getOriginalFilename());
            Files.write(path, bytes);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

