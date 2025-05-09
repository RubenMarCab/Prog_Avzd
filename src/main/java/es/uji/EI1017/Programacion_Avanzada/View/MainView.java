package es.uji.EI1017.Programacion_Avanzada.View;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainView {
    // Algoritmo
    public RadioButton songRadio;
    public RadioButton genreRadio;
    public ToggleGroup algorithmGroup;

    // Métrica
    public RadioButton euclidRadio;
    public RadioButton manhRadio;
    public ToggleGroup metricGroup;

    // Resto de controles
    public ListView<String> songListView;
    public Spinner<Integer> numRecommendationsSpinner;
    public Button clearButton;
    public Button recommendButton;
    public ListView<String> recommendationListView;

    public void start(Stage primaryStage) {
        // ----------------------------------------------------
        // — Controles —
        // ----------------------------------------------------
        songRadio = new RadioButton("Canción");
        genreRadio = new RadioButton("Género");
        algorithmGroup = new ToggleGroup();
        songRadio.setToggleGroup(algorithmGroup);
        genreRadio.setToggleGroup(algorithmGroup);
        songRadio.setSelected(true);

        euclidRadio = new RadioButton("Euclidiana");
        manhRadio = new RadioButton("Manhattan");
        metricGroup = new ToggleGroup();
        euclidRadio.setToggleGroup(metricGroup);
        manhRadio.setToggleGroup(metricGroup);
        euclidRadio.setSelected(true);

        songListView = new ListView<>();
        recommendationListView = new ListView<>();
        numRecommendationsSpinner = new Spinner<>(1, 50, 10);
        clearButton = new Button("Borrar lista");

        recommendButton = new Button("Obtener recomendaciones");
        recommendButton.setMaxWidth(Double.MAX_VALUE);
        recommendButton.setStyle(
                "-fx-font-weight: bold; "
                        + "-fx-background-color: white; "
                        + "-fx-text-fill: #004394; "
                        + "-fx-font-size: 16px;"
        );
        recommendButton.setPadding(new Insets(10, 0, 10, 0));

        // ----------------------------------------------------
        // — Estilos CSS generales —
        // ----------------------------------------------------
        String fondoPrincipal = "-fx-background-color: #0094e3;";
        String textTytles = "-fx-font-weight: bold; -fx-text-fill: black; -fx-font-size: 14px;";
        String textColor = "-fx-text-fill: black; -fx-font-size: 14px;";

        Label lblAlgo = new Label("Recomendar en base a:");
        Label lblMetric = new Label("Elige la métrica:");
        Label lblSongs = new Label("Elige la canción:");
        Label lblNum = new Label("Cantidad:");
        Label lblResults = new Label("Recomendaciones");

        lblAlgo.setStyle(textTytles);
        lblMetric.setStyle(textTytles);
        lblSongs.setStyle(textTytles);
        lblNum.setStyle(textTytles);
        lblResults.setStyle(textTytles);

        songRadio.setStyle(textColor);
        genreRadio.setStyle(textColor);
        euclidRadio.setStyle(textColor);
        manhRadio.setStyle(textColor);
        clearButton.setStyle(textColor);

        songListView.setStyle("-fx-control-inner-background: white; -fx-text-fill: black;");
        recommendationListView.setStyle("-fx-control-inner-background: white; -fx-text-fill: black;");

        // ----------------------------------------------------
        // Columna 1: Configuración de la recomendación
        // ----------------------------------------------------
        VBox algoBox = new VBox(5, lblAlgo, songRadio, genreRadio);
        algoBox.setPadding(new Insets(0, 0, 15, 0));

        VBox metricBox = new VBox(5, lblMetric, euclidRadio, manhRadio);
        metricBox.setPadding(new Insets(0, 0, 15, 0));

        VBox countBox = new VBox(5, lblNum, numRecommendationsSpinner, clearButton);
        countBox.setPadding(new Insets(0, 0, 15, 0));

        // Region expansible para empujar el botón al pie
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        VBox col1 = new VBox(10,
                algoBox,
                metricBox,
                countBox,
                spacer,
                recommendButton
        );
        col1.setPadding(new Insets(20));
        col1.setStyle(fondoPrincipal);
        col1.setPrefWidth(300);

        // ----------------------------------------------------
        // Columna 2: listado de canciones
        // ----------------------------------------------------
        VBox col2 = new VBox(10, lblSongs, songListView);
        col2.setPadding(new Insets(20));
        col2.setStyle(fondoPrincipal);
        col2.setMinWidth(350);
        col2.setMaxWidth(Double.MAX_VALUE);
        VBox.setVgrow(songListView, Priority.ALWAYS);

        // ----------------------------------------------------
        // Columna 3: Resultados
        // ----------------------------------------------------
        VBox col3 = new VBox(10, lblResults, recommendationListView);
        col3.setPadding(new Insets(20));
        col3.setStyle(fondoPrincipal);
        col3.setMinWidth(350);
        col3.setMaxWidth(Double.MAX_VALUE);
        VBox.setVgrow(recommendationListView, Priority.ALWAYS);

        // — Contenedor principal con HGrow en col2 y col3 —
        // "HGrow" permite la expansión de tamaño
        HBox root = new HBox(20, col1, col2, col3);
        root.setStyle(fondoPrincipal);
        HBox.setHgrow(col2, Priority.ALWAYS);
        HBox.setHgrow(col3, Priority.ALWAYS);

        // — Escena y escenario —
        Scene scene = new Scene(root, 1024, 600, Color.web("#0094e3"));
        primaryStage.setScene(scene);
        primaryStage.setTitle("RECOMMEND U");
        primaryStage.show();
    }
}
