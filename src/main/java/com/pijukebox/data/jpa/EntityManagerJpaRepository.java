package com.pijukebox.data.jpa;

import com.pijukebox.data.ImageRepository;
import com.pijukebox.data.InterImage;
import com.pijukebox.data.model.AnnotatedImage;
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

@Repository
@AllArgsConstructor
@NoArgsConstructor
public class EntityManagerJpaRepository implements ImageRepository {

  /**
   * This gives us an EntityManager.
   * Or, well, a proxy to one. Which gives or creates a thread-safe EntityManager for us
   * every time we use it.
   */
  @PersistenceContext(unitName = "entityManagerFactory")
  private EntityManager em;

  @Override
  @Transactional
  public InterImage getImageById(Long id) {
    CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
    CriteriaQuery<AnnotatedImage> query = criteriaBuilder.createQuery(AnnotatedImage.class);
    Root<AnnotatedImage> from = query.from(AnnotatedImage.class);
    ParameterExpression<Long> parameter = criteriaBuilder.parameter(Long.class);
    query.select(from).where(criteriaBuilder.equal(from.get("id"), parameter));

    return em.createQuery(query).setParameter(parameter, id).getSingleResult();
    // A simpler way of doing it
//    return em.find(AnnotatedHouse.class, id);
  }

//  @Override
//  @Transactional
//  public House getByID(Long id) {
//    CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
//    CriteriaQuery<AnnotatedHouse> query = criteriaBuilder.createQuery(AnnotatedHouse.class);
//    Root<AnnotatedHouse> from = query.from(AnnotatedHouse.class);
//    ParameterExpression<Long> parameter = criteriaBuilder.parameter(Long.class);
//    query.select(from).where(criteriaBuilder.equal(from.get("id"), parameter));
//
//    return em.createQuery(query).setParameter(parameter, id).getSingleResult();
//    // A simpler way of doing it
////    return em.find(AnnotatedHouse.class, id);
//  }

//  public void createTrack()
}
