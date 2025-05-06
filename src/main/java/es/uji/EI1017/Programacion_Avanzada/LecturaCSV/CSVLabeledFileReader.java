package es.uji.EI1017.Programacion_Avanzada.LecturaCSV;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CSVLabeledFileReader extends FileReader<TableWithLabels> {
    @Override
    protected void openSource(String source) {
        // Intenta cargar el CSV como recurso de clase:
        InputStream is = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(source);
        if (is == null) {
            throw new RuntimeException("Archivo no encontrado en classpath: " + source);
        }
        scanner = new Scanner(is);
    }

    @Override
    protected void processHeaders(String headers) {
        String[] headerColumns = headers.split(",");
        for (int i = 0; i < headerColumns.length - 1; i++) {
            tableBeingBuilt.addColumn(headerColumns[i].trim());
        }
    }

    @Override
    protected void processData(String data, TableWithLabels table) {
        String[] tokens = data.split(",");
        List<Double> rowData = new ArrayList<>();
        for (int i = 0; i < tokens.length - 1; i++) {
            rowData.add(Double.parseDouble(tokens[i].trim()));
        }
        String label = tokens[tokens.length - 1].trim();
        table.addRow(new RowWithLabel(rowData, label));
    }

    @Override
    protected TableWithLabels createTable() {
        return new TableWithLabels();
    }
}
