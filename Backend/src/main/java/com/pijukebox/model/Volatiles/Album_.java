package com.pijukebox.model.Volatiles;

import com.pijukebox.model.Album;
import com.pijukebox.model.Artist;
import com.pijukebox.model.Genre;
import com.pijukebox.model.Track;

import javax.persistence.metamodel.SingularAttribute;
import java.util.List;

@javax.persistence.metamodel.StaticMetamodel(Album.class)
public class Album_{
    public static volatile SingularAttribute<Album, Long> id;
    public static volatile SingularAttribute<Album, String> name;
    public static volatile SingularAttribute<Album, String> releaseDate;
    public static volatile SingularAttribute<Album, List<Genre>> genres;
    public static volatile SingularAttribute<Album, List<Track>> tracks;
    public static volatile SingularAttribute<Album, List<Artist>> artist;
}
