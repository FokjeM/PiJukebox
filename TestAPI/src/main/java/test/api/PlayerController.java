package test.api;

import java.io.IOException;
import test.player.Player;
import test.player.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLDecoder;
import java.nio.charset.Charset;
import org.springframework.web.bind.annotation.RequestParam;
import test.player.FatalException;
import test.player.NonFatalException;


@RestController
@RequestMapping("/api/v1/player")
public class PlayerController {
    
    private final Player player;
    private Track track;

    @Autowired
    public PlayerController() throws IOException, FatalException, NonFatalException {
        try {
            player = new Player();
        } catch (IOException | FatalException | NonFatalException ex) {
            ex.printStackTrace(System.err);
            throw ex;
        }
    }

    /**
     * Calls {@link Player#playTrack(Track) playTrack(Track t)}
     * @param path
     * @return The title of the currently playing track
     */
    @GetMapping("/play")
    public String playSong(@RequestParam("path") String path) {
        try {
            track = new Track(URLDecoder.decode(path, "UTF-8"));
            player.playTrack(track);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return track.getTitle();
    }
}
