package es.uji.EI1017.Programacion_Avanzada.Algoritmos.KMeans;

import es.uji.EI1017.Programacion_Avanzada.Algoritmos.Algorithm;
import es.uji.EI1017.Programacion_Avanzada.Algoritmos.Distance;
import es.uji.EI1017.Programacion_Avanzada.Excepciones.InvalidClusterNumberException;
import es.uji.EI1017.Programacion_Avanzada.LecturaCSV.Table;
import es.uji.EI1017.Programacion_Avanzada.Algoritmos.EuclideanDistance; // Asegúrate de importar la clase adecuada

import java.util.*;

public class KMeans implements Algorithm<Table, Integer> {
    private int numClusters;
    private int numIterations;
    private long seed;
    private List<List<Double>> centroids;
    private Distance distance;

    // Constructor principal que recibe todos los parámetros.
    public KMeans(int numClusters, int numIterations, long seed, Distance distance) throws InvalidClusterNumberException {
        if (numClusters <= 0) {
            throw new InvalidClusterNumberException(numClusters, 0);
        }
        if (distance == null) {
            throw new IllegalArgumentException("Distance cannot be null");
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
    public void train(Table datos) throws InvalidClusterNumberException {
        if (numClusters > datos.getRowCount()) {
            throw new InvalidClusterNumberException(numClusters, datos.getRowCount());
        }
        Random random = new Random(seed);
        Set<Integer> selectedIndexes = new HashSet<>();
        while (selectedIndexes.size() < numClusters) {
            selectedIndexes.add(random.nextInt(datos.getRowCount()));
        }

        centroids.clear();
        for (int index : selectedIndexes) {
            centroids.add(new ArrayList<>(datos.getRowAt(index).getData()));
        }

        for (int i = 0; i < numIterations; i++) {
            List<List<Double>> newCentroids = new ArrayList<>();
            // Inicializamos cada clúster con una lista de ceros de la dimensión correspondiente
            for (int j = 0; j < numClusters; j++) {
                newCentroids.add(new ArrayList<>(Collections.nCopies(centroids.get(0).size(), 0.0)));
            }

            int[] clusterSizes = new int[numClusters];

            for (int r = 0; r < datos.getRowCount(); r++) {
                List<Double> row = datos.getRowAt(r).getData();
                int clusterIndex = estimate(row);

                for (int d = 0; d < row.size(); d++) {
                    newCentroids.get(clusterIndex).set(d, newCentroids.get(clusterIndex).get(d) + row.get(d));
                }
                clusterSizes[clusterIndex]++;
            }

            for (int j = 0; j < numClusters; j++) {
                if (clusterSizes[j] > 0) {
                    for (int d = 0; d < newCentroids.get(j).size(); d++) {
                        newCentroids.get(j).set(d, newCentroids.get(j).get(d) / clusterSizes[j]);
                    }
                }
            }

            centroids = newCentroids;
        }
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
}
