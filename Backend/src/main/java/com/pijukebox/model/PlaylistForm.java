package com.pijukebox.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlaylistForm implements Serializable {
    @Column(name="title")
    private String title;

    @Column(name="description")
    private String description;
}
