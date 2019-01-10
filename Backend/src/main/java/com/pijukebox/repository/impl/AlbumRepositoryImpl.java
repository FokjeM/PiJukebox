package com.pijukebox.repository.impl;

import com.pijukebox.model.Album;
import com.pijukebox.model.AlbumGenre;
import com.pijukebox.model.Genre;
import com.pijukebox.repository.IAlbumRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
@NoArgsConstructor
@Transactional
public class AlbumRepositoryImpl implements IAlbumRepository {
    /* This gives us an EntityManager proxy, which gives or creates a thread-safe EntityManager for us every time we use it. */
    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager em;

//    @Override
//    public List<Album> getAll() {
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<Album> query = cb.createQuery(Album.class);
//        Root<Album> table = query.from(Album.class);
//        query.select(table);
//
//        return em.createQuery(query).getResultList();
//    }
//
//    @Override
//    public Album getById(Long id) {
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<Album> query = cb.createQuery(Album.class);
//        Root<Album> table = query.from(Album.class);
//        ParameterExpression<Long> parameter = cb.parameter(Long.class);
//        query.select(table).where(cb.equal(table.get("id"), parameter));
//
//        return em.createQuery(query).setParameter(parameter, id).getSingleResult();
//    }

    @Override
    public Optional<Album> findById(Long id) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Album> query = cb.createQuery(Album.class);
        Root<Album> table = query.from(Album.class);
        ParameterExpression<Long> parameter = cb.parameter(Long.class);
        query.select(table).where(cb.equal(table.get("id"), parameter));

//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<Album> query = cb.createQuery(Album.class);
//        Root<Album> table = query.from(Album.class);
//        ParameterExpression<Long> parameter = cb.parameter(Long.class);
//        query.multiselect(table).where(cb.equal(table.get("id"), parameter));
        return Optional.of(em.createQuery(query).setParameter(parameter, id).getSingleResult());
    }
    @Override
    public List<Album> findAll() {
        return null;
    }




    @Override
    public Album getAlbumGenre(Long id) {
        Map<Album, Genre> albumGenreRes = new HashMap<>();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Album> query = cb.createQuery(Album.class);
        Root<Album> table = query.from(Album.class);
        ParameterExpression<Long> parameter = cb.parameter(Long.class);
        query.select(table).where(cb.equal(table.get("id"), parameter));
//        Album album = new Album(1L, "Sheep Reliability", "NULL");
        Album album = em.createQuery(query).setParameter(parameter, id).getSingleResult();
//        Metamodel m = em.getMetamodel();
//        EntityType<Album> Album_ = m.entity(Album.class);
//        EntityType<Genre> Genre_ = m.entity(Genre.class);
//        Root<Genre> albumGenre = query.from(Genre.class);
//        Join<Album, Genre> genreJoin = table.join("genres", JoinType.INNER);
//        Join<Genre, AlbumGenre> albumGenreJoin = table.join("id", JoinType.INNER);
//        ParameterExpression<Long> parameter = cb.parameter(Long.class);
//        ListJoin<Album, Genre>  albumGenre = table.joinList("genres");
//        query.select(table).where()
//        albumGenreJoin.on(cb.equal(albumAlbumGenreJoin.get("album_id"), id))
//        query.multiselect(table,
//                          genreJoin);

        CriteriaQuery<AlbumGenre> query1 = cb.createQuery(AlbumGenre.class);
        Root<AlbumGenre> table1 = query1.from(AlbumGenre.class);
        query1.select(table1).where(cb.equal(table1.get("album_id"), parameter));

        List<AlbumGenre> albumGenres = em.createQuery(query1).setParameter(parameter, id).getResultList();

        CriteriaQuery<Genre> query2 = cb.createQuery(Genre.class);
        Root<Genre> table2 = query2.from(Genre.class);
        query2.select(table2);

        List<Genre> genres = em.createQuery(query2).getResultList();

        for(AlbumGenre albumGenre : albumGenres)
        {
            for(Genre genre : genres)
            {
                if(albumGenre.getGenre_id() == genre.getId())
                {
                    album.getGenres().add(genre);
                    System.out.println(album.getName() + " \n " +
                                       album.getId() + " \n " +
                                       genre.getName()+ " \n " +
                                       genre.getId());
                }
            }
        }
        return album;
//        return new Album();
    }
}
