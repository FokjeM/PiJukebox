package test.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.api.data.jpa.SpringJpaRepository;
import test.api.data.model.ImageAnnotated;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ImageService {
    private final SpringJpaRepository springJpaRepository;

    @Autowired
    public ImageService(SpringJpaRepository springJpaRepository)
    {
        this.springJpaRepository = springJpaRepository;
    }

    public List<ImageAnnotated> getImages()
    {
        return springJpaRepository.findAll();
    }
}
