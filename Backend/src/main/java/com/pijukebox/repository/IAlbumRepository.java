package com.pijukebox.repository;

import com.pijukebox.model.Album;
import com.pijukebox.model.Genre;
//import com.pijukebox.model.Genre;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
//import java.util.Map;
import java.util.Map;
import java.util.Optional;

public interface IAlbumRepository{

    Optional<Album> findById(Long id);

    List<Album> findAll();

    Album getAlbumGenre(Long id);

}
