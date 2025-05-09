package es.uji.EI1017.Programacion_Avanzada.View;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MainView {
    public ComboBox<String> algorithmComboBox;
    public ComboBox<String> distanceComboBox;
    public ListView<String> songListView;
    public Spinner<Integer> numRecommendationsSpinner;
    public Button recommendButton;
    public TextArea recommendationOutput;

    public void start(Stage primaryStage) {
        algorithmComboBox = new ComboBox<>();
        algorithmComboBox.getItems().addAll("KNN", "KMeans");

        distanceComboBox = new ComboBox<>();
        distanceComboBox.getItems().addAll("Euclidean", "Manhattan");

        songListView = new ListView<>();

        numRecommendationsSpinner = new Spinner<>(1, 20, 5);

        recommendButton = new Button("Recommend");
        recommendButton.setDisable(true);

        recommendationOutput = new TextArea();
        recommendationOutput.setEditable(false);

        VBox root = new VBox(10, algorithmComboBox, distanceComboBox, songListView,
                numRecommendationsSpinner, recommendButton, recommendationOutput);
        primaryStage.setScene(new Scene(root, 400, 600));
        primaryStage.setTitle("Song Recommender");
        primaryStage.show();
    }
}
