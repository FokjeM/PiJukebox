package com.pijukebox.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pijukebox.model.SqlElement;
import com.pijukebox.model.simple.SimplePlaylist;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@Table(schema = "pijukebox", name = "user")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserPlaylist extends SqlElement implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "firstName", nullable = false)
    private String firstname;

    @Column(name = "lastName", nullable = false)
    private String lastname;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name = "role_id", nullable = false)
    private String roleId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "playlist", catalog = "pijukebox", joinColumns = {@JoinColumn(name = "user_id", nullable = false)})//, inverseJoinColumns = {@JoinColumn(name = "album_id", nullable = false)}
    private Set<SimplePlaylist> playlists = new HashSet<>();
}