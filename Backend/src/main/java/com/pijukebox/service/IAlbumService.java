package com.pijukebox.service;

import com.pijukebox.model.Album;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface IAlbumService {

    List<Album> findAll();

    Optional<Album> findById(Long id);
}
