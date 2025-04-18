package es.uji.EI1017.Programacion_Avanzada.LecturaCSV;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableWithLabels extends Table {
    protected Map<String, Integer> labelsMap;
    private List<String> labels;

    // Constructor predeterminado
    public TableWithLabels() {
        super();
        this.labelsMap = new HashMap<>();
        labels = new ArrayList<>();
    }

    // Constructor con parámetros (headers y filas)
    public TableWithLabels(List<String> headers, List<RowWithLabel> rows) {
        super(headers, (List<Row>) (List<?>) rows); // Cast explícito
        this.labelsMap = new HashMap<>();
    }

    public Integer getLabelAsInteger(String label) {
        return labelsMap.computeIfAbsent(label, k -> labelsMap.size());
    }

    public void addRow(RowWithLabel row) {
        super.addRow(row);
        String label = row.getLabel();
        getLabelAsInteger(label);
    }

    public void addLabel(String label) {
        labels.add(label);
    }

    public List<String> getLabels() {
        return labels;
    }

    @Override
    public RowWithLabel getRowAt(int index) {
        return (RowWithLabel) super.getRowAt(index);
    }

    public void addColumn(String columnName) {
        getHeaders().add(columnName);
    }
}