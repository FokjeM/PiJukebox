package com.pijukebox.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@Table(schema = "pijukebox", name = "artist_album")
public class ArtistAlbum implements Serializable {
    @Id
    @Column(name = "album_id")
    private Long album_id;

    @Id
    @Column(name = "artist_id")
    private Long artist_id;

    @Column(name = "artist_main")
    private String mainArtist;
}
