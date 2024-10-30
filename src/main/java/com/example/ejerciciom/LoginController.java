package com.example.ejerciciom;

import DAO.DaoUsuarios;
import Model.UsuarioModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controlador para la pantalla de inicio de sesión. Gestiona la autenticación
 * del usuario y la transición a la pantalla de lista de aeropuertos.
 */
public class LoginController implements Initializable {

    @FXML
    private Button btnLogin;

    @FXML
    private TextField tfPassword;

    @FXML
    private TextField tfUsuario;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Listener para activar el botón de login al presionar Enter
        tfPassword.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                btnLogin.fire();  // Simula un clic en el botón Login
            }
        });

        tfUsuario.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                tfPassword.requestFocus();  // Mueve el foco al campo de contraseña
            }
        });
    }

    /**
     * Método que se ejecuta cuando se presiona el botón de inicio de sesión.
     * Comprueba si el usuario y la contraseña son correctos. Si son válidos,
     * carga la pantalla de lista de aeropuertos; de lo contrario, muestra un
     * mensaje de error.
     *
     * @param event el evento de acción que activa este método
     */
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

    /**
     * Muestra una alerta con el título y el mensaje especificados.
     *
     * @param title   el título de la alerta
     * @param message el mensaje a mostrar en la alerta
     */
    private void mostrarAlerta(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Carga la pantalla de lista de aeropuertos. Cambia la escena actual
     * a la nueva pantalla y maneja cualquier excepción que ocurra durante
     * el proceso de carga.
     */
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
