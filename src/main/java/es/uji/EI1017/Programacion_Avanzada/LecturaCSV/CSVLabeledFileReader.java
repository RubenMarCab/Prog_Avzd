package es.uji.EI1017.Programacion_Avanzada.LecturaCSV;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVLabeledFileReader extends ReaderTemplate<TableWithLabels> {

    private BufferedReader reader;
    private String currentLine;

    @Override
    protected void processHeaders(String headers) {
        if (tableBeingBuilt == null) {
            throw new IllegalStateException("Table is not initialized. Ensure createTable() is called before processHeaders.");
        }
        String[] headerColumns = headers.split(",");
        for (int i = 0; i < headerColumns.length - 1; i++) {
            tableBeingBuilt.addColumn(headerColumns[i].trim());
        }
    }


    @Override
    protected void processData(String data, TableWithLabels table) {
        String[] tokens = data.split(",");
        String label = tokens[tokens.length - 1].trim();
        List<Double> rowData = new ArrayList<>();
        for (int i = 0; i < tokens.length - 1; i++) {
            rowData.add(Double.parseDouble(tokens[i].trim()));
        }
        RowWithLabel row = new RowWithLabel(rowData, label);
        table.addRow(row);
    }

    @Override
    protected TableWithLabels createTable() {
        return new TableWithLabels();
    }

    @Override
    protected void openSource(String source) {
        try {
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            java.net.URL url = cl.getResource(source);

            if (url == null) {
                throw new IOException("Resource not found in classpath: " + source);
            }

            reader = new BufferedReader(new java.io.InputStreamReader(url.openStream()));
            currentLine = reader.readLine();

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
            currentLine = reader.readLine();
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
            currentLine = reader.readLine();
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
