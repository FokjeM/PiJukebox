package com.pijukebox.model.artist;

import com.pijukebox.model.BaseModel;
import com.pijukebox.model.simple.SimpleAlbum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "pijukebox", name = "artist")
public class ArtistWithAlbums extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @NaturalId
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "artist_album", catalog = "pijukebox", joinColumns = {@JoinColumn(name = "artist_id", nullable = false)}, inverseJoinColumns = {@JoinColumn(name = "album_id", nullable = false)})
    private Set<SimpleAlbum> albums = new HashSet<>();
}

