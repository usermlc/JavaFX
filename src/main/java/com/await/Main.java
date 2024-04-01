package com.await;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Main.fxml"));

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("FXML ComboBox Example");
        primaryStage.show();
    }
}