package com.pijukebox.controller;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/v1/genres")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Transactional
public class GenreController {
}
