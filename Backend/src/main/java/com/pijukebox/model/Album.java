package com.pijukebox.model;

import com.pijukebox.model.interfaces.IAlbum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "album")
public class Album extends IAlbum implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="releaseDate")
    private String releaseDate;


//    @OneToMany(cascade= CascadeType.ALL)
//    @JoinTable(name="album_genre", catalog = "pijukebox", joinColumns = {
//            @JoinColumn(name="album_id", nullable = false)},//, updatable = false
//            inverseJoinColumns = {
//            @JoinColumn(name="genre_id", nullable = false)})//, updatable = false
//    private List<AlbumGenre> albumGenres;
    

    @ManyToMany(cascade= CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name="album_genre", catalog = "pijukebox", joinColumns = {
            @JoinColumn(name="album_id", nullable = false)},//, updatable = false
            inverseJoinColumns = {
            @JoinColumn(name="genre_id", nullable = false)})//, updatable = false
//    @Transient
    private Set<Genre> genres = new HashSet<>();


    @ManyToMany(cascade= CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name="album_track", catalog = "pijukebox", joinColumns = {
            @JoinColumn(name="album_id", nullable = false)},//, updatable = false
            inverseJoinColumns = {
                    @JoinColumn(name="track_id", nullable = false)})//, updatable = false
//    @Transient
    private Set<Track> tracks = new HashSet<>();


    @ManyToMany(cascade= CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name="artist_album", catalog = "pijukebox", joinColumns = {
            @JoinColumn(name="album_id", nullable = false)},//, updatable = false
            inverseJoinColumns = {
                    @JoinColumn(name="artist_id", nullable = false)})//, updatable = false
//    @Transient
    private Set<Artist> artists = new HashSet<>();


//    @OneToMany(cascade= CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinTable(name="artist_album", catalog = "pijukebox", joinColumns = {
//            @JoinColumn(name="album_id", nullable = false)})//, updatable = false
//    @Transient
//    private Set<AlbumArtist> albumArtists = new HashSet<>();

}
