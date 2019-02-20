package com.pijukebox.model.simple;

import com.pijukebox.model.BaseModel;
import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Table(schema = "pijukebox", name = "genre")
public class SimpleGenre extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

}
