package com.cursos.backend.DTO;

import com.cursos.backend.model.Modality;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class CourseResponseDTO {
    private Long id;
    private String title;
    private Modality modality;
    private String certification;
    private String duration;
    private String description;
    private BigDecimal price;
    private List<TagDTO> tags;

    public CourseResponseDTO(Long id, String title, String certification, Modality modality,
                             String duration, String description, BigDecimal price, List<TagDTO> tags) {
        this.id = id;
        this.title = title;
        this.certification = certification;
        this.modality = modality;
        this.duration = duration;
        this.description = description;
        this.price = price;
        this.tags = tags;
    }
}
