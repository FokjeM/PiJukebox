package com.pijukebox.service.impl;

import com.pijukebox.model.Album;
import com.pijukebox.repository.IAlbumRepository;
import com.pijukebox.service.IAlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AlbumServiceImpl implements IAlbumService {

    private final IAlbumRepository albumRepository;

    @Autowired
    public AlbumServiceImpl(IAlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    @Override
    public List<Album> findAll() {
        return albumRepository.findAll();
    }

    @Override
    public Optional<Album> findById(Long id) {
        return albumRepository.findById(id);
    }
}
