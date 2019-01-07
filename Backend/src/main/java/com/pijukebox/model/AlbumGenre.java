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
@Table(name = "album_genre")
public class AlbumGenre {
    @Id
    @Column(name="genre_id")
    private Long genre_id;

    @Column(name="album_id")
    private Long album_id;
}
