package com.pijukebox.service.impl;

import com.pijukebox.model.Album;
import com.pijukebox.model.Genre;
import com.pijukebox.model.SimpleAlbum;
import com.pijukebox.repository.IAlbumRepository;
import com.pijukebox.service.IAlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AlbumServiceImpl implements IAlbumService {


    private final IAlbumRepository albumRepository;

    @Autowired
    public AlbumServiceImpl(IAlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    @Override
    public List<SimpleAlbum> findAll() {
        return albumRepository.findAll();
    }

    @Override
    public SimpleAlbum findById(Long id) {
        return albumRepository.findById(id).get();
    }


    @Override
    public List<Album> findAlbumsDetails() {
        return albumRepository.getAlbumsDetails();
    }

}
