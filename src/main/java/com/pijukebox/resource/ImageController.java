package com.pijukebox.resource;

import com.pijukebox.data.InterImage;
import com.pijukebox.service.ImageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// We're telling Spring MVC that this is a REST controller
// This treats methods with @RequestMapping as if it had the @ResponseBody annotation
// which tells Spring that the return value should be transformed into a response
@RestController
// The base request mapping this controller listens to, we respond to anything starting with /data
@RequestMapping("/images")
public class ImageController {

    private ImageService imageService;

    public ImageController(ImageService imageService)
    {
        this.imageService = imageService;
    }

    @GetMapping("/{id}")
    public InterImage getImageById(@PathVariable("id") Long id)
    {
        return imageService.getImageById(id);
    }
}
