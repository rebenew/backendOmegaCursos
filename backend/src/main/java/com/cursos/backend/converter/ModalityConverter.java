package com.cursos.backend.converter;

import com.cursos.backend.model.Modality;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ModalityConverter implements AttributeConverter<Modality, String> {

    @Override
    public String convertToDatabaseColumn(Modality modality) {
        return modality != null ? modality.getLabel() : null;
    }

    @Override
    public Modality convertToEntityAttribute(String dbData) {
        return dbData != null ? Modality.fromLabel(dbData) : null;
    }
}
