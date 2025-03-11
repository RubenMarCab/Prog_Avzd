package es.uji.EI1017.Programacion_Avanzada.Algoritmos;

import java.util.List;

public interface Algorithm<T, R> {
    void train(T data);
    R estimate(List<Double> dato);
}
