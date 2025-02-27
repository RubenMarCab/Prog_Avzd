package es.uji.martinez.Programacion_Avanzada.Practica1;

import java.util.List;

public class KNN {
    private TableWithLabels trainingData;

    public void train(TableWithLabels table) {
        this.trainingData = table;
    }

    public Integer estimate(List<Double> sample) {
        double minDistance = Double.MAX_VALUE;
        Integer predictedLabel = null;

        for (int i = 0; i < trainingData.getRowCount(); i++) {
            RowWithLabel row = trainingData.getRowAt(i);
            double distance = euclideanDistance(sample, row.getData());

            if (distance < minDistance) {
                minDistance = distance;
                predictedLabel = trainingData.getLabelAsInteger(row.getLabel());
            }
        }
        return predictedLabel;
    }

    private double euclideanDistance(List<Double> p1, List<Double> p2) {
        double sum = 0.0;
        for (int i = 0; i < p1.size(); i++) {
            sum += Math.pow(p1.get(i) - p2.get(i), 2);
        }
        return Math.sqrt(sum);
    }
}
