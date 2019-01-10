package com.pijukebox.service.impl;

import com.pijukebox.model.Album;
import com.pijukebox.repository.IAlbumRepository;
import com.pijukebox.service.IAlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumServiceImpl implements IAlbumService {

    private final IAlbumRepository albumRepository;

    @Autowired
    public AlbumServiceImpl(IAlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    @Override
    public List<Album> findAll() {
        return albumRepository.getAll();
    }

    @Override
    public Album findById(Long id) {
        return albumRepository.getById(id);
    }

    @Override
    public Album findDetails(long id) {
        return albumRepository.getDetails(id);
    }
}
