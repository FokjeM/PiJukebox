package test.api.data.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test.api.data.model.ImageAnnotated;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpringJpaRepository extends JpaRepository<ImageAnnotated, Long> {
    List<ImageAnnotated> findAll();
    Optional<ImageAnnotated> findById(Long id);
}
