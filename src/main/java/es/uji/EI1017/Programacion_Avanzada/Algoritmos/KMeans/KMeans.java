package es.uji.EI1017.Programacion_Avanzada.Algoritmos.KMeans;

import es.uji.EI1017.Programacion_Avanzada.Algoritmos.Algorithm;
import es.uji.EI1017.Programacion_Avanzada.Algoritmos.Distance;
import es.uji.EI1017.Programacion_Avanzada.Excepciones.InvalidClusterNumberException;
import es.uji.EI1017.Programacion_Avanzada.LecturaCSV.Table;

import java.util.*;

public class KMeans implements Algorithm<Table, Integer> {
    private int numClusters;
    private int numIterations;
    private long seed;
    private List<List<Double>> centroids;
    private Distance distance;

    public KMeans(int numClusters, int numIterations, long seed) throws InvalidClusterNumberException {
        if (numClusters <= 0) {
            throw new InvalidClusterNumberException(numClusters, 0);
        }
        this.numClusters = numClusters;
        this.numIterations = numIterations;
        this.seed = seed;
        this.centroids = new ArrayList<>();
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
            List<List<Double>> newCentroids = new ArrayList<>(Collections.nCopies(numClusters, new ArrayList<>()));

            for (int j = 0; j < numClusters; j++) {
                newCentroids.set(j, new ArrayList<>(Collections.nCopies(centroids.get(0).size(), 0.0)));
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
        int bestCluster = 0;
        double minDistance = Double.MAX_VALUE;

        for (int i = 0; i < centroids.size(); i++) {
            double distance = 0;
            for (int d = 0; d < dato.size(); d++) {
                distance += Math.pow(dato.get(d) - centroids.get(i).get(d), 2);
            }
            distance = Math.sqrt(distance);

            if (distance < minDistance) {
                minDistance = distance;
                bestCluster = i;
            }
        }

        return bestCluster;
    }
}
