package com.pijukebox.model.genre;

import com.pijukebox.model.BaseModel;
import com.pijukebox.model.simple.SimpleAlbum;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@Table(schema = "pijukebox", name = "genre")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GenreWithAlbums extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NaturalId
    @Column(name = "name")
    private String name;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "album_genre", catalog = "pijukebox", joinColumns = {@JoinColumn(name = "genre_id", nullable = false)}, inverseJoinColumns = {@JoinColumn(name = "album_id", nullable = false)})
    private Set<SimpleAlbum> albums = new HashSet<>();
}