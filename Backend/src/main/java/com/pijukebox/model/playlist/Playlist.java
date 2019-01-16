package com.pijukebox.model.playlist;

import com.pijukebox.model.SqlElement;
import com.pijukebox.model.simple.SimpleAlbum;
import com.pijukebox.model.simple.SimpleTrack;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

@Entity
@AllArgsConstructor
@Table(schema = "pijukebox", name = "playlist")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Playlist extends SqlElement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @NotNull
    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "track_playlist", catalog = "pijukebox", joinColumns = {@JoinColumn(name = "playlist_id", nullable = false)}, inverseJoinColumns = {@JoinColumn(name = "track_id", nullable = false)})
    private Set<SimpleTrack> tracks = new HashSet<>();
}
