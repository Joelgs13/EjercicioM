package com.example.ejerciciol;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AniadirEditarAeropuertosController {

    @FXML
    private Label lbFinanciacion;

    @FXML
    private Label lbNumeroDeSocios;

    @FXML
    private Label lbNumeroDeTrabajadores;

    @FXML
    private RadioButton rbPrivado;

    @FXML
    private RadioButton rbPublico;

    @FXML
    private ToggleGroup rbTipoAeropuerto;

    @FXML
    private TextField tfAñoDeInauguracion;

    @FXML
    private TextField tfCalle;

    @FXML
    private TextField tfCapacidad;

    @FXML
    private TextField tfCiudad;

    @FXML
    private TextField tfFinanciacion;

    @FXML
    private TextField tfNombre;

    @FXML
    private TextField tfNumero;

    @FXML
    private TextField tfNumeroDeSocios;

    @FXML
    private TextField tfPais;

    @FXML
    private TextField tfTrabajadores;

    @FXML
    void cancelar(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    // Método para mostrar/ocultar campos según el tipo de aeropuerto seleccionado
    @FXML
    void generarCampos(ActionEvent event) {
        if (rbPrivado.isSelected()) {
            // Mostrar campos para aeropuerto privado
            lbNumeroDeSocios.setVisible(true);
            tfNumeroDeSocios.setVisible(true);

            // Ocultar campos para aeropuerto público
            lbFinanciacion.setVisible(false);
            tfFinanciacion.setVisible(false);
            lbNumeroDeTrabajadores.setVisible(false);
            tfTrabajadores.setVisible(false);
        } else if (rbPublico.isSelected()) {
            // Mostrar campos para aeropuerto público
            lbFinanciacion.setVisible(true);
            tfFinanciacion.setVisible(true);
            lbNumeroDeTrabajadores.setVisible(true);
            tfTrabajadores.setVisible(true);

            // Ocultar campos para aeropuerto privado
            lbNumeroDeSocios.setVisible(false);
            tfNumeroDeSocios.setVisible(false);
        }
    }

    @FXML
    void guardar(ActionEvent event) {

    }

}
