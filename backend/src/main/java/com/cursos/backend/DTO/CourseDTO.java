package com.cursos.backend.DTO;

import com.cursos.backend.model.Modality;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class CourseDTO {
    private Long id;
    private String title;
    private Modality modality;
    private String certification;
    private String duration;
    private String description;
    private BigDecimal price;
    private List<TagDTO> tags;

    public CourseDTO(Long id, String title, Modality modality, String certification, String duration, String description, BigDecimal price, List<TagDTO> tags) {
        this.id = id;
        this.title = title;
        this.modality = modality;
        this.certification = certification;
        this.duration = duration;
        this.description = description;
        this.price = price;
        this.tags = tags;
    }
}
