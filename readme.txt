Explicación del código:

    - Clase Row: Representa una fila de datos numéricos (List<Double>). 
                Método getData() para obtener los valores de la fila.

    - Clase Table: Contiene los encabezados (headers: List<String>) y las filas (rows: List<Row>). 
                Métodos para obtener filas (getRowAt), columnas (getColumnAt) y mostrar la tabla (printTable).

    - Clase CSV: Método readTable(String filename) que lee un archivo CSV y devuelve un objeto Table. 
                Convierte los datos de texto en números (Double).

