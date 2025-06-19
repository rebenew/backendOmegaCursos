package com.cursos.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Entity
    @Table(name = "tags")
    public class Tag {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false, unique = true)
        private String name;

        @ManyToMany(mappedBy = "tags")
        private Set<Course> courses = new HashSet<>();

        // Constructor adicional para crear tag solo con nombre
        public Tag(String name) {
            this.name = name;
        }
    }