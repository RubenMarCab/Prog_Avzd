package es.uji.martinez.Programacion_Avanzada.Prac1.RecSys;

import es.uji.martinez.Programacion_Avanzada.Prac1.Algoritmos.Algorithm;

import java.util.List;
import java.util.ArrayList;

public class RecSys {
    private Algorithm<List<List<Double>>, Integer> algorithm;
    private List<String> testItemNames;
    private List<Integer> estimatedClusters;

    public RecSys(Algorithm<List<List<Double>>, Integer> algorithm) {
        this.algorithm = algorithm;
        this.testItemNames = new ArrayList<>();
        this.estimatedClusters = new ArrayList<>();
    }

    public void train(List<List<Double>> trainData) {
        algorithm.train(trainData);
    }

    public void initialise(List<List<Double>> testData, List<String> testItemNames) {
        this.testItemNames = testItemNames;
        this.estimatedClusters.clear();
        for (List<Double> data : testData) {
            estimatedClusters.add(algorithm.estimate(data));
        }
    }

    public List<String> recommend(String nameLikedItem, int numRecommendations) {
        return new ArrayList<>(); // Implementar lógica de recomendación
    }
}

