package com.pijukebox.model.playlist;

import com.pijukebox.model.SqlElement;
import com.pijukebox.model.simple.SimpleTrack;
import com.pijukebox.model.track.Track;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class PlaylistTracks extends SqlElement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @NotNull
    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "track_playlist", catalog = "pijukebox", joinColumns = {@JoinColumn(name = "playlist_id", nullable = false)}, inverseJoinColumns = {@JoinColumn(name = "track_id", nullable = false)})
    private Set<Track> tracks = new HashSet<>();
}
