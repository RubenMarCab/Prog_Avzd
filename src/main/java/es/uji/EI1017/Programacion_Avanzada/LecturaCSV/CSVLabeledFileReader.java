package es.uji.EI1017.Programacion_Avanzada.LecturaCSV;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.FileReader;

public class CSVLabeledFileReader extends ReaderTemplate<TableWithLabels> {

    private BufferedReader reader;
    private String currentLine;
    // Se elimina el campo privado tableBeingBuilt para utilizar el protegido heredado de ReaderTemplate

    @Override
    protected void processHeaders(String headers) {
        if (tableBeingBuilt == null) {
            throw new IllegalStateException("Table is not initialized. Ensure createTable() is called before processHeaders.");
        }
        // Separamos los encabezados
        String[] headerColumns = headers.split(",");
        // Se añaden todas las columnas excepto la última (suponiendo que es la etiqueta/clase)
        for (int i = 0; i < headerColumns.length - 1; i++) {
            tableBeingBuilt.addColumn(headerColumns[i].trim());
        }
    }

    @Override
    protected void processData(String data, TableWithLabels table) {
        String[] tokens = data.split(",");

        // Obtener la etiqueta (última columna)
        String label = tokens[tokens.length - 1].trim();

        // Convertir las demás columnas a Double
        List<Double> rowData = new ArrayList<>();
        for (int i = 0; i < tokens.length - 1; i++) {
            rowData.add(Double.parseDouble(tokens[i].trim()));
        }

        // Crear y añadir el RowWithLabel
        RowWithLabel row = new RowWithLabel(rowData, label);
        table.addRow(row);
    }

    @Override
    protected TableWithLabels createTable() {
        // La plantilla (ReaderTemplate) asigna el resultado a su variable protegida tableBeingBuilt
        return new TableWithLabels();
    }

    @Override
    protected void openSource(String source) {
        try {
            reader = new BufferedReader(new FileReader(source));
            currentLine = reader.readLine(); // Leer la primera línea (encabezados)
        } catch (IOException e) {
            throw new RuntimeException("Error opening source: " + source, e);
        }
    }

    @Override
    protected String getHeaders() {
        if (currentLine == null) {
            throw new IllegalStateException("No headers found. Ensure the source is opened correctly.");
        }
        String headers = currentLine;
        try {
            currentLine = reader.readLine(); // Avanza a la siguiente línea
        } catch (IOException e) {
            throw new RuntimeException("Error reading headers.", e);
        }
        return headers;
    }

    @Override
    protected boolean hasMoreData() {
        return currentLine != null;
    }

    @Override
    protected String getNextData() {
        String data = currentLine;
        try {
            currentLine = reader.readLine(); // Avanza a la siguiente línea
        } catch (IOException e) {
            throw new RuntimeException("Error reading next data line.", e);
        }
        return data;
    }

    @Override
    protected void closeSource() {
        try {
            if (reader != null) {
                reader.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error closing source.", e);
        }
    }
}
