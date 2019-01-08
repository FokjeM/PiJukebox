package com.pijukebox.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@Table(schema = "pijukebox", name = "album")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Album implements Serializable {

    // https://vladmihalcea.com/the-best-way-to-use-the-manytomany-annotation-with-jpa-and-hibernate/

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "releaseDate")
    private String releaseDate;

    @NotNull
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "album_track", joinColumns = @JoinColumn(name = "album_id"), inverseJoinColumns = @JoinColumn(name = "track_id"))
    private Set<Track> tracks = new HashSet<>();

    @NotNull
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "album_genre", joinColumns = @JoinColumn(name = "album_id"), inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genres = new HashSet<>();

    @NotNull
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "artist_album", joinColumns = @JoinColumn(name = "artist_id"), inverseJoinColumns = @JoinColumn(name = "album_id"))
    private Set<Artist> artists = new HashSet<>();
}
