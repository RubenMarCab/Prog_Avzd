package es.uji.martinez.Programacion_Avanzada.Practica1;
import java.io.*;
import java.util.*;

public class CSV {
    public static Table readTable(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        
        List<String> headers = Arrays.asList(br.readLine().split(","));
        Table table = new Table(headers);
        
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

    public static TableWithLabels readTableWithLabels(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        
        List<String> headers = Arrays.asList(br.readLine().split(","));
        TableWithLabels table = new TableWithLabels(headers);
        
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            List<Double> data = new ArrayList<>();
            for (int i = 0; i < values.length - 1; i++) {
                data.add(Double.parseDouble(values[i]));
            }
            String label = values[values.length - 1];
            table.addRow(new RowWithLabel(data, label));
        }
        br.close();
        return table;
    }
}   

