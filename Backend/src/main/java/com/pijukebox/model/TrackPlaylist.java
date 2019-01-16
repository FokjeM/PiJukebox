package com.pijukebox.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@Table(name = "track_playlist")
public class TrackPlaylist {
    @Id
    @Column(name="playlist_id")
    private Long playlist_id;

    @Column(name="track_id")
    private Long track_id;

    @Column(name="position")
    private String position;
}