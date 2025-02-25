package es.uji.martinez.Programacion_Avanzada.Practica1;
import java.io.*;
import java.util.*;

public class CSV {
    public static Table readTable(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        
        // Leer encabezados
        List<String> headers = Arrays.asList(br.readLine().split(","));
        Table table = new Table(headers);
        
        // Leer filas de datos
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            List<Double> data = new ArrayList<>();
            for (String value : values) {
                data.add(Double.parseDouble(value));
            }
            table.addRow(new Row(data));
        }
        br.close();
        return table;
    }
}
