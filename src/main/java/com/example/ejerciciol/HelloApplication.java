package com.example.ejerciciol;

import BBDD.ConexionBBDD;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Properties;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Properties connConfig = ConexionBBDD.loadProperties();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 220, 150);
        stage.setResizable(false);
        stage.setTitle("AVIONES - LOGIN!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}