package com.pijukebox.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@Table(schema = "pijukebox", name = "genre")
public class Genre {

    // https://vladmihalcea.com/the-best-way-to-use-the-manytomany-annotation-with-jpa-and-hibernate/

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @NaturalId
    @Column(name = "name")
    private String name;

    @NotNull
    @ManyToMany(mappedBy = "genres")
    private Set<Album> albums = new HashSet<>();
}
