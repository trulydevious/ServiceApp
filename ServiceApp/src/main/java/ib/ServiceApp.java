package ib;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ServiceApp extends Application {

    /**
     * ServiceApp class is a main class to initialize application for this program.
     */

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        Parent root= FXMLLoader.load(getClass().getResource("/fxml/dbFX.fxml"));
        Scene scene= new Scene(root,675,525);
        primaryStage.setScene(scene);
        primaryStage.show();


    }
}