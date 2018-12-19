package com.pijukebox.data.jpa;

import com.pijukebox.data.model.AnnotatedImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringJpaRepository extends JpaRepository<AnnotatedImage, Long> {
}
