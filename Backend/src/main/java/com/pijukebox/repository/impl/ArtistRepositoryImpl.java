package com.pijukebox.repository.impl;

import com.pijukebox.model.Album;
import com.pijukebox.model.Artist;
import com.pijukebox.repository.IArtistRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@AllArgsConstructor
@NoArgsConstructor
@Transactional
public class ArtistRepositoryImpl implements IArtistRepository {
    /* This gives us an EntityManager proxy, which gives or creates a thread-safe EntityManager for us every time we use it. */
    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager em;

    @Override
    public List<Artist> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Artist> query = cb.createQuery(Artist.class);
        Root<Artist> table = query.from(Artist.class);
        query.select(table);

        return em.createQuery(query).getResultList();
    }

    @Override
    public Artist getById(Long id) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Artist> query = cb.createQuery(Artist.class);
        Root<Artist> table = query.from(Artist.class);
        ParameterExpression<Long> parameter = cb.parameter(Long.class);
        query.select(table).where(cb.equal(table.get("id"), parameter));

        return em.createQuery(query).setParameter(parameter, id).getSingleResult();
    }
}
