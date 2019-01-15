package com.pijukebox.model.genre;

import com.pijukebox.model.SqlElement;
import com.pijukebox.model.simple.SimpleAlbum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@Table(schema = "pijukebox", name = "genre")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Genre extends SqlElement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @NaturalId
    @Column(name = "name")
    private String name;
}
