package com.pijukebox.model.genre;

import com.pijukebox.model.SqlElement;
import com.pijukebox.model.simple.SimpleTrack;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@Table(schema = "pijukebox", name = "genre")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GenreWithTracks extends SqlElement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @NaturalId
    @Column(name = "name")
    private String name;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "track_genre", catalog = "pijukebox", joinColumns = {@JoinColumn(name = "genreId", nullable = false)}, inverseJoinColumns = {@JoinColumn(name = "trackId", nullable = false)})
    private Set<SimpleTrack> tracks = new HashSet<>();
}