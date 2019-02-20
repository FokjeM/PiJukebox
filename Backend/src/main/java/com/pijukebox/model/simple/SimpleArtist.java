package com.pijukebox.model.simple;

import com.pijukebox.model.BaseModel;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Table(schema = "pijukebox", name = "artist")
public class SimpleArtist extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;
}
