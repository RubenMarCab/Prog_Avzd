package es.uji.EI1017.Programacion_Avanzada.Algoritmos;

import es.uji.EI1017.Programacion_Avanzada.LecturaCSV.Table;
import java.util.Arrays;
import java.util.List;

public class CSVUnlabeledFileReader extends FileReader<Table> {

    @Override
    protected void processHeaders(String headers) {
        // Procesar la cabecera (si es necesario)
        System.out.println("Procesando cabecera (sin etiquetas): " + headers);
    }

    @Override
    protected void processData(String data, Table table) {
        // Se asume que cada línea es: value1, value2, ..., valueN
        String[] tokens = data.split(",");
        // Se pueden recortar espacios en blanco, si es necesario
        List<String> rowData = Arrays.asList(tokens);
        table.addRow(rowData);
    }

    @Override
    protected Table createTable() {
        return new Table();
    }
}

