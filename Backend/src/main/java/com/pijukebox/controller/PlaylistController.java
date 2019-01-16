package com.pijukebox.controller;

import com.pijukebox.model.playlist.Playlist;
import com.pijukebox.service.IPlaylistService;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/v1/playlists")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Transactional
public class PlaylistController {

    // https://spring.io/guides/gs/accessing-data-mysql/

    private IPlaylistService playlistService;

    public PlaylistController(IPlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @GetMapping()
    @ApiOperation(value = "Retrieve all playlists of a user.")
    public List<Playlist> getPlaylists(@RequestParam("userId") Long userId) {
        return playlistService.findAllByUserId(userId);
    }

    @PostMapping()
    @ApiOperation(value = "Save a new playlist.")
    public Playlist savePlaylist(@PathVariable Long userId, @RequestBody Playlist playlist) {
        return playlistService.save(playlist);
    }

//    @PatchMapping()
//    @ApiOperation(value = "Update a playlist.")
//    public Playlist updatePlaylist(@RequestParam long playlistId, Long userId, @RequestBody Playlist playlist) throws Exception {
//        Playlist existing = playlistService.findById(playlistId);
//        playlist.CopyNonNullProperties(existing);
//        return playlistService.save(existing);
//    }
//
//    @DeleteMapping()
//    @ApiOperation(value = "Delete a playlists of a user.")
//    public Playlist deletePlaylist(@RequestParam long playlistId, Long userId) {
//        return playlistService.delete(userId, playlistId);
//    }
}
