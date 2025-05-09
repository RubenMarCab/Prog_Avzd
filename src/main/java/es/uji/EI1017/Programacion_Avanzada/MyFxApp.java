package es.uji.EI1017.Programacion_Avanzada;

import javafx.application.Application;
import javafx.stage.Stage;
import es.uji.EI1017.Programacion_Avanzada.Controller.MainController;

public class MyFxApp extends Application {

    //Lanza la app
    @Override
    public void start(Stage primaryStage) throws Exception {
        MainController controller = new MainController();
        controller.init(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
