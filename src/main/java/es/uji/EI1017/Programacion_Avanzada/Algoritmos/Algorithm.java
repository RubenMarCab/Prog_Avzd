package es.uji.EI1017.Programacion_Avanzada.Algoritmos;

import es.uji.EI1017.Programacion_Avanzada.Excepciones.InvalidClusterNumberException;

import java.util.List;

public interface Algorithm<T, R> {
    void train(T data) throws InvalidClusterNumberException;
    R estimate(List<Double> dato);
}
