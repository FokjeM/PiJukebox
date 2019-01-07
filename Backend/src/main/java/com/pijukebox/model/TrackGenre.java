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
@Table(name = "track_genre")
public class TrackGenre {

    @Id
    @Column(name="genreId")
    private Long genre_id;

    @Column(name="trackId")
    private Long track_id;
}
