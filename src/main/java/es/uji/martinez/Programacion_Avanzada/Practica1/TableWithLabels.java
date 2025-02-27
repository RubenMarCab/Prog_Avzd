package es.uji.martinez.Programacion_Avanzada.Practica1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableWithLabels extends Table {
    private Map<String, Integer> labelsToInt = new HashMap<>();

    public TableWithLabels(List<String> headers, List<RowWithLabel> rows) {
        super(headers, List.copyOf(rows));
        int labelIndex = 0;
        for (RowWithLabel row : rows) {
            labelsToInt.putIfAbsent(row.getLabel(), labelIndex++);
        }
    }

    public TableWithLabels() {
        super();
    }

    @Override
    public RowWithLabel getRowAt(int index) {
        return (RowWithLabel) super.getRowAt(index);
    }

    public Integer getLabelAsInteger(String label) {
        return labelsToInt.get(label);
    }
}
