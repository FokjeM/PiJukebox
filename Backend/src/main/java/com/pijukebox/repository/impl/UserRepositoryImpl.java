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
public class UserRepositoryImpl implements IUserRepository {

    /* This gives us an EntityManager proxy, which gives or creates a thread-safe EntityManager for us every time we use it. */
    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager em;

    @Override
    public List<User> findAll() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> userTable = query.from(User.class);
        query.select(userTable);

        return em.createQuery(query).getResultList();
    }

    @Override
    public User getById(Long id) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> userTable = query.from(User.class);
        ParameterExpression<Long> parameter = criteriaBuilder.parameter(Long.class);
        query.select(userTable).where(criteriaBuilder.equal(userTable.get("id"), parameter));

        return em.createQuery(query).setParameter(parameter, id).getSingleResult();
    }

    @Override
    public User getByName(String name) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> userTable = query.from(User.class);
        ParameterExpression<String> parameter = criteriaBuilder.parameter(String.class);
        query.select(userTable).where(criteriaBuilder.equal(userTable.get("name"), parameter));

        return em.createQuery(query).setParameter(parameter, name).getSingleResult();
    }
}
