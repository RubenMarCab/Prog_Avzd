package es.uji.martinez.Programacion_Avanzada.Practica1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Table {
    String rutaFichero;
    List<String[]> rows;
    String[][] tabla;

    public static void main(String[] args) {
        String fichero = "iris.csv";
        Table prueba = new Table(fichero);
        prueba.showTabla();
    }
    public Table(String name){
    
        rutaFichero = getClass().getClassLoader().getResource(name).getFile();
        String separator = ",";

        rows = new ArrayList<>();

        // Leer el archivo CSV línea a línea
        try {
            BufferedReader br = new BufferedReader(new FileReader(rutaFichero));
            String line;
            while ((line = br.readLine()) != null) {
                // Separa la línea según el delimitador
                String[] values = line.split(separator);
                rows.add(values);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Convertir la lista a una matriz bidimensional
        tabla = new String[rows.size()][];
        for (int i = 0; i < rows.size(); i++) {
            tabla[i] = rows.get(i);
        }
    }

    private void showTabla(){
        for (String[] row : tabla) {
            System.out.println(Arrays.toString(row));
        }
    }



}
