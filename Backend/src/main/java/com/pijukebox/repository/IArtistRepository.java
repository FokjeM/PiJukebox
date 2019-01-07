package com.pijukebox.repository;


import com.pijukebox.model.Artist;

import java.util.List;

public interface IArtistRepository {
    List<Artist> findAll();

    Artist getById(Long id);

    List<Artist> findByName(String name);
}
