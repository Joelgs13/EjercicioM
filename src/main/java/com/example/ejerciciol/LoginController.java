package com.example.ejerciciol;

import DAO.DaoUsuarios;
import Model.UsuarioModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private Button btnLogin;

    @FXML
    private TextField tfPassword;

    @FXML
    private TextField tfUsuario;

    @FXML
    void login(ActionEvent event) {
        String usuario = tfUsuario.getText();
        String password = tfPassword.getText();

        // Comprobar si el usuario existe
        UsuarioModel usuarioModel = DaoUsuarios.getUsuario(usuario);
        if (usuarioModel != null && usuarioModel.getPassword().equals(password)) {
            // Usuario encontrado, cargar la pantalla de lista de aeropuertos
            loadAeropuertosScreen();
        } else {
            // Usuario no encontrado, mostrar alerta y limpiar campos
            mostrarAlerta("Usuario no encontrado", "No hay un usuario así en la base de datos.");
            tfUsuario.clear();
            tfPassword.clear();
        }
    }

    private void mostrarAlerta(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadAeropuertosScreen() {
        try {
            // Cargar la nueva pantalla
            Parent root = FXMLLoader.load(getClass().getResource("listaDeAeropuertos.fxml")); // Asegúrate de que la ruta sea correcta
            Stage stage = (Stage) btnLogin.getScene().getWindow(); // Obtener la ventana actual
            stage.setScene(new Scene(root)); // Cambiar la escena
            stage.setTitle("AVIONES - AEROPUERTOS");
            stage.show(); // Mostrar la nueva escena
        } catch (IOException e) {
            e.printStackTrace(); // Manejo de errores al cargar la pantalla
            mostrarAlerta("Error", "No se pudo cargar la pantalla de lista de aeropuertos.");
        }
    }
}
