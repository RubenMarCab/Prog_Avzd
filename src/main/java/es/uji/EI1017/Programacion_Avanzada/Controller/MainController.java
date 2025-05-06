package es.uji.EI1017.Programacion_Avanzada.Controller;

import es.uji.EI1017.Programacion_Avanzada.RecSys.RecSys;
import es.uji.EI1017.Programacion_Avanzada.Algoritmos.Algorithm;
import es.uji.EI1017.Programacion_Avanzada.LecturaCSV.Table;
import es.uji.EI1017.Programacion_Avanzada.Excepciones.LikedItemNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class MainController {

    @FXML private ComboBox<String> cbAlgorithm;
    @FXML private ComboBox<String> cbDistance;
    @FXML private Spinner<Integer> spnCount;
    @FXML private Button btnRecommend;
    @FXML private ListView<String> lvSongs;
    @FXML private TableView<String> tvResults;
    @FXML private TableColumn<String, String> colTitle;

    private RecSys recSys;
    private ObservableList<String> songList;

    @FXML
    public void initialize() {
        // Configurar ComboBoxes
        cbAlgorithm.setItems(FXCollections.observableArrayList("kMeans", "kNN"));
        cbDistance.setItems(FXCollections.observableArrayList("Euclidiana", "Manhattan"));

        // Spinner para número de recomendaciones
        SpinnerValueFactory<Integer> factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 5);
        spnCount.setValueFactory(factory);

        // Configurar columna de resultados
        colTitle.setCellValueFactory(new PropertyValueFactory<>(""));

        // Deshabilitar botón hasta selección
        btnRecommend.setDisable(true);
        lvSongs.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) ->
                btnRecommend.setDisable(newSel == null)
        );
    }

    public void setRecSys(RecSys recSys) {
        this.recSys = recSys;
    }

    public void setSongList(List<String> songs) {
        this.songList = FXCollections.observableArrayList(songs);
        lvSongs.setItems(songList);
    }

    @FXML
    public void onRecommend(ActionEvent event) {
        String selected = lvSongs.getSelectionModel().getSelectedItem();
        int count = spnCount.getValue();

        // Validar selección
        if (selected == null || recSys == null) return;

        try {
            List<String> recommendations = recSys.recommend(selected, count);
            tvResults.setItems(FXCollections.observableArrayList(recommendations));
        } catch (LikedItemNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No se pudo encontrar la canción seleccionada");
            alert.showAndWait();
        }
    }
}