package com.example.ejerciciol;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class AniadirAvionController {

    @FXML
    private ComboBox<?> cbAeropuerto;

    @FXML
    private RadioButton rbActivado;

    @FXML
    private RadioButton rbDesactivado;

    @FXML
    private ToggleGroup rbGrupo;

    @FXML
    private TextField tfAsientos;

    @FXML
    private TextField tfModelo;

    @FXML
    private TextField tfVelMaxima;

    @FXML
    void cancelar(ActionEvent event) {

    }

    @FXML
    void guardar(ActionEvent event) {

    }

}
