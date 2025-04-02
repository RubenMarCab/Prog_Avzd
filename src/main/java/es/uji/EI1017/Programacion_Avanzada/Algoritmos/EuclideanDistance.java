package es.uji.EI1017.Programacion_Avanzada.Algoritmos;

import java.util.List;

public class EuclideanDistance implements Distance {
    @Override
    public double calculateDistance(List<Double> p, List<Double> q) {
        if (p.size() != q.size()) {
            throw new IllegalArgumentException("Los vectores deben tener la misma dimensión");
        }
        double sum = 0.0;
        for (int i = 0; i < p.size(); i++) {
            sum += Math.pow(p.get(i) - q.get(i), 2);
        }
        return Math.sqrt(sum);
    }
}
