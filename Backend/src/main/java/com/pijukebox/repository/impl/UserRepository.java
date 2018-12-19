package com.pijukebox.repository.impl;

import com.pijukebox.model.User;
import com.pijukebox.repository.IUserRepository;
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
public class UserRepository implements IUserRepository {

    /**
     * This gives us an EntityManager.
     * Or, well, a proxy to one. Which gives or creates a thread-safe EntityManager for us
     * every time we use it.
     */
    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager em;

    @Override
    public List<User> findAll() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> from = query.from(User.class);
        query.select(from);

        return em.createQuery(query).getResultList();
    }


    @Override
    public User getById(Long id) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> from = query.from(User.class);
        ParameterExpression<Long> parameter = criteriaBuilder.parameter(Long.class);
        query.select(from).where(criteriaBuilder.equal(from.get("id"), parameter));

        return em.createQuery(query).setParameter(parameter, id).getSingleResult();
    }

    @Override
    public User getByName(String name) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> from = query.from(User.class);
        ParameterExpression<String> parameter = criteriaBuilder.parameter(String.class);
        query.select(from).where(criteriaBuilder.equal(from.get("name"), parameter));

        return em.createQuery(query).setParameter(parameter, name).getSingleResult();
    }
}
