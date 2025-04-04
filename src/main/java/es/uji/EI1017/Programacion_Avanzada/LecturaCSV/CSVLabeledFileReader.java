package es.uji.EI1017.Programacion_Avanzada.LecturaCSV;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVLabeledFileReader extends FileReader<TableWithLabels> {

    @Override
    @Override
    protected void processHeaders(String headers) {
        String[] columns = headers.split(",");
        // Guarda las columnas que no son la etiqueta
        for (int i = 0; i < columns.length - 1; i++) {
            ((TableWithLabels) tableBeingBuilt).addColumn(columns[i].trim());
        }
    }


    @Override
    protected void processData(String data, TableWithLabels table) {
        String[] tokens = data.split(",");

// Obtener la etiqueta (última columna)
        String label = tokens[tokens.length - 1];

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
        return new TableWithLabels();
    }
}


