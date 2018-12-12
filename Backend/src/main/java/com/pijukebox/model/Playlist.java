package com.pijukebox.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@Table(schema = "pijukebox", name = "playlist")
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // https://stackoverflow.com/questions/5478328/jpa-jointable-annotation
    @JoinTable
    @OneToMany
    private List<Track> tracks;
}
