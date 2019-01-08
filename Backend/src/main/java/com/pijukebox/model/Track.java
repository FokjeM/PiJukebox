package com.pijukebox.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@Table(schema = "pijukebox", name = "track")
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
    @JoinTable(name = "track_playlist")
    @OneToMany
    private List<TrackPlaylist> playlists;

    @NotNull
    @ManyToMany(mappedBy = "tracks")
    private Set<Album> tracks = new HashSet<>();
}
