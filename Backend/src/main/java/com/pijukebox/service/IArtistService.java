package com.pijukebox.service;

import com.pijukebox.model.Artist;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IArtistService {

    List<Artist> findAll();

    List<Artist> findMany(Long amount);

    Artist findByName(String name);
}
