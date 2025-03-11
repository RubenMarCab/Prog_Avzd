package es.uji.EI1017.Programacion_Avanzada.Algoritmos;

import java.util.List;

public interface Algorithm<T, U> {
    void train(T data);
    U estimate(List<Double> point);
}
