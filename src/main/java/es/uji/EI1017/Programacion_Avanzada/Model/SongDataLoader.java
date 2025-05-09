package es.uji.EI1017.Programacion_Avanzada.Model;

import es.uji.EI1017.Programacion_Avanzada.LecturaCSV.CSVLabeledFileReader;    // :contentReference[oaicite:0]{index=0}:contentReference[oaicite:1]{index=1}
import es.uji.EI1017.Programacion_Avanzada.LecturaCSV.CSVUnlabeledFileReader; // :contentReference[oaicite:2]{index=2}:contentReference[oaicite:3]{index=3}
import es.uji.EI1017.Programacion_Avanzada.LecturaCSV.Table;
import es.uji.EI1017.Programacion_Avanzada.LecturaCSV.TableWithLabels;

import java.util.List;

public class SongDataLoader {

    // Rutas relativas en el classpath (por ejemplo, dentro de src/main/resources/)
    private static final String TRAIN_PATH        = "recommender/songs_train.csv";
    private static final String TEST_FEATURES_PATH = "recommender/songs_test_withoutnames.csv";
    private static final String TEST_NAMES_PATH    = "recommender/songs_test_names.csv";

    /**
     * Lee el CSV de entrenamiento (features + etiqueta al final de cada fila)
     * y devuelve un TableWithLabels con columnas y etiquetas cargadas.
     */
    public static TableWithLabels loadTrainData() {
        CSVLabeledFileReader reader = new CSVLabeledFileReader();
        return reader.readTableFromSource(TRAIN_PATH);
    }

    /**
     * Lee el CSV de prueba (solo features, sin etiqueta) y devuelve un Table.
     */
    public static Table loadTestData() {
        CSVUnlabeledFileReader reader = new CSVUnlabeledFileReader();
        return reader.readTableFromSource(TEST_FEATURES_PATH);
    }

    /**
     * Lee un CSV de una única columna (nombres de canciones) y devuelve la lista de nombres.
     */
    public static List<String> loadTestNames() {
        CSVUnlabeledFileReader reader = new CSVUnlabeledFileReader();
        return reader.readAllLines(TEST_NAMES_PATH);
    }
}
