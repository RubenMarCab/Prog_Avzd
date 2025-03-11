package es.uji.martinez.Programacion_Avanzada.TABLE;

import es.uji.martinez.Programacion_Avanzada.TABLE.Row;
import es.uji.martinez.Programacion_Avanzada.TABLE.RowWithLabel;
import es.uji.martinez.Programacion_Avanzada.TABLE.Table;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableWithLabels extends Table {
    protected Map<String, Integer> labelsMap;

    // Constructor predeterminado
    public TableWithLabels() {
        super();
        this.labelsMap = new HashMap<>();
    }

    // Constructor con parámetros (headers y filas)
    public TableWithLabels(List<String> headers, List<RowWithLabel> rows) {
        super(headers, (List<Row>) (List<?>) rows); // Cast explícito
        this.labelsMap = new HashMap<>();
    }

    public Integer getLabelAsInteger(String label) {
        return labelsMap.computeIfAbsent(label, k -> labelsMap.size());
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