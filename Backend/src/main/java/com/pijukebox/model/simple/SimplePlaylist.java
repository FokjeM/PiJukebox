package com.pijukebox.model.simple;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Entity
@AllArgsConstructor
@Table(schema = "pijukebox", name = "playlist")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SimplePlaylist implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @NotNull
    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

}
