package es.uji.EI1017.Programacion_Avanzada.LecturaCSV;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class CSVUnlabeledFileReader extends FileReader<Table> {

    @Override
    protected void openSource(String source) {
        // Carga el CSV como recurso de clase, igual que en CSVLabeledFileReader
        InputStream is = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(source);
        if (is == null) {
            throw new RuntimeException("Archivo no encontrado en classpath: " + source);
        }
        // Usa el InputStream para el Scanner
        scanner = new Scanner(new BufferedReader(
                new InputStreamReader(is, StandardCharsets.UTF_8)
        ));
    }

    @Override
    protected void processHeaders(String headers) {
        String[] headerColumns = headers.split(",");
        for (String column : headerColumns) {
            tableBeingBuilt.addColumn(column.trim());
        }
    }

    @Override
    protected void processData(String data, Table table) {
        String[] tokens = data.split(",");
        List<Double> rowData = new ArrayList<>();
        for (String token : tokens) {
            rowData.add(Double.parseDouble(token.trim()));
        }
        table.addRow(new Row(rowData));
    }

    @Override
    protected Table createTable() {
        return new Table();
    }

    public List<String> readAllLines(String resourcePath) {
        List<String> result = new ArrayList<>();
        try (InputStream is = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(resourcePath);
             BufferedReader br = new BufferedReader(
                     new InputStreamReader(is, StandardCharsets.UTF_8)))
        {
            String line;
            while ((line = br.readLine()) != null) {
                // en caso de múltiples columnas, coge la primera
                String[] parts = line.split(",");
                result.add(parts[0].trim());
            }
        } catch (IOException | NullPointerException e) {
            throw new RuntimeException("Error leyendo líneas de " + resourcePath, e);
        }
        return result;
    }
}
