package es.uji.EI1017.Programacion_Avanzada.LecturaCSV;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSV {

    public Table readTable(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(getFileStream(filename), "UTF-8"))) {
            String line = br.readLine();
            if (line == null || line.isEmpty()) {
                throw new IOException("El archivo está vacío o no tiene encabezados.");
            }

            List<String> headers = List.of(line.split(","));
            List<Row> rows = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                rows.add(parseRow(line, headers.size()));
            }
            return new Table(headers, rows);
        }
    }

    public TableWithLabels readTableWithLabels(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(getFileStream(filename), "UTF-8"))) {
            String line = br.readLine();
            if (line == null || line.isEmpty()) {
                throw new IOException("El archivo está vacío o no tiene encabezados.");
            }

            String[] rawHeaders = line.split(",");
            List<String> headers = List.of(rawHeaders).subList(0, rawHeaders.length - 1);
            List<RowWithLabel> rows = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                rows.add(parseRowWithLabel(line, rawHeaders.length));
            }
            return new TableWithLabels(headers, rows);
        }
    }

    private InputStream getFileStream(String filename) throws FileNotFoundException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename);
        if (inputStream == null) {
            throw new FileNotFoundException("No se pudo encontrar el archivo: " + filename);
        }
        return inputStream;
    }

    private Row parseRow(String line, int expectedColumns) throws IOException {
        String[] values = line.split(",");
        if (values.length != expectedColumns) {
            throw new IOException("Número incorrecto de columnas en la línea: " + line);
        }

        List<Double> rowData = new ArrayList<>();
        for (String value : values) {
            try {
                rowData.add(Double.parseDouble(value));
            } catch (NumberFormatException e) {
                throw new IOException("Error al convertir el valor '" + value + "' a Double en la línea: " + line, e);
            }
        }
        return new Row(rowData);
    }

    private RowWithLabel parseRowWithLabel(String line, int expectedColumns) throws IOException {
        String[] values = line.split(",");
        if (values.length < expectedColumns) {
            throw new IOException("Número incorrecto de columnas o datos faltantes en la línea: " + line);
        }

        List<Double> rowData = new ArrayList<>();
        for (int i = 0; i < values.length - 1; i++) {
            try {
                rowData.add(Double.parseDouble(values[i]));
            } catch (NumberFormatException e) {
                throw new IOException("Error al convertir el valor '" + values[i] + "' a Double en la línea: " + line, e);
            }
        }
        return new RowWithLabel(rowData, values[values.length - 1]);
    }
}