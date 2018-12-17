package com.pijukebox.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import javax.validation.constraints.NotNull;

@Entity
@Data
@AllArgsConstructor
@Table(schema = "pijukebox", name = "role")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String code;

    @NotNull
    private String description;

}
