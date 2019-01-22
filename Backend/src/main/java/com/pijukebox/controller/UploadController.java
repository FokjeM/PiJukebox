package com.pijukebox.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class UploadController {




    @PostMapping("/upload")
    public ResponseEntity<String> playCurrent()
    {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
