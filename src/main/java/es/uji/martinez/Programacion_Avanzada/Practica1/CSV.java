package es.uji.martinez.Programacion_Avanzada.Practica1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSV {

    public Table readTable(String filename) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename);
        if (inputStream == null) {
            throw new FileNotFoundException("No se pudo encontrar el archivo: " + filename);
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        List<String> headers = List.of(br.readLine().split(","));
        List<Row> rows = new ArrayList<>();

        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            List<Double> rowData = new ArrayList<>();
            for (String value : values) {
                rowData.add(Double.parseDouble(value));
            }
            rows.add(new Row(rowData));
        }
        br.close();
        return new Table(headers, rows);
    }

    public TableWithLabels readTableWithLabels(String filename) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename);
        if (inputStream == null) {
            throw new FileNotFoundException("No se pudo encontrar el archivo: " + filename);
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        List<String> headers = List.of(br.readLine().split(","));
        List<RowWithLabel> rows = new ArrayList<>();

        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            List<Double> rowData = new ArrayList<>();
            for (int i = 0; i < values.length - 1; i++) {
                rowData.add(Double.parseDouble(values[i]));
            }
            rows.add(new RowWithLabel(rowData, values[values.length - 1]));
        }
        br.close();
        return new TableWithLabels(headers, rows);
    }
}
