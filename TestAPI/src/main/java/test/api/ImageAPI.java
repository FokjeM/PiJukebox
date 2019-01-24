package test.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.api.data.model.ImageAnnotated;
import test.api.service.ImageService;
import javax.sound.sampled.AudioSystem;
import java.util.List;

@RestController
@RequestMapping("/images")
public class ImageAPI {

    private ImageService imageService;

    @Autowired
    public ImageAPI(ImageService imageService)
    {
        this.imageService = imageService;
    }

    @GetMapping("/images")
    public List<ImageAnnotated> getImages()
    {
        AudioSystem.getMixerInfo();
        return imageService.getImages();
    }
}
