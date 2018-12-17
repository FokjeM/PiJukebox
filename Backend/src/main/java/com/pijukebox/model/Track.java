package com.pijukebox.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@Table(schema = "pijukebox", name = "track")
public class Track implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private String filename;

    // https://stackoverflow.com/questions/5478328/jpa-jointable-annotation
    @NotNull
    @JoinTable
    @OneToMany
    List<Album> albums;

    // https://stackoverflow.com/questions/5478328/jpa-jointable-annotation
    @NotNull
    @JoinTable
    @OneToMany
    List<Artist> artists;

    // https://stackoverflow.com/questions/5478328/jpa-jointable-annotation
    @NotNull
    @JoinTable
    @OneToMany
    List<Genre> genres;

}
