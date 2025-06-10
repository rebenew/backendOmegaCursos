package com.cursos.backend.controller;

import com.cursos.backend.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    private CourseRepository courseRepository;

    // DELETE/courses/{id} + administrador
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteCourse(@PathVariable Long id) {
        if (!courseRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El curso con ID " + id + " no existe.");
        }

        courseRepository.deleteById(id);
        return ResponseEntity.ok("Curso eliminado exitosamente.");
    }
}

