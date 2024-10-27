package com.example.ejerciciol;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class AniadirEditarAeropuertosController {

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
    private TextField tfPais;

    @FXML
    private TextField tfTrabajadores;

    @FXML
    void cancelar(ActionEvent event) {

    }

    @FXML
    void guardar(ActionEvent event) {

    }

}
