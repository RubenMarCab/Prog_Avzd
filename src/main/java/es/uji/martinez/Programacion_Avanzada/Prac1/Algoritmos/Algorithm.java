package es.uji.martinez.Programacion_Avanzada.Prac1.Algoritmos;

import java.util.List;

public interface Algorithm<T, U> {
    void train(T data);
    U estimate(List<Double> point);
}
