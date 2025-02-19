package es.uji.martinez.Programacion_Avanzada.Practica1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


//1º Definimos las variables que usemos en el contructor en la parte de arriba. DONE
//2º Crearemos el contructor que nos pasaran como argumento el nombre del fichero y crearemos la tabla usando una matriz. DONE
//3º Creamos el metodo row que nos daran como parametro el numero de la fila row>=1 (Empezando por 0). DONE
//4º Creamos un metodo que muestre una list<String> de la cabecera. DONE
//5º Creamos un metodo que devuelva la columna que muestre por parametro column>=1 (Empezando por 0).DOne
public class Table {
    //String rutaFichero;
    private List<List<String>> tabla;
    private String[] headers;
    
    public Table(String nombreFichero) throws IOException{
        this.tabla = new ArrayList<>();

        //rutaFichero = getClass().getClassLoader().getResource(nombreFichero).getFile();

        try (BufferedReader br = new BufferedReader(new FileReader(nombreFichero))) {
            String line;
            boolean firstLine = true;
            
            while ((line = br.readLine()) != null) {
                String[] values = line.split(","); // Separador por comas
                
                if (firstLine) {
                    headers = values;
                    firstLine = false;
                } else {
                    tabla.add(Arrays.asList(values));
                }
            }
        }
    }

    public String[] getHeaders() {
        return headers;
    }

    public List<String> getRow(Integer row){    
        if (row <= 0 || row >= tabla.size()) {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }
        return tabla.get(row);
    }

    public String[] getColumn(Integer col){    
        if (col <= 0 || col >= tabla.size()) {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }
        String[] columna = new String[tabla.size()];
        for (int i = 0; i<tabla.size(); i++){
            columna[i] = tabla.get(i+1).get(col);
        }
        return columna;
    }
    
    public void printTable() {
        System.out.println(String.join(" | ", headers));
        System.out.println("-".repeat(headers.length * 10));
        
        for (List<String> row : tabla) {
            System.out.println(String.join(" | ", row));
        }
    }
}
