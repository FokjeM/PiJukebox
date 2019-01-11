package com.pijukebox.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@Table(schema = "pijukebox", name = "album")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Album implements Serializable {

    // https://vladmihalcea.com/the-best-way-to-use-the-manytomany-annotation-with-jpa-and-hibernate/
    // https://en.wikibooks.org/wiki/Java_Persistence/ManyToMany
    // https://vladmihalcea.com/jpa-hibernate-synchronize-bidirectional-entity-associations/

    // https://stackoverflow.com/questions/37243159/mappedby-in-bi-directional-manytomany-what-is-the-reason/37312213#37312213
    // https://stackoverflow.com/questions/10968536/jpa-difference-in-the-use-of-the-mappedby-property-to-define-the-owning-entity

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @NaturalId
    @Column(name = "name")
    private String name;

    @Column(name = "releaseDate")
    private String releaseDate;


    @ManyToMany(targetEntity = Track.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinTable(name = "album_track", joinColumns = @JoinColumn(name = "album_id", referencedColumnName = "id", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "track_id", referencedColumnName = "id", nullable = false, updatable = false))
    private Set<Track> tracks;

    @ManyToMany(targetEntity = Genre.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinTable(name = "album_genre", joinColumns = @JoinColumn(name = "album_id", referencedColumnName = "id", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "id", nullable = false, updatable = false))
    private Set<Genre> genres;

    @ManyToMany(targetEntity = Artist.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinTable(name = "artist_album", joinColumns = @JoinColumn(name = "album_id", referencedColumnName = "id", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "artist_id", referencedColumnName = "id", nullable = false, updatable = false))
    private Set<Artist> artists;
}
