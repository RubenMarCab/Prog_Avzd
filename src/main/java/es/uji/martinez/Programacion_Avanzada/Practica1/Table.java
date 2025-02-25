package es.uji.martinez.Programacion_Avanzada.Practica1;

import java.util.ArrayList;
import java.util.List;

public class Table {
    
    private List<String> headers;
    private List<Row> rows;

    public Table(List<String> headers) {
        this.headers = headers;
        this.rows = new ArrayList<>();
    }

    public void addRow(Row row) {
        rows.add(row);
    }

    public Row getRowAt(int index) {
        return rows.get(index);
    }

    public List<Double> getColumnAt(int index) {
        List<Double> column = new ArrayList<>();
        for (Row row : rows) {
            column.add(row.getData().get(index));
        }
        return column;
    }

    public void printTable() {
        System.out.println(headers);
        for (Row row : rows) {
            System.out.println(row.getData());
        }
    }
}

