package es.uji.EI1017.Programacion_Avanzada.Controller;

import es.uji.EI1017.Programacion_Avanzada.Algoritmos.Algorithm;
import es.uji.EI1017.Programacion_Avanzada.Algoritmos.Distance;
import es.uji.EI1017.Programacion_Avanzada.Algoritmos.EuclideanDistance;
import es.uji.EI1017.Programacion_Avanzada.Algoritmos.KMeans.KMeans;
import es.uji.EI1017.Programacion_Avanzada.Algoritmos.KNN.KNN;
import es.uji.EI1017.Programacion_Avanzada.Algoritmos.ManhattanDistance;
import es.uji.EI1017.Programacion_Avanzada.Excepciones.InvalidClusterNumberException;
import es.uji.EI1017.Programacion_Avanzada.Excepciones.LikedItemNotFoundException;
import es.uji.EI1017.Programacion_Avanzada.LecturaCSV.Table;
import es.uji.EI1017.Programacion_Avanzada.LecturaCSV.TableWithLabels;
import es.uji.EI1017.Programacion_Avanzada.Model.SongDataLoader;
import es.uji.EI1017.Programacion_Avanzada.RecSys.RecSys;
import es.uji.EI1017.Programacion_Avanzada.View.MainView;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.util.List;

public class MainController {
    private RecSys recSys;
    private MainView view;

    public void init(Stage stage) {
        view = new MainView();
        view.start(stage);

        // Pobla la lista de canciones reales
        loadSongs();

        // Deshabilita el botón si no hay selección completa
        view.recommendButton.setDisable(true);
        view.algorithmComboBox.valueProperty().addListener((o, old, val) -> checkEnableRecommend());
        view.distanceComboBox.valueProperty().addListener((o, old, val) -> checkEnableRecommend());
        view.songListView.getSelectionModel().selectedItemProperty().addListener((o, old, val) -> checkEnableRecommend());

        view.recommendButton.setOnAction(e -> {
            try {
                generateRecommendations();
            } catch (InvalidClusterNumberException ex) {
                showError("Parámetros de KMeans inválidos", ex.getMessage());
            }
        });
    }

    private void checkEnableRecommend() {
        boolean ready = view.algorithmComboBox.getValue() != null
                && view.distanceComboBox.getValue()  != null
                && view.songListView.getSelectionModel().getSelectedItem() != null;
        view.recommendButton.setDisable(!ready);
    }

    private void loadSongs() {
        List<String> names = SongDataLoader.loadTestNames();
        view.songListView.getItems().setAll(names);
    }

    private void generateRecommendations() throws InvalidClusterNumberException {
        String algorithmChoice = view.algorithmComboBox.getValue();
        String distanceChoice  = view.distanceComboBox.getValue();
        String likedSong       = view.songListView.getSelectionModel().getSelectedItem();
        int    numRecs         = view.numRecommendationsSpinner.getValue();

        // 1. Crea el algoritmo
        Algorithm algorithm = createAlgorithm(algorithmChoice, distanceChoice);
        recSys = new RecSys(algorithm);

        // 2. Carga datos reales
        TableWithLabels trainTable = SongDataLoader.loadTrainData();
        Table            testTable  = SongDataLoader.loadTestData();
        List<String>     names      = SongDataLoader.loadTestNames();

        // 3. Entrena y inicializa
        recSys.train(trainTable);
        recSys.initialise(testTable, names);

        // 4. Llama a recommend() y maneja si el ítem no existe
        try {
            List<String> recs = recSys.recommend(likedSong, numRecs);
            view.recommendationOutput.setText(String.join("\n", recs));
        } catch (LikedItemNotFoundException ex) {
            showError("Canción no válida", ex.getMessage());
        }
    }

    private Algorithm createAlgorithm(String type, String distance)
            throws InvalidClusterNumberException {
        Distance dist = "Manhattan".equals(distance)
                ? new ManhattanDistance()
                : new EuclideanDistance();

        if ("KNN".equals(type)) {
            int k = 1;  // o hazlo configurable
            return new KNN(k, dist);
        } else {
            int numClusters   = 3;
            int numIterations = 10;
            long seed         = 42L;
            return new KMeans(numClusters, numIterations, seed, dist);
        }
    }

    private void showError(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
