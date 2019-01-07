package com.pijukebox.repository.impl;

import com.pijukebox.model.Genre;
import com.pijukebox.model.Genre;
import com.pijukebox.repository.IGenreRepository;

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
public class GenreRepositoryImpl implements IGenreRepository {
    /* This gives us an EntityManager proxy, which gives or creates a thread-safe EntityManager for us every time we use it. */
    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager em;

    @Override
    public List<Genre> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Genre> query = cb.createQuery(Genre.class);
        Root<Genre> table = query.from(Genre.class);
        query.select(table);

        return em.createQuery(query).getResultList();
    }

    @Override
    public Genre getById(Long id) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Genre> query = cb.createQuery(Genre.class);
        Root<Genre> table = query.from(Genre.class);
        ParameterExpression<Long> parameter = cb.parameter(Long.class);
        query.select(table).where(cb.equal(table.get("id"), parameter));

        return em.createQuery(query).setParameter(parameter, id).getSingleResult();
    }
}
