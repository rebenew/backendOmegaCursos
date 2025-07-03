package com.cursos.backend.controller;

import com.cursos.backend.DTO.CourseRequestDTO;
import com.cursos.backend.DTO.CourseResponseDTO;
import com.cursos.backend.DTO.TagDTO;
import com.cursos.backend.model.Course;
import com.cursos.backend.model.Tag;
import com.cursos.backend.repository.TagRepository;
import com.cursos.backend.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    private CourseResponseDTO mapToDTO(Course course) {
        List<TagDTO> tagDTOs = course.getTags().stream()
                .map(tag -> new TagDTO(tag.getId(), tag.getName()))
                .collect(Collectors.toList());

        return new CourseResponseDTO(
                course.getId(),
                course.getTitle(),
                course.getCertification(),
                course.getModality(),
                course.getDuration(),
                course.getDescription(),
                course.getPrice(),
                tagDTOs
        );
    }



    @GetMapping
    public ResponseEntity<List<CourseResponseDTO>> getCourses(
            @RequestParam(required = false) List<String> tags,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String modality) {
        try {
            List<Course> courses = courseService.filterCourses(tags, title, modality);
            List<CourseResponseDTO> courseResponseDTOS = courses.stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(courseResponseDTOS);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Long id) {
        try {
            Course course = courseService.getCourseById(id);
            CourseResponseDTO dto = mapToDTO(course);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("El curso con ID " + id + " no existe.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno del servidor.");
        }
    }


    @PostMapping
    public ResponseEntity<?> createCourse(@RequestBody CourseRequestDTO dto) {
        Course course = mapToEntity(dto);
        Course saved = courseService.createCourse(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapToDTO(saved));  // <== aquí el cambio
    }

    @Autowired
    private TagRepository tagRepository;

    private Course mapToEntity(CourseRequestDTO dto) {
        Course course = new Course();
        course.setTitle(dto.getTitle());
        course.setModality(dto.getModality());
        course.setCertification(dto.getCertification());
        course.setDuration(dto.getDuration());
        course.setDescription(dto.getDescription());
        course.setPrice(dto.getPrice());

        if (dto.getTags() != null) {
            Set<Tag> tags = dto.getTags().stream()
                    .map(tagId -> tagRepository.findById(tagId)
                            .orElseThrow(() -> new RuntimeException("Tag no encontrado con ID: " + tagId)))
                    .collect(Collectors.toSet());
            course.setTags(tags);
        }

        return course;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable Long id, @RequestBody CourseRequestDTO requestDTO) {
        if (!courseService.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("El curso con ID " + id + " no existe.");
        }

        try {
            Course course = mapToEntity(requestDTO);
            Course updatedCourse = courseService.updateCourse(id, course);
            CourseResponseDTO updatedDTO = mapToDTO(updatedCourse);
            return ResponseEntity.ok(updatedDTO);
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


