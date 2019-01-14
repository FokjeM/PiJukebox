package com.pijukebox.model;

import com.pijukebox.model.simple.SimpleAlbum;
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
@NoArgsConstructor
@Table(schema = "pijukebox", name = "artist")
public class Artist implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
