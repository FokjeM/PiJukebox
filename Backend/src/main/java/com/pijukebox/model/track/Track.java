package com.pijukebox.model.track;

import com.pijukebox.model.BaseModel;
import com.pijukebox.model.simple.SimpleArtist;
import com.pijukebox.model.simple.SimpleGenre;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Table(schema = "pijukebox", name = "track")
public class Track extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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


    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "track_genre", catalog = "pijukebox", joinColumns = {@JoinColumn(name = "track_id", nullable = false)}, inverseJoinColumns = {@JoinColumn(name = "genre_id", nullable = false)})
    private Set<SimpleGenre> genres = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "artist_track", catalog = "pijukebox", joinColumns = {@JoinColumn(name = "track_id", nullable = false)}, inverseJoinColumns = {@JoinColumn(name = "artist_id", nullable = false)})
    private Set<SimpleArtist> artists = new HashSet<>();
}
