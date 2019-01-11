package com.pijukebox.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
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

//    @OneToMany
//    private List<TrackPlaylist> playlists;

    @ManyToMany(mappedBy = "tracks", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Album> albums;
}
