package com.pijukebox.model.Volatiles;

import com.pijukebox.model.Genre;

import javax.persistence.metamodel.SingularAttribute;

@javax.persistence.metamodel.StaticMetamodel(Genre.class)
public class Genre_ {
    public static volatile SingularAttribute<Genre, Long> id;
    public static volatile SingularAttribute<Genre, String> name;
}
