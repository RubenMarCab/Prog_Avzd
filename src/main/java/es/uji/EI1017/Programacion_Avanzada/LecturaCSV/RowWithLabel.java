package es.uji.EI1017.Programacion_Avanzada.LecturaCSV;

import es.uji.EI1017.Programacion_Avanzada.LecturaCSV.Row;

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