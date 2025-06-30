package com.cursos.backend.repository;

import com.cursos.backend.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {
    @Query("""
    SELECT DISTINCT c FROM Course c
    LEFT JOIN c.tags t
    WHERE (:tags IS NULL OR t.name IN :tags)
      AND (:title IS NULL OR LOWER(c.title) LIKE LOWER(CONCAT('%', :title, '%')))
      AND (:modality IS NULL OR LOWER(c.modality) = LOWER(:modality))
""")
    List<Course> findCoursesWithFilters(
            @Param("tags") List<String> tags,
            @Param("title") String title,
            @Param("modality") String modality
    );

}
