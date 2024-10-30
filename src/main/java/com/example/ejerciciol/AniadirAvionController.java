package com.example.ejerciciol;

import DAO.DaoAeropuerto;
import Model.AeropuertoModel;
import Model.AvionModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import DAO.DaoAvion;

/**
 * Controlador para añadir un nuevo avión en la aplicación.
 * Este controlador gestiona la entrada de datos del usuario
 * para crear un nuevo avión y su validación antes de guardarlo
 * en la base de datos.
 */
public class AniadirAvionController {

    @FXML
    private ComboBox<AeropuertoModel> cbAeropuerto; // ComboBox para seleccionar el aeropuerto

    @FXML
    private RadioButton rbActivado; // RadioButton para activar el avión

    @FXML
    private RadioButton rbDesactivado; // RadioButton para desactivar el avión

    @FXML
    private ToggleGroup rbGrupo; // Grupo de radio buttons para activar/desactivar

    @FXML
    private TextField tfAsientos; // Campo de texto para ingresar el número de asientos

    @FXML
    private TextField tfModelo; // Campo de texto para ingresar el modelo del avión

    @FXML
    private TextField tfVelMaxima; // Campo de texto para ingresar la velocidad máxima del avión

    /**
     * Cancela la operación actual y cierra la ventana del controlador.
     *
     * @param event Evento de acción que desencadena la cancelación.
     */
    @FXML
    void cancelar(ActionEvent event) {
        ListaDeAeropuertosController.getS().close();
    }

    /**
     * Guarda la información del nuevo avión. Realiza validaciones sobre los campos de entrada
     * y muestra un mensaje de confirmación o error según el resultado.
     *
     * @param event Evento de acción que desencadena la operación de guardado.
     */
    @FXML
    void guardar(ActionEvent event) {
        String error = "";
        String modelo = tfModelo.getText();
        int numAsientos = -1;
        int velMax = -1;
        boolean existe = false;
        Alert al = new Alert(Alert.AlertType.INFORMATION);
        al.setHeaderText(null);

        // Validación del campo modelo
        if (tfModelo.getText().isEmpty()) {
            error += "El campo modelo es obligatorio\n";
        }

        // Validación del campo asientos
        if (tfAsientos.getText().isEmpty()) {
            error += "El campo asientos es obligatorio\n";
        } else {
            try {
                numAsientos = Integer.parseInt(tfAsientos.getText());
                if (numAsientos <= 0) {
                    throw new Exception();
                }
            } catch (NumberFormatException e) {
                error += "El número de asientos debe ser un número entero\n";
            } catch (Exception e) {
                error += "El número de asientos debe ser mayor que 0\n";
            }
        }

        // Validación del campo velocidad máxima
        if (tfVelMaxima.getText().isEmpty()) {
            error += "La velocidad máxima es obligatoria\n";
        } else {
            try {
                velMax = Integer.parseInt(tfVelMaxima.getText());
                if (velMax < 1) {
                    throw new Exception();
                }
            } catch (NumberFormatException e) {
                error += "La velocidad máxima debe ser un número entero\n";
            } catch (Exception e) {
                error += "La velocidad máxima debe ser mayor que 0\n";
            }
        }

        // Verificación de existencia de un avión con el mismo modelo en el aeropuerto seleccionado
        for (AvionModel avion : DaoAvion.conseguirListaTodos()) {
            if (avion.getModelo().equals(modelo) && cbAeropuerto.getSelectionModel().getSelectedItem().getId() == avion.getIdAeropuerto()) {
                existe = true;
            }
        }

        // Guardar el avión si no hay errores
        if (error.equals("") && !existe) {
            DaoAvion.aniadir(modelo, numAsientos, velMax, rbActivado.isSelected(), cbAeropuerto.getSelectionModel().getSelectedItem().getId());
            al.setContentText("Avión añadido correctamente");
        } else {
            // Manejo de errores
            if (error.equals("")) {
                al.setAlertType(Alert.AlertType.WARNING);
                al.setContentText("Ya existe un avión de ese modelo en ese aeropuerto");
            } else {
                al.setAlertType(Alert.AlertType.ERROR);
                al.setContentText(error);
            }
        }
        al.showAndWait();
    }

    /**
     * Inicializa el controlador, cargando la lista de aeropuertos en el ComboBox.
     */
    @FXML
    private void initialize() {
        this.cbAeropuerto.setItems(DaoAeropuerto.listaTodas());
    }
}
