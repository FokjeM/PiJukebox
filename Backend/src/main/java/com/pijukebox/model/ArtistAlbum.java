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
@Table(name = "artist_album")
public class ArtistAlbum {
    @Id
    @Column(name="album_id")
    private Long album_id;

    @Column(name="artist_id")
    private Long artist_id;

    @Column(name="artist_main")
    private String mainArtist;
}
