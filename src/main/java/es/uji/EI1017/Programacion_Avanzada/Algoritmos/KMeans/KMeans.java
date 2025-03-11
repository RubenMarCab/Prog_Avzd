package es.uji.EI1017.Programacion_Avanzada.Algoritmos.KMeans;

import java.util.*;

public class KMeans {
    private int numClusters;
    private int numIterations;
    private Random random;
    private List<List<Double>> centroids;

    public KMeans(int numClusters, int numIterations, long seed) {
        this.numClusters = numClusters;
        this.numIterations = numIterations;
        this.random = new Random(seed);
        this.centroids = new ArrayList<>();
    }

    public void train(List<List<Double>> data) {
        initializeCentroids(data);

        for (int i = 0; i < numIterations; i++) {
            Map<Integer, List<List<Double>>> clusters = assignClusters(data);
            updateCentroids(clusters);
        }
    }

    public int estimate(List<Double> point) {
        return findClosestCentroid(point);
    }

    private void initializeCentroids(List<List<Double>> data) {
        Collections.shuffle(data, random);
        centroids.clear();
        for (int i = 0; i < numClusters; i++) {
            centroids.add(new ArrayList<>(data.get(i)));
        }
    }

    private Map<Integer, List<List<Double>>> assignClusters(List<List<Double>> data) {
        Map<Integer, List<List<Double>>> clusters = new HashMap<>();
        for (int i = 0; i < numClusters; i++) {
            clusters.put(i, new ArrayList<>());
        }
        for (List<Double> point : data) {
            int clusterIndex = findClosestCentroid(point);
            clusters.get(clusterIndex).add(point);
        }
        return clusters;
    }

    private int findClosestCentroid(List<Double> point) {
        int closestIndex = 0;
        double minDistance = Double.MAX_VALUE;
        for (int i = 0; i < centroids.size(); i++) {
            double distance = euclideanDistance(point, centroids.get(i));
            if (distance < minDistance) {
                minDistance = distance;
                closestIndex = i;
            }
        }
        return closestIndex;
    }

    private void updateCentroids(Map<Integer, List<List<Double>>> clusters) {
        for (int i = 0; i < numClusters; i++) {
            if (!clusters.get(i).isEmpty()) {
                centroids.set(i, calculateCentroid(clusters.get(i)));
            }
        }
    }

    private List<Double> calculateCentroid(List<List<Double>> cluster) {
        int dimensions = cluster.get(0).size();
        List<Double> centroid = new ArrayList<>(Collections.nCopies(dimensions, 0.0));
        for (List<Double> point : cluster) {
            for (int j = 0; j < dimensions; j++) {
                centroid.set(j, centroid.get(j) + point.get(j));
            }
        }
        for (int j = 0; j < dimensions; j++) {
            centroid.set(j, centroid.get(j) / cluster.size());
        }
        return centroid;
    }

    private double euclideanDistance(List<Double> p1, List<Double> p2) {
        double sum = 0.0;
        for (int i = 0; i < p1.size(); i++) {
            sum += Math.pow(p1.get(i) - p2.get(i), 2);
        }
        return Math.sqrt(sum);
    }
}
