package com.cursos.backend.service;

import com.cursos.backend.model.Tag;
import com.cursos.backend.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    @Transactional
    public Tag createTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Transactional
    public Tag updateTag(Long id, Tag tagDetails) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag no encontrado con id: " + id));
        tag.setName(tagDetails.getName());
        return tagRepository.save(tag);
    }

    public Optional<Tag> getTagById(Long id) {
        return tagRepository.findById(id);
    }
} 