package com.pijukebox.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import javax.validation.constraints.NotNull;

@Entity
@Table(schema = "", name = "")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private Long id;

    @NotNull
    @Getter
    @Setter
    private String code;

    @NotNull
    @Getter
    @Setter
    private String description;

}
