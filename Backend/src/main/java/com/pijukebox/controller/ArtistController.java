package com.pijukebox.controller;

import com.pijukebox.service.IArtistService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Transactional
public class ArtistController {

    private final IArtistService artistService;

    @Autowired
    public ArtistController(IArtistService artistService) {
        this.artistService = artistService;
    }
	
    @GetMapping("/artists")
    @ApiOperation(value = "Get all information pertaining to an artist via its name")
    public ResponseEntity<List<Artist>> artists(@RequestParam(name = "id", required = false) Long id, @RequestParam(name = "name", required = false) String name) {
        try {

            if (id != null && id > 0) {
                if (!artistService.findGenresByNameContaining(name).isPresent()) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<>(artistService.findGenresByNameContaining(name).get(), HttpStatus.OK);
            }

            if (name != null && !name.isEmpty()) {
                if (!artistService.findGenresByNameContaining(name).isPresent()) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<>(artistService.findGenresByNameContaining(name).get(), HttpStatus.OK);
            }
            return new ResponseEntity<>(artistService.findAll(), HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Album with ID {id} Not Found", ex);
        }
    }

    @GetMapping("/artists/{id}")
    @ApiOperation(value = "Get all information pertaining to an artist via its ID")
    public ResponseEntity<Artist> artistDetails(@PathVariable Long id) {
        try {
            if (!artistService.findById(id).isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(artistService.findById(id).get(), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Artist with ID {id} Not Found", ex);
        }
    }	
}
