package com.cursos.backend.service;

import com.cursos.backend.model.Course;
import com.cursos.backend.model.Tag;
import com.cursos.backend.repository.CourseRepository;
import com.cursos.backend.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TagRepository tagRepository;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado con id: " + id));
    }

    @Transactional
    public Course createCourse(Course course) {
        Set<Tag> tags = new HashSet<>();
        if (course.getTags() != null) {
            for (Tag tag : course.getTags()) {
                Tag existingTag = tagRepository.findByName(tag.getName()).orElse(null);
                if (existingTag != null) {
                    tags.add(existingTag);
                } else {
                    tags.add(tagRepository.save(new Tag(tag.getName())));
                }
            }
        }
        course.setTags(tags);
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
                if (existingTag != null) {
                    tags.add(existingTag);
                } else {
                    tags.add(tagRepository.save(new Tag(tag.getName())));
                }
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
