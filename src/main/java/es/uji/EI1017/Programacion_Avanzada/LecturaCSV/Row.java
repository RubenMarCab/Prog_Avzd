package es.uji.EI1017.Programacion_Avanzada.LecturaCSV;

import java.util.List;

public class Row {
    private List<Double> data;

    public Row(List<Double> data) {
        this.data = data;
    }

    public List<Double> getData() {
        return data;
    }
}