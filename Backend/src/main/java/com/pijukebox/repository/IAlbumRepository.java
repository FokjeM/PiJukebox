package com.pijukebox.repository;

import com.pijukebox.model.Album;
import com.pijukebox.model.Genre;
import com.pijukebox.model.SimpleAlbum;
//import com.pijukebox.model.Genre;
import com.pijukebox.model.interfaces.IAlbum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
//import java.util.Map;
import java.util.Map;
import java.util.Optional;

public interface IAlbumRepository extends JpaRepository<Album, Long>{

    Optional<Album> findById(Long id);

    List<Album> findAll();

    List<Album> getAlbumsDetails();
}
