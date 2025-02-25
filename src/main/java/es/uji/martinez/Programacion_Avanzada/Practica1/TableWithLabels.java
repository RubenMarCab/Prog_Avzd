package es.uji.martinez.Programacion_Avanzada.Practica1;

import java.util.*;

public class TableWithLabels extends Table {
    private Map<String, Integer> labelToIndex;

    public TableWithLabels(List<String> headers) {
        super(headers);
        this.labelToIndex = new HashMap<>();
    }

    @Override
    public void addRow(Row row) {
        if (row instanceof RowWithLabel) {
            RowWithLabel labeledRow = (RowWithLabel) row;
            labelToIndex.putIfAbsent(labeledRow.getLabel(), labelToIndex.size());
        }
        super.addRow(row);
    }

    public int getLabelAsInteger(String label) {
        return labelToIndex.getOrDefault(label, -1);
    }
}
