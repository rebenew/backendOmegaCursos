package com.cursos.backend.repository;

import com.cursos.backend.model.Course;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("SELECT DISTINCT c FROM Course c " +
            "WHERE (:category IS NULL OR EXISTS (" +
            "  SELECT 1 FROM c.tags t WHERE t.name = :category" +
            ")) " +
            "AND (:level IS NULL OR EXISTS (" +
            "  SELECT 1 FROM c.tags t WHERE t.name = :level" +
            "))")
    List<Course> findByCategoryAndLevel(@Param("category") String category,
            @Param("level") String level);

}
