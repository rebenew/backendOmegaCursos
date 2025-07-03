package com.cursos.backend.service;

import com.cursos.backend.DTO.CourseDTO;
import com.cursos.backend.DTO.TagDTO;
import com.cursos.backend.model.Course;
import com.cursos.backend.model.Tag;
import com.cursos.backend.repository.CourseRepository;
import com.cursos.backend.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TagRepository tagRepository;

    private CourseDTO mapToDTO(Course course) {
        List<TagDTO> tagDTOs = course.getTags().stream()
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
                tagDTOs
        );
    }


    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public List<Course> filterCourses(List<String> tags, String title, String modality) {
        List<Course> allCourses = courseRepository.findAll();

        return allCourses.stream()
                .filter(course -> {
                    boolean modalityMatch = (modality == null || modality.isBlank()) ||
                            course.getModality().name().equalsIgnoreCase(modality);

                    boolean titleMatch = (title == null || title.isBlank()) ||
                            course.getTitle().toLowerCase().contains(title.toLowerCase());

                    boolean tagsMatch = (tags == null || tags.isEmpty()) ||
                            course.getTags().stream().anyMatch(courseTag ->
                                    tags.stream().anyMatch(inputTag -> {
                                        String courseTagName = courseTag.getName().toLowerCase();
                                        String inputTagName = inputTag.toLowerCase();
                                        return courseTagName.contains(inputTagName) || inputTagName.contains(courseTagName);
                                    })
                            );

                    if (tagsMatch || titleMatch) {
                        System.out.println("✅ Coincide: " + course.getTitle());
                    }

                    return modalityMatch && (titleMatch || tagsMatch);
                })
                .collect(Collectors.toList());
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado con id: " + id));
    }


    @Transactional
    public Course createCourse(Course course) {
        Set<Tag> resolvedTags = new HashSet<>();

        for (Tag tag : course.getTags()) {
            if (tag.getId() != null) {
                Tag existingTag = tagRepository.findById(tag.getId())
                        .orElseThrow(() -> new RuntimeException("Tag no encontrado con ID: " + tag.getId()));
                resolvedTags.add(existingTag);
            }
        }

        course.setTags(resolvedTags);
        return courseRepository.save(course);
    }

    @Transactional
    public Course updateCourse(Long id, Course courseDetails) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado con id: " + id));
        course.setTitle(courseDetails.getTitle());
        course.setModality(courseDetails.getModality());
        course.setCertification(courseDetails.getCertification());
        course.setDuration(courseDetails.getDuration());
        course.setDescription(courseDetails.getDescription());
        course.setPrice(courseDetails.getPrice());
        if (courseDetails.getTags() != null) {
            Set<Tag> tags = new HashSet<>();
            for (Tag tag : courseDetails.getTags()) {
                Tag existingTag = tagRepository.findByName(tag.getName()).orElse(null);
                tags.add(Objects.requireNonNullElseGet(existingTag, () -> tagRepository.save(new Tag(tag.getName()))));
            }
            course.setTags(tags);
        }
        return courseRepository.save(course);
    }

    @Transactional
    public void deleteCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado con id: " + id));
        courseRepository.delete(course);
    }

    @Transactional
    public Course associateTagsToCourse(Long courseId, Set<Long> tagIds) {
        Course course = courseRepository.findById(courseId)
            .orElseThrow(() -> new RuntimeException("Curso no encontrado con id: " + courseId));
        Set<Tag> tags = new HashSet<>(tagRepository.findAllById(tagIds));
        course.setTags(tags);
        return courseRepository.save(course);
    }
    
    public boolean existsById(Long id) {
        return courseRepository.existsById(id);
    }
}
