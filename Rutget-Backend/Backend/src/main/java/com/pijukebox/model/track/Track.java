package com.pijukebox.model;

import com.pijukebox.model.simple.SimpleGenre;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@Table(schema = "pijukebox", name = "track")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Track implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @NaturalId
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private String description;

    @NotNull
    @Column(nullable = false)
    private String filename;


    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "album_genre", catalog = "pijukebox", joinColumns = {@JoinColumn(name = "album_id", nullable = false)}, inverseJoinColumns = {@JoinColumn(name = "genre_id", nullable = false)})
    private Set<SimpleGenre> genres = new HashSet<>();
}
