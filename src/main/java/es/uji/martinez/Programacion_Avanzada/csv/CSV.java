package es.uji.martinez.Programacion_Avanzada.csv;
import es.uji.martinez.Programacion_Avanzada.TABLE.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSV {

    public Table readTable(String filename) throws IOException {
        // Intentar obtener el archivo como recurso
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename);
        if (inputStream == null) {
            throw new FileNotFoundException("No se pudo encontrar el archivo: " + filename);
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        String line = br.readLine();

        if (line == null || line.isEmpty()) {
            throw new IOException("El archivo está vacío o no tiene encabezados.");
        }

        // Leer encabezados de la primera línea
        List<String> headers = List.of(line.split(","));
        List<Row> rows = new ArrayList<>();

        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            if (values.length != headers.size()) {
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

        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        String line = br.readLine();

        if (line == null || line.isEmpty()) {
            throw new IOException("El archivo está vacío o no tiene encabezados.");
        }

        // Leer encabezados, ignorando el último elemento
        String[] rawHeaders = line.split(",");
        List<String> headers = List.of(rawHeaders).subList(0, rawHeaders.length - 1);
        List<RowWithLabel> rows = new ArrayList<>();

        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            if (values.length < rawHeaders.length) {
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

            rows.add(new RowWithLabel(rowData, values[values.length - 1]));
        }

        br.close();
        return new TableWithLabels(headers, rows);
    }
}