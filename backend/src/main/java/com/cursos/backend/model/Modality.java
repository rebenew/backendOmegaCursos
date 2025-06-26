package com.cursos.backend.model;

public enum Modality {
    PRESENCIAL("Presencial"),
    VIRTUAL("Virtual");

    private final String label;

    Modality(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static Modality fromLabel(String label) {
        for (Modality m : values()) {
            if (m.label.equalsIgnoreCase(label)) {
                return m;
            }
        }
        throw new IllegalArgumentException("Modalidad no válida: " + label);
    }
}
