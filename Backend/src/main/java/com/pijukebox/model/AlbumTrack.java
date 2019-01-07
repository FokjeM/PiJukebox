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
@Table(name = "album_track")
public class AlbumTrack {
    @Id
    @Column(name="album_id")
    private Long album_id;

    @Column(name="track_id")
    private Long track_id;
}
