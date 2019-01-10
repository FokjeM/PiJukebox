package com.pijukebox.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "pijukebox", name = "album_genre")
public class AlbumGenre implements Serializable {
    @Id
    @Column(name = "album_id")
    private Long album_id;

    @Id
    @Column(name = "genre_id")
    private Long genre_id;
}
