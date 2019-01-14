package com.pijukebox.repository.impl;

import com.pijukebox.model.*;
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

    @Override
    public Optional<SimpleAlbum> findById(Long id) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SimpleAlbum> query = cb.createQuery(SimpleAlbum.class);
        Root<SimpleAlbum> table = query.from(SimpleAlbum.class);
        ParameterExpression<Long> parameter = cb.parameter(Long.class);
        query.select(table).where(cb.equal(table.get("id"), parameter));
        return Optional.of(em.createQuery(query).setParameter(parameter, id).getSingleResult());
    }
    @Override
    public List<SimpleAlbum> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SimpleAlbum> query = cb.createQuery(SimpleAlbum.class);
        query.from(SimpleAlbum.class);
        List<SimpleAlbum> simpleAlbums = em.createQuery(query).getResultList();
        return simpleAlbums;
    }


    @Override
    public List<Album> getAlbumsDetails() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Album> query = cb.createQuery(Album.class);
        query.from(Album.class);
        List<Album> album_genres = em.createQuery(query).getResultList();
        return album_genres;    }
}
