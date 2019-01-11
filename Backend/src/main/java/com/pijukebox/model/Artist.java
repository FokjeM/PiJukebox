package com.pijukebox.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@Table(schema = "pijukebox", name = "artist")
public class Artist implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @NotNull
    @NaturalId
    @Column(name="name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "artists", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Album> albums;
}
