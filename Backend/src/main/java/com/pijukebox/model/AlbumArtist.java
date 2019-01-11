package com.pijukebox.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "artist_album")
public class AlbumArtist implements Serializable {
    @Id
    @Column(name = "album_id")
    private Long album_id;

    @Id
    @Column(name = "artist_id")
    private Long artist_id;

    @Column(name= "artist_main")
    private String artist_main;
}
