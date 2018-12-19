package com.pijukebox.service;

import com.pijukebox.data.ImageRepository;
import com.pijukebox.data.InterImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    private ImageRepository entityManagerJpaRepositiry;

    @Autowired
    public ImageService(ImageRepository entityManagerJpaRepositiry)
    {
        this.entityManagerJpaRepositiry = entityManagerJpaRepositiry;
    }

    public InterImage getImageById(Long id)
    {
        return entityManagerJpaRepositiry.getImageById(id);
    }
}
