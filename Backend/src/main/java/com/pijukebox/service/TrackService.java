package com.pijukebox.service;

import com.pijukebox.model.Track;

import java.util.List;

public interface TrackService {

    List<Track> findAll();

    List<Track> findMany(Long amountToFind);

    Track findById(Long id);
}
