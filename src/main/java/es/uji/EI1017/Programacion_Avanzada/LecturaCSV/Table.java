package es.uji.EI1017.Programacion_Avanzada.LecturaCSV;

import es.uji.EI1017.Programacion_Avanzada.LecturaCSV.Row;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private List<String> headers;
    private List<Row> rows;

    // Constructor con argumentos
    public Table(List<String> headers, List<Row> rows) {
        this.headers = headers;
        this.rows = rows;
    }

    // Constructor sin argumentos
    public Table() {
        this.headers = new ArrayList<>();
        this.rows = new ArrayList<>();
    }

    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    public Row getRowAt(int index) {
        return rows.get(index);
    }

    public int getRowCount() {
        return rows.size();
    }

    public List<Double> getColumnAt(int index) {
        return rows.stream().map(row -> row.getData().get(index)).toList();
    }

    // Método para añadir una fila
    public void addRow(Row row) {
        rows.add(row);
    }
}