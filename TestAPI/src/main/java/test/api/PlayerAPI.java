package test.api;

import java.io.IOException;
import java.net.URLDecoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import test.player.*;

@RestController
@RequestMapping("/player")
public class PlayerAPI {

    private Player player;
    public PlayerAPI() {
        try {
            player = new Player();
        } catch (IOException | FatalException fe) {
            player.getLogger().writeLog(fe, true);
        } catch (NonFatalException nfe) {
            player.getLogger().writeLog(nfe, false);
        }
    }
    
    @GetMapping("/play/{path}")
    public  void playSong(@PathVariable String path)
    {
        try {
            String filepath = URLDecoder.decode(path);
            Track track = new Track(filepath);
            player.playTrack(track);
        } catch (NonFatalException ex) {
            player.getLogger().writeLog(ex, false);
        } catch (FatalException ex) {
            player.getLogger().writeLog(ex, true);
        }
    }
}
