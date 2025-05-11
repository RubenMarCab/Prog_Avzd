package es.uji.EI1017.Programacion_Avanzada.Algoritmos.KMeans;

import es.uji.EI1017.Programacion_Avanzada.Algoritmos.Algorithm;
import es.uji.EI1017.Programacion_Avanzada.Algoritmos.Distance;
import es.uji.EI1017.Programacion_Avanzada.Excepciones.InvalidClusterNumberException;
import es.uji.EI1017.Programacion_Avanzada.LecturaCSV.Table;
import es.uji.EI1017.Programacion_Avanzada.Algoritmos.EuclideanDistance;
import es.uji.EI1017.Programacion_Avanzada.LecturaCSV.TableWithLabels;

import java.util.*;

public class KMeans implements Algorithm<Table, Integer> {
    private int numClusters;
    private int numIterations;
    private long seed;
    private List<List<Double>> centroids;
    private Distance distance;
    private Map<Integer, List<List<Double>>> clusters = new HashMap<>();
    private List<List<Double>> previousCentroids = new ArrayList<>();
    private double tolerance = 1e-4;


    // Constructor principal que recibe todos los parámetros.
    public KMeans(int numClusters, int numIterations, long seed, Distance distance) throws InvalidClusterNumberException {
        if (numClusters <= 0) {
            throw new InvalidClusterNumberException(numClusters, 0);
        }
        if (distance == null) {
            throw new IllegalArgumentException("Distancia no debe ser null");
        }
        this.numClusters = numClusters;
        this.numIterations = numIterations;
        this.seed = seed;
        this.distance = distance;
        this.centroids = new ArrayList<>();
    }

    // Constructor sobrecargado: usa una estrategia de distancia por defecto (EuclideanDistance)
    public KMeans(int numClusters, int numIterations, long seed) throws InvalidClusterNumberException {
        this(numClusters, numIterations, seed, new EuclideanDistance());
    }

    // Delegación del cálculo de la distancia
    public double calculateDistance(List<Double> p, List<Double> q) {
        return distance.calculateDistance(p, q);
    }


    @Override
    public void train(TableWithLabels data) throws InvalidClusterNumberException {
        validateClusterNumber(data);
        initializeCentroids(data);
        previousCentroids.clear();
        for (List<Double> c : centroids) {
            previousCentroids.add(new ArrayList<>(c));
        }
        for (int iter = 0; iter < numIterations; iter++) {
            assignClusters(data);
            updateCentroids(data);
            if (converged()) break;
        }
    }

    public void train(Table data) throws InvalidClusterNumberException {
        if (!(data instanceof TableWithLabels)) {
            throw new IllegalArgumentException(
                    "KMeans necesita un TableWithLabels, pero recibió: " + data.getClass().getSimpleName()
            );
        }
        train((TableWithLabels) data);
    }


    // MÉTODOS AUXILIARES
    private void validateClusterNumber(TableWithLabels data) throws InvalidClusterNumberException {
        if (numClusters > data.getRowCount()) {
            throw new InvalidClusterNumberException(numClusters, data.getRowCount());
        }
    }

    private void initializeCentroids(TableWithLabels data) {
        centroids = new ArrayList<>();
        Random rnd = new Random(seed);
        Set<Integer> chosen = new HashSet<>();
        while (centroids.size() < numClusters) {
            int idx = rnd.nextInt(data.getRowCount());
            if (chosen.add(idx)) {
                centroids.add(data.getRowAt(idx).getData());
            }
        }
    }

    private void assignClusters(TableWithLabels data) {
        clusters.clear();
        for (int i = 0; i < data.getRowCount(); i++) {
            List<Double> row = data.getRowAt(i).getData();
            int best = estimate(row);  // reutiliza tu método estimate()
            clusters.computeIfAbsent(best, k -> new ArrayList<>()).add(row);
        }
    }

    private void updateCentroids(TableWithLabels data) {
        for (int k = 0; k < numClusters; k++) {
            List<List<Double>> points = clusters.getOrDefault(k, List.of());
            if (!points.isEmpty()) {
                centroids.set(k, meanOf(points));
            }
        }
    }

    private boolean converged() {
        // compara antiguos vs. nuevos centroides según tu tolerancia
        return computeShift() < tolerance;
    }

    @Override
    public Integer estimate(List<Double> dato) {
        if (centroids == null || centroids.isEmpty()) {
            throw new IllegalStateException("El modelo debe ser entrenado antes de estimar.");
        }

        int bestCluster = 0;
        double minDistance = Double.MAX_VALUE;

        for (int i = 0; i < centroids.size(); i++) {
            double dist = this.distance.calculateDistance(dato, centroids.get(i));
            if (dist < minDistance) {
                minDistance = dist;
                bestCluster = i;
            }
        }

        return bestCluster;
    }

    // Calcula la media (vector) de un conjunto de puntos.
         private List<Double> meanOf(List<List<Double>> points) {
        if (points.isEmpty()) return List.of();
        int dim = points.get(0).size();
        List<Double> mean = new ArrayList<>(Collections.nCopies(dim, 0.0));
        for (List<Double> p : points) {
            for (int i = 0; i < dim; i++) {
                mean.set(i, mean.get(i) + p.get(i));
            }
        }
        for (int i = 0; i < dim; i++) {
            mean.set(i, mean.get(i) / points.size());
        }
        return mean;
    }

    // Suma el cambio de posición de cada centroide respecto a la iteración previa.
    private double computeShift() {
        double sum = 0.0;
        for (int i = 0; i < centroids.size(); i++) {
            sum += distance.calculateDistance(previousCentroids.get(i), centroids.get(i));
        }
        // actualizo para la siguiente iteración
        previousCentroids.clear();
        for (List<Double> c : centroids) {
            previousCentroids.add(new ArrayList<>(c));
        }
        return sum;
    }

}
