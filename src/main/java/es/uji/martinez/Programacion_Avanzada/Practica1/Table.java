package es.uji.martinez.Programacion_Avanzada.Practica1;

import java.util.List;

public class Table {
    private List<String> headers;
    private List<Row> rows;

    public Table(List<String> headers, List<Row> rows) {
        this.headers = headers;
        this.rows = rows;
    }

    public Table() {
    }

    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    public void addRow(Row row) {
        rows.add(row);
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
}
