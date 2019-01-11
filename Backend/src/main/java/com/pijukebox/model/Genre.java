package com.pijukebox.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@Table(schema = "pijukebox", name = "genre")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Genre implements Serializable {

    // https://vladmihalcea.com/the-best-way-to-use-the-manytomany-annotation-with-jpa-and-hibernate/

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @NaturalId
    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "genres", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Album> albums;
}
