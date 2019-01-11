package com.pijukebox.model;

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
@Table(name = "genre")
public class Genre  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @Column(name="name")
    private String name;

//    @OneToMany(targetEntity = AlbumGenre.class, mappedBy = "genre_id", fetch = FetchType.LAZY)
//    private List<AlbumGenre> albumGenres;
//     @ManyToMany(mappedBy = "genres")
//     private Set<Album> albums = new HashSet<>();
//    @OneToMany(cascade= CascadeType.ALL)
//    @JoinTable(name="album_genre", catalog = "pijukebox", joinColumns = {
//            @JoinColumn(name="album_id", nullable = false)},//, updatable = false
//            inverseJoinColumns = {
//                    @JoinColumn(name="genre_id", nullable = false)})//, updatable = false
//    private List<AlbumGenre> genreAlbums;
}
