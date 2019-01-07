package com.pijukebox.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@AllArgsConstructor
@Table(name = "artist_track")
public class ArtistTrack {
    @Id
    @Column(name="track_id")
    private Long track_id;

    @Column(name="artist_id")
    private Long artist_id;

    @Column(name="artist_main")
    private String mainArtist;
}
