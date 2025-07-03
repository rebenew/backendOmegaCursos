package com.cursos.backend.DTO;

import com.cursos.backend.converter.ModalityConverter;
import com.cursos.backend.model.Modality;
import jakarta.persistence.Convert;

import java.math.BigDecimal;
import java.util.List;


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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Modality getModality() {
        return modality;
    }

    public void setModality(Modality modality) {
        this.modality = modality;
    }

    public String getCertification() {
        return certification;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<TagDTO> getTags() {
        return tags;
    }

    public void setTags(List<TagDTO> tags) {
        this.tags = tags;
    }
}
