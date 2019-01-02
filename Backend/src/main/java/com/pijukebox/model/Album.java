package com.pijukebox.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@Table(schema = "pijukebox", name = "album")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // https://stackoverflow.com/questions/5478328/jpa-jointable-annotation
    @JoinTable
    @OneToMany
    List<Artist> artists;

    // https://stackoverflow.com/questions/5478328/jpa-jointable-annotation
    @JoinTable
    @OneToMany
    List<Genre> genres;
}
