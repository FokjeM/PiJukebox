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
@Table(schema = "pijukebox", name = "album_track")
public class AlbumTrack implements Serializable {
    @Id
    @Column(name = "album_id")
    private Long album_id;

    @Id
    @Column(name = "track_id")
    private Long track_id;
}
