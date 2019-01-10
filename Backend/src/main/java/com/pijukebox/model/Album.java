package com.pijukebox.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
//import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "album")
public class Album implements Serializable {

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
    

//    @ManyToMany(cascade= CascadeType.ALL)
//    @JoinTable(name="album_genre", catalog = "pijukebox", joinColumns = {
//            @JoinColumn(name="album_id", nullable = false)},//, updatable = false
//            inverseJoinColumns = {
//            @JoinColumn(name="genre_id", nullable = false)})//, updatable = false
    @Transient
    private ArrayList<Genre> genres = new ArrayList<>();

}
