package com.pijukebox.model.album;

import com.pijukebox.model.SqlElement;
import com.pijukebox.model.simple.SimpleGenre;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "album")
public class AlbumWithGenres extends SqlElement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "releaseDate")
    private String releaseDate;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "album_genre", catalog = "pijukebox", joinColumns = {@JoinColumn(name = "album_id", nullable = false)}, inverseJoinColumns = {@JoinColumn(name = "genre_id", nullable = false)})
    private Set<SimpleGenre> genres = new HashSet<>();
}