package com.cursos.backend.DTO;

import com.cursos.backend.model.Modality;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class CourseRequestDTO {
    private Long id;
    private String title;
    private Modality modality;
    private String certification;
    private String duration;
    private String description;
    private BigDecimal price;
    private List<Long> tags; // solo IDs
}