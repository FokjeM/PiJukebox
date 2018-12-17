package com.pijukebox.service.impl;

import com.pijukebox.model.Artist;
import com.pijukebox.service.ArtistService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.transaction.Transactional;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
/// the artist implementation it is the connection that JPA is doing with the database... needs to be written

public class ArtistServiceImpl implements ArtistService {

    @Override
    @Transactional
    public List<Artist> findAll()
    {
        return null;
    }

    @Override
    public List<Artist> findMany(Long amount) {
        return null;
    }

    @Override
    public Artist findByName(String name) {
        return null;
    }

    @Override
    public Artist addArtist(Artist artist) {
        return null;
    }

    @Override
    public Artist deleteArtist(Long id) {
        return null;
    }
}
