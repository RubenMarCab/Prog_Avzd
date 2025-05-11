package es.uji.EI1017.Programacion_Avanzada.Controller;

import es.uji.EI1017.Programacion_Avanzada.Algoritmos.*;
import es.uji.EI1017.Programacion_Avanzada.Algoritmos.KMeans.KMeans;
import es.uji.EI1017.Programacion_Avanzada.Algoritmos.KNN.KNN;
import es.uji.EI1017.Programacion_Avanzada.Excepciones.*;
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

        // Carga inicial de canciones
        loadSongs();

        // Listeners para habilitar/deshabilitar el botón
        view.songRadio.selectedProperty().addListener((o,old,n) -> checkEnable());
        view.genreRadio.selectedProperty().addListener((o,old,n) -> checkEnable());
        view.euclidRadio.selectedProperty().addListener((o,old,n) -> checkEnable());
        view.manhRadio.selectedProperty().addListener((o,old,n) -> checkEnable());
        view.songListView.getSelectionModel().selectedItemProperty()
                .addListener((o,old,n) -> checkEnable());

        // Acción de limpiar la lista de resultados
        view.clearButton.setOnAction(e -> view.recommendationListView.getItems().clear());

        // Acción de recomendar
        view.recommendButton.setOnAction(e -> {
            try {
                generateRecommendations();
            } catch (InvalidClusterNumberException ex) {
                showError("Parámetros de KMeans inválidos", ex.getMessage());
            }
        });
    }

    private void checkEnable() {
        boolean algoSelected   = view.songRadio.isSelected() || view.genreRadio.isSelected();
        boolean metricSelected = view.euclidRadio.isSelected() || view.manhRadio.isSelected();
        boolean songSelected   = view.songListView.getSelectionModel().getSelectedItem() != null;
        view.recommendButton.setDisable(!(algoSelected && metricSelected && songSelected));
    }

    private void loadSongs() {
        List<String> names = SongDataLoader.loadTestNames();
        view.songListView.getItems().setAll(names);
    }

    private void generateRecommendations() throws InvalidClusterNumberException {
        // ----------------------------------------------------
        // 1. Elige algoritmo según radio buttons
        // ----------------------------------------------------
        Distance dist = view.manhRadio.isSelected()
                ? new ManhattanDistance()
                : new EuclideanDistance();

        Algorithm algorithm;
        if (view.songRadio.isSelected()) {
            algorithm = new KNN(1, dist);
        } else {
            algorithm = new KMeans(3, 10, 42L, dist);
        }

        recSys = new RecSys(algorithm);

        // ----------------------------------------------------
        // 2. Carga datos
        // ----------------------------------------------------
        TableWithLabels train = SongDataLoader.loadTrainData();
        Table test  = SongDataLoader.loadTestData();
        List<String> names = SongDataLoader.loadTestNames();

        // ----------------------------------------------------
        // 3. Entrena e inicializa
        // ----------------------------------------------------
        recSys.train(train);
        recSys.initialise(test, names);

        // ----------------------------------------------------
        // 4. Recomienda y muestra en la lista
        // ----------------------------------------------------
        try {
            String liked = view.songListView.getSelectionModel().getSelectedItem();
            int cnt = view.numRecommendationsSpinner.getValue();
            List<String> recs = recSys.recommend(liked, cnt);
            view.recommendationListView.getItems().setAll(recs);
        } catch (LikedItemNotFoundException ex) {
            showError("Canción no encontrada", ex.getMessage());
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
