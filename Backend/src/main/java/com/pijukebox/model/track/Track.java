package com.pijukebox.model.track;

import com.pijukebox.model.SqlElement;
import com.pijukebox.model.simple.SimpleArtist;
import com.pijukebox.model.simple.SimpleGenre;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@Table(schema = "pijukebox", name = "track")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Track extends SqlElement implements Serializable {

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


    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "track_genre", catalog = "pijukebox", joinColumns = {@JoinColumn(name = "trackId", nullable = false)}, inverseJoinColumns = {@JoinColumn(name = "genreId", nullable = false)})
    private Set<SimpleGenre> genres = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "artist_track", catalog = "pijukebox", joinColumns = {@JoinColumn(name = "track_id", nullable = false)}, inverseJoinColumns = {@JoinColumn(name = "artist_id", nullable = false)})
    private Set<SimpleArtist> artists = new HashSet<>();
}
