package com.example.ejerciciom;

import com.example.ejerciciom.BBDD.ConexionBBDD;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Properties;

/**
 * La clase HelloApplication es la clase principal de la aplicación.
 * Extiende la clase Application de JavaFX y gestiona la inicialización
 * y el lanzamiento de la interfaz de usuario.
 */
public class EjercicioMApp extends Application {

     static Stage stage;  // La ventana principal de la aplicación

    /**
     * Método que se llama al iniciar la aplicación.
     * Carga el archivo FXML para la interfaz de inicio de sesión
     * y muestra la ventana principal.
     *
     * @param s El escenario principal que se utiliza para mostrar la interfaz.
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */
    @Override
    public void start(Stage s) throws IOException {
        Properties connConfig = ConexionBBDD.loadProperties();  // Carga la configuración de conexión a la base de datos
        stage = s;  // Establece el escenario principal
        FXMLLoader fxmlLoader = new FXMLLoader(EjercicioMApp.class.getResource("login.fxml"));  // Carga el archivo FXML
        Scene scene = new Scene(fxmlLoader.load(), 220, 150);  // Crea una nueva escena con el tamaño especificado
        stage.setResizable(false);  // Evita que la ventana se pueda redimensionar
        stage.setTitle("AVIONES - LOGIN!");  // Establece el título de la ventana
        stage.setScene(scene);  // Establece la escena en el escenario
        stage.show();  // Muestra la ventana
    }

    /**
     * Devuelve el escenario principal de la aplicación.
     *
     * @return El escenario principal de la aplicación.
     */
    public static Stage getStage() {
        return stage;  // Retorna el escenario principal
    }

    /**
     * Método principal que se utiliza para iniciar la aplicación.
     *
     * @param args Argumentos de línea de comandos (no se utilizan).
     */
    public static void main(String[] args) {
        launch();  // Lanza la aplicación JavaFX
    }
}
