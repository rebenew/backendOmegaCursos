package com.cursos.backend.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagDTO {
    private Long id;
    private String name;

    public TagDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
