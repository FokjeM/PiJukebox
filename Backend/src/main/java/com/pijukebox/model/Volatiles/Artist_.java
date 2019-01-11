package com.pijukebox.model.Volatiles;

import com.pijukebox.model.Artist;

import javax.persistence.metamodel.SingularAttribute;

@javax.persistence.metamodel.StaticMetamodel(Artist.class)
public class Artist_ {
    public static volatile SingularAttribute<Artist, Long> id;
    public static volatile SingularAttribute<Artist, String> name;
}
