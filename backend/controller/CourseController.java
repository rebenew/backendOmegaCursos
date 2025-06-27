package com.backend.controller;

import com.model.Course;
import com.repository.CourseRepository;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping
    public ResponseEntity<?> getCourses(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String level) {

        try {
            List<Course> courses;

            if (category != null && level != null) {
                courses = courseRepository.findByCategoryAndLevel(category, level);
            } else if (category != null) {
                courses = courseRepository.findByCategory(category);
            } else if (level != null) {
                courses = courseRepository.findByLevel(level);
            } else {
                courses = courseRepository.findAll();
            }

            if (courses.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No se encontraron cursos con esos filtros.");
            }

            return ResponseEntity.ok(courses);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Ocurrió un error al procesar la solicitud: " + e.getMessage());
        }
    }
}