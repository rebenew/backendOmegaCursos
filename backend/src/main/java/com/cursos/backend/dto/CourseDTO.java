package com.cursos.backend.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {
    private Long id;
    private String title;
    private String modality;
    private String certification;
    private String duration;
    private String description;
    private BigDecimal price;
    private List<String> tags; // Nombres de las etiquetas
}
