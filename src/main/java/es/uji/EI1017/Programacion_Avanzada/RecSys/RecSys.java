package es.uji.EI1017.Programacion_Avanzada.RecSys;

import es.uji.EI1017.Programacion_Avanzada.Algoritmos.Algorithm;
import es.uji.EI1017.Programacion_Avanzada.LecturaCSV.Table;
import es.uji.EI1017.Programacion_Avanzada.Excepciones.LikedItemNotFoundException;

import java.util.*;

public class RecSys {
    private Algorithm<Table, Integer> algorithm;
    private List<String> testItemNames;
    private Map<String, Integer> estimatedClasses;

    public RecSys(Algorithm<Table, Integer> algorithm) {
        this.algorithm = algorithm;
        this.testItemNames = new ArrayList<>();
        this.estimatedClasses = new HashMap<>();
    }

    public void train(Table trainData) {
        algorithm.train(trainData);
    }

    public void initialise(Table testData, List<String> testItemNames) {
        this.testItemNames = testItemNames;
        estimatedClasses.clear();

        for (int i = 0; i < testData.getRowCount(); i++) {
            Integer classLabel = algorithm.estimate(testData.getRowAt(i).getData());
            estimatedClasses.put(testItemNames.get(i), classLabel);
        }
    }

    public List<String> recommend(String nameLikedItem, int numRecommendations) throws LikedItemNotFoundException {
        if (!estimatedClasses.containsKey(nameLikedItem)) {
            throw new LikedItemNotFoundException(nameLikedItem);
        }

        int targetClass = estimatedClasses.get(nameLikedItem);
        List<String> recommendations = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : estimatedClasses.entrySet()) {
            if (!entry.getKey().equals(nameLikedItem) && entry.getValue() == targetClass) {
                recommendations.add(entry.getKey());
            }
        }

        return recommendations.subList(0, Math.min(numRecommendations, recommendations.size()));
    }
}
