package com.cursos.backend.controller;

import com.cursos.backend.DTO.CourseDTO;
import com.cursos.backend.DTO.TagDTO;
import com.cursos.backend.model.Course;
import com.cursos.backend.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    private CourseDTO mapToDTO(Course course) {
        List<TagDTO> tags = course.getTags().stream()
                .map(tag -> new TagDTO(tag.getId(), tag.getName()))
                .collect(Collectors.toList());

        return new CourseDTO(
                course.getId(),
                course.getTitle(),
                course.getModality(),
                course.getCertification(),
                course.getDuration(),
                course.getDescription(),
                course.getPrice(),
                tags
        );
    }

    @GetMapping
    public ResponseEntity<List<CourseDTO>> getCourses(
            @RequestParam(required = false) List<String> tags,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String modality) {
        try {
            List<Course> courses = courseService.filterCourses(tags, title, modality);
            List<CourseDTO> courseDTOs = courses.stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(courseDTOs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Long id) {
        try {
            Course course = courseService.getCourseById(id);
            return ResponseEntity.ok(course);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("El curso con ID " + id + " no existe.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno del servidor.");
        }
    }


    @PostMapping
    public ResponseEntity<?> createCourse(@RequestBody Course course) {
        try {
            Course createdCourse = courseService.createCourse(course);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al crear el curso: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable Long id, @RequestBody Course courseDetails) {
        if (!courseService.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("El curso con ID " + id + " no existe.");
        }
        try {
            return ResponseEntity.ok(courseService.updateCourse(id, courseDetails));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al actualizar el curso: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long id) {
        if (!courseService.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("El curso con ID " + id + " no existe.");
        }
        try {
            courseService.deleteCourse(id);
            return ResponseEntity.ok("Curso eliminado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar el curso: " + e.getMessage());
        }
    }
}


