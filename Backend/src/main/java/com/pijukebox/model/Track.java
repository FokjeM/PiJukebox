package com.pijukebox.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@Table(name = "track")
public class Track implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private String description;

    @NotNull
    @Column(nullable = false)
    private String filename;

    @NotNull
    @JoinTable(name="track_playlist")
    @OneToMany
    private List<TrackPlaylist> trackPlaylists;
//    // https://stackoverflow.com/questions/5478328/jpa-jointable-annotation
//    @NotNull
//    @JoinTable
//    @OneToMany
//    List<Album> albums;
//
//    // https://stackoverflow.com/questions/5478328/jpa-jointable-annotation
//    @NotNull
//    @JoinTable
//    @OneToMany
//    List<Artist> artists;
//
//    // https://stackoverflow.com/questions/5478328/jpa-jointable-annotation
//    @NotNull
//    @JoinTable
//    @OneToMany
//    List<Genre> genres;

}
