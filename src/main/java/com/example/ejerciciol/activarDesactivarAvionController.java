package com.example.ejerciciol;

import DAO.DaoAeropuerto;
import Model.AeropuertoModel;
import Model.AvionModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import DAO.DaoAvion;

public class activarDesactivarAvionController {

    @FXML
    private ComboBox<AeropuertoModel> cbAeropuertos;

    @FXML
    private ComboBox<AvionModel> cbAviones;

    @FXML
    private RadioButton rbActivado;

    @FXML
    private RadioButton rbDesactivado;

    @FXML
    private ToggleGroup rbGrupo;


    @FXML
    private void initialize() {
        this.cbAeropuertos.setItems(DaoAeropuerto.listaTodas());
        rbActivado.setVisible(!ListaDeAeropuertosController.isBorrar());
        rbDesactivado.setVisible(!ListaDeAeropuertosController.isBorrar());
    }

    @FXML
    void actualizarCB(ActionEvent event) {
        this.cbAviones.setItems(DaoAvion.listaAviones(this.cbAeropuertos.getSelectionModel().getSelectedItem().getId()));
    }

    @FXML
    void cancelar(ActionEvent event) {
        ListaDeAeropuertosController.getS().close();
    }

    @FXML
    void guardar(ActionEvent event) {
        Alert al=new Alert(Alert.AlertType.CONFIRMATION);
        al.setHeaderText(null);
        if(ListaDeAeropuertosController.isBorrar()) {
            al.setContentText("¿Estas seguro de que deseas eliminar el avion?");
            al.showAndWait();
            if(al.getResult().getButtonData().name().equals("OK_DONE")) {
                DaoAvion.delete(cbAviones.getSelectionModel().getSelectedItem().getModelo(),cbAviones.getSelectionModel().getSelectedItem().getIdAeropuerto());
                cbAviones.setItems(DaoAvion.listaAviones(cbAeropuertos.getSelectionModel().getSelectedItem().getId()));
            }
        }else {
            al.setAlertType(Alert.AlertType.INFORMATION);
            DaoAvion.update(cbAviones.getSelectionModel().getSelectedItem().getModelo(), cbAviones.getSelectionModel().getSelectedItem().getIdAeropuerto(),
                    rbActivado.isSelected());
            al.setContentText("Avion modificado correctamente");
            al.showAndWait();
        }
    }



}
