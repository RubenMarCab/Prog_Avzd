package es.uji.martinez.Programacion_Avanzada.Practica1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableWithLabels extends Table {
    private Map<String, Integer> labelsMap;

    // Constructor predeterminado
    public TableWithLabels() {
        super();
        this.labelsMap = new HashMap<>();
        initializeLabelsMap();
    }

    // Constructor con parámetros (headers y filas)
    public TableWithLabels(List<String> headers, List<RowWithLabel> rows) {
        super(headers, (List<Row>) (List<?>) rows); // Cast explícito
        this.labelsMap = new HashMap<>();
        initializeLabelsMap();
    }

    public Integer getLabelAsInteger(String label) {
        return labelsMap.getOrDefault(label, -1); // Devolver -1 si no se encuentra la etiqueta
    }

    private void initializeLabelsMap() {
        labelsMap.put("Iris-setosa", 0);
        labelsMap.put("Iris-versicolor", 1);
        labelsMap.put("Iris-virginica", 2);
    }

    // Sobrescribir addRow para trabajar con RowWithLabel
    public void addRow(RowWithLabel row) {
        super.addRow(row); // Usa el método de la clase base para añadir la fila
    }

    // Sobrescribir getRowAt para devolver RowWithLabel
    @Override
    public RowWithLabel getRowAt(int index) {
        return (RowWithLabel) super.getRowAt(index);
    }
}