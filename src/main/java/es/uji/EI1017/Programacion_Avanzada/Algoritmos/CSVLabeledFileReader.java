package es.uji.EI1017.Programacion_Avanzada.Algoritmos;

// CSVLabeledFileReader.java
import es.uji.EI1017.Programacion_Avanzada.LecturaCSV.TableWithLabels;

import java.util.Arrays;
import java.util.List;

public class CSVLabeledFileReader extends FileReader<TableWithLabels> {

    @Override
    protected void processHeaders(String headers) {
        // Puedes procesar o almacenar la cabecera según tus necesidades.
        System.out.println("Procesando cabecera (con etiquetas): " + headers);
    }

    @Override
    protected void processData(String data, TableWithLabels table) {
        // Se asume que cada línea tiene el formato: etiqueta, valor1, valor2, ..., valorN
        String[] tokens = data.split(",");
        if (tokens.length < 2) {
            throw new IllegalArgumentException("La línea debe contener al menos una etiqueta y un valor.");
        }
        // El primer token es la etiqueta
        String label = tokens[0].trim();
        // Los tokens restantes forman la fila de datos
        List<String> rowData = Arrays.asList(Arrays.copyOfRange(tokens, 1, tokens.length));

        // Agregar la etiqueta y la fila a la tabla
        table.addLabel(label);
        table.addRow(rowData);
    }

    @Override
    protected TableWithLabels createTable() {
        return new TableWithLabels();
    }
}


