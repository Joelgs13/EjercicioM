package com.example.ejerciciol;

import DAO.DaoAeropuerto;
import Model.AeropuertoModel;
import Model.AvionModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import DAO.DaoAvion;

public class AniadirAvionController {

    @FXML
    private ComboBox<AeropuertoModel> cbAeropuerto;

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
        ListaDeAeropuertosController.getS().close();
    }

    /**
     * Guardar avion.
     *
     * @param event the event
     */
    @FXML
    void guardar(ActionEvent event) {
        String error="";
        String modelo=tfModelo.getText();
        int numAsientos=-1;
        int velMax=-1;
        boolean existe=false;
        Alert al=new Alert(Alert.AlertType.INFORMATION);
        al.setHeaderText(null);
        if(tfModelo.getText().isEmpty()) {
            error+="El campo modelo es obligatorio\n";
        }
        if(tfAsientos.getText().isEmpty()) {
            error+="El campo asientos es obligatorio\n";
        }else {
            try {
                numAsientos=Integer.parseInt(tfAsientos.getText());
                if(numAsientos<=0) {
                    throw new Exception();
                }
            }catch(NumberFormatException e) {
                error+="El numero de asientos debe ser un numero entero\n";
            } catch (Exception e) {
                error+="El numero de asientos debe ser mayor que 0\n";
            }
        }
        if(tfVelMaxima.getText().isEmpty()) {
            error+="La velocidad maxima es obligatoria\n";
        }else {
            try {
                velMax=Integer.parseInt(tfVelMaxima.getText());
                if(velMax<1) {
                    throw new Exception();
                }
            }catch(NumberFormatException e) {
                error+="La velocidad maxima debe ser un numero entero\n";
            } catch (Exception e) {
                error+="La velocidad maxima debe ser mayor que 0\n";
            }
        }
        for(AvionModel avion:DaoAvion.conseguirListaTodos()) {
            if(avion.getModelo().equals(modelo)&&cbAeropuerto.getSelectionModel().getSelectedItem().getId()==avion.getIdAeropuerto()) {
                existe=true;
            }
        }
        if(error.equals("")&&!existe) {
            DaoAvion.aniadir(modelo, numAsientos, velMax, rbActivado.isSelected(),cbAeropuerto.getSelectionModel().getSelectedItem().getId());
            al.setContentText("Avion añadido correctamente");
        }else {
            if(error.equals("")) {
                al.setAlertType(Alert.AlertType.WARNING);
                al.setContentText("Ya existe un avion de ese modelo en ese aeropuerto");
            }else {
                al.setAlertType(Alert.AlertType.ERROR);
                al.setContentText(error);
            }
        }
        al.showAndWait();
    }

    /**
     * Initializa el combo.
     */
    @FXML
    private void initialize() {
        this.cbAeropuerto.setItems(DaoAeropuerto.listaTodas());
    }

}
