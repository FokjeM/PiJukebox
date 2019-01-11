package com.pijukebox.model;


import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SimpleAlbum implements Serializable {

    private Long id;

    private String name;

    private String releaseDate;

    public SimpleAlbum(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public SimpleAlbum(Long id, String name, String releaseDate) {
        this.id = id;
        this.name = name;
        this.releaseDate = releaseDate;
    }
}
