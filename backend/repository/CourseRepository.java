package com.equipo.backend.repository;

import com.example.demo.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByCategory(String category);

    List<Course> findByLevel(String level);

    List<Course> findByCategoryAndLevel(String category, String level);
}