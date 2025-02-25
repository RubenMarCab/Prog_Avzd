package es.uji.martinez.Programacion_Avanzada.Practica1;

import java.util.*;

public class Row {
    protected List<Double> data;

    public Row(List<Double> data) {
        this.data = data;
    }

    public List<Double> getData() {
        return data;
    }
}
