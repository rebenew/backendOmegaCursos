package com.cursos.backend.dto;

import java.math.BigDecimal;
import java.util.Set;

public class CourseRequest {
    private String title;
    private String modality;
    private String certification;
    private String duration;
    private String description;
    private BigDecimal price;
    private Set<TagDTO> tags;

    // Getters y Setters

    public static class TagDTO {
        private String name;
        // Getters y Setters
    }
}
