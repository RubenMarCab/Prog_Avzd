package es.uji.EI1017.Programacion_Avanzada.Algoritmos;

import es.uji.EI1017.Programacion_Avanzada.Excepciones.InvalidClusterNumberException;
import es.uji.EI1017.Programacion_Avanzada.LecturaCSV.TableWithLabels;

import java.util.List;

public interface Algorithm<T, R> {

    void train(TableWithLabels data) throws InvalidClusterNumberException;

    void train(T data) throws InvalidClusterNumberException;

    R estimate(List<Double> dato);
}
