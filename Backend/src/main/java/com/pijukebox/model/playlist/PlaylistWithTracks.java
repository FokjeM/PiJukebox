package com.pijukebox.model.playlist;

import com.pijukebox.model.BaseModel;
import com.pijukebox.model.simple.SimpleTrack;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
@Table(schema = "pijukebox", name = "playlist")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlaylistWithTracks extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "user_id")
    private Long userID;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "track_playlist", catalog = "pijukebox", joinColumns = {@JoinColumn(name = "playlist_id", nullable = false)}, inverseJoinColumns = {@JoinColumn(name = "track_id", nullable = false)})
    private Set<SimpleTrack> tracks = new HashSet<>();
}
