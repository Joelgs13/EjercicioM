package com.example.ejerciciom;

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

/**
 * Controlador para gestionar la activación y desactivación de aviones.
 * Este controlador maneja la interacción del usuario con la interfaz gráfica,
 * permitiendo seleccionar aeropuertos y aviones, y aplicar cambios en el estado
 * de los aviones según la selección realizada.
 */
public class activarDesactivarAvionController {

    @FXML
    private ComboBox<AeropuertoModel> cbAeropuertos; // ComboBox para seleccionar aeropuertos

    @FXML
    private ComboBox<AvionModel> cbAviones; // ComboBox para seleccionar aviones

    @FXML
    private RadioButton rbActivado; // RadioButton para activar el avión

    @FXML
    private RadioButton rbDesactivado; // RadioButton para desactivar el avión

    @FXML
    private ToggleGroup rbGrupo; // Grupo de radio buttons para activar/desactivar

    /**
     * Inicializa el controlador. Se cargan los aeropuertos en el ComboBox
     * y se configuran los RadioButtons según el estado de borrado.
     */
    @FXML
    private void initialize() {
        this.cbAeropuertos.setItems(DaoAeropuerto.listaTodas());
        rbActivado.setVisible(!ListaDeAeropuertosController.isBorrar());
        rbDesactivado.setVisible(!ListaDeAeropuertosController.isBorrar());
    }

    /**
     * Actualiza el ComboBox de aviones según el aeropuerto seleccionado.
     *
     * @param event Evento de acción que desencadena la actualización.
     */
    @FXML
    void actualizarCB(ActionEvent event) {
        this.cbAviones.setItems(DaoAvion.listaAviones(this.cbAeropuertos.getSelectionModel().getSelectedItem().getId()));
    }

    /**
     * Cancela la operación y cierra la ventana actual.
     *
     * @param event Evento de acción que desencadena la cancelación.
     */
    @FXML
    void cancelar(ActionEvent event) {
        ListaDeAeropuertosController.getS().close();
    }

    /**
     * Guarda los cambios realizados en el estado del avión o lo elimina
     * si se ha activado el modo de borrado.
     *
     * @param event Evento de acción que desencadena la operación de guardado.
     */
    @FXML
    void guardar(ActionEvent event) {
        Alert al = new Alert(Alert.AlertType.CONFIRMATION);
        al.setHeaderText(null);
        if (ListaDeAeropuertosController.isBorrar()) {
            al.setContentText("¿Estás seguro de que deseas eliminar el avión?");
            al.showAndWait();
            if (al.getResult().getButtonData().name().equals("OK_DONE")) {
                DaoAvion.delete(cbAviones.getSelectionModel().getSelectedItem().getModelo(), cbAviones.getSelectionModel().getSelectedItem().getIdAeropuerto());
                cbAviones.setItems(DaoAvion.listaAviones(cbAeropuertos.getSelectionModel().getSelectedItem().getId()));
            }
        } else {
            al.setAlertType(Alert.AlertType.INFORMATION);
            DaoAvion.update(cbAviones.getSelectionModel().getSelectedItem().getModelo(), cbAviones.getSelectionModel().getSelectedItem().getIdAeropuerto(),
                    rbActivado.isSelected());
            al.setContentText("Avión modificado correctamente");
            al.showAndWait();
        }
    }
}
