package es.uji.martinez.Programacion_Avanzada.Practica1;

import java.util.List;

public class RowWithLabel extends Row {
    private String label;

    public RowWithLabel(List<Double> data, String label) {
        super(data);
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
