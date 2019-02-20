package com.pijukebox.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@Table(name = "role")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Role extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;
}
