package com.pijukebox.model.album;

import com.pijukebox.model.BaseModel;
import com.pijukebox.model.simple.SimpleTrack;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "album")
public class AlbumWithTracks extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "releaseDate")
    private String releaseDate;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "album_track", catalog = "pijukebox", joinColumns = {@JoinColumn(name = "album_id", nullable = false)}, inverseJoinColumns = {@JoinColumn(name = "track_id", nullable = false)})
    private Set<SimpleTrack> tracks = new HashSet<>();
}