package es.uji.EI1017.Programacion_Avanzada.Excepciones;

public class InvalidClusterNumberException extends Exception {
    private final int numClusters;
    private final int numDatos;

    public InvalidClusterNumberException(int numClusters, int numDatos) {
        super("Número de clusters inválido: " + numClusters + " con solo " + numDatos + " datos disponibles.");
        this.numClusters = numClusters;
        this.numDatos = numDatos;
    }

    public int getNumDatos() {
        return numDatos;
    }

    public int getNumberOfCusters() {
        return numClusters;
    }
}
