package com.pijukebox.model.simple;

import com.pijukebox.model.SqlElement;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@Table(name = "track")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SimpleTrack extends SqlElement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(nullable = false, name="name")
    private String name;

    @NotNull
    @Column(nullable = false)
    private String description;

    @NotNull
    @Column(nullable = false, name="fileName")
    private String filename;
}
