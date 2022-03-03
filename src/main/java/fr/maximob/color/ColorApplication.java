package fr.maximob.color;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ColorApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ColorApplication.class.getResource("color-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 320);
        stage.setTitle("JavaFX Color");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}