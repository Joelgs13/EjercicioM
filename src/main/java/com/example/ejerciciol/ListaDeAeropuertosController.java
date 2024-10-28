package com.example.ejerciciol;

import Model.AeropuertoPrivadoModel;
import Model.AeropuertoPublicoModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ListaDeAeropuertosController {

    @FXML
    private ToggleGroup grupoRB;

    @FXML
    private MenuItem miActivarDesactivarAvion;

    @FXML
    private MenuItem miAniadirAeropuerto;

    @FXML
    private MenuItem miAniadirAvion;

    @FXML
    private MenuItem miBorrarAeropuerto;

    @FXML
    private MenuItem miEditarAeropuerto;

    @FXML
    private MenuItem miEliminarAvion;

    @FXML
    private MenuItem miInformacionAeropuerto;

    @FXML
    private RadioButton rbPrivados;

    @FXML
    private RadioButton rbPublicos;

    @FXML
    private TableView<AeropuertoPrivadoModel> tablaPriv;

    @FXML
    private TableView<AeropuertoPublicoModel> tablaPubli;

    @FXML
    private TableColumn<AeropuertoPrivadoModel, Integer> tcIdPrivado;

    @FXML
    private TableColumn<AeropuertoPublicoModel, Integer> tcIdPublico;

    @FXML
    private TableColumn<AeropuertoPrivadoModel, String> tcNombrePrivado;

    @FXML
    private TableColumn<AeropuertoPublicoModel, String> tcNombrePublico;

    @FXML
    private TableColumn<AeropuertoPrivadoModel, String> tcPaisPrivado;

    @FXML
    private TableColumn<AeropuertoPublicoModel, String> tcPaisPublico;

    @FXML
    private TableColumn<AeropuertoPrivadoModel, String> tcCiudadPrivado;

    @FXML
    private TableColumn<AeropuertoPublicoModel, String> tcCiudadPublico;

    @FXML
    private TableColumn<AeropuertoPrivadoModel, String> tcCallePrivado;

    @FXML
    private TableColumn<AeropuertoPublicoModel, String> tcCallePublico;

    @FXML
    private TableColumn<AeropuertoPrivadoModel, Integer> tcNumeroPrivado;

    @FXML
    private TableColumn<AeropuertoPublicoModel, Integer> tcNumeroPublico;

    @FXML
    private TableColumn<AeropuertoPrivadoModel, Integer> tcAnioPrivado;

    @FXML
    private TableColumn<AeropuertoPublicoModel, Integer> tcAnioPublico;

    @FXML
    private TableColumn<AeropuertoPrivadoModel, Integer> tcCapacidadPrivado;

    @FXML
    private TableColumn<AeropuertoPublicoModel, Integer> tcCapacidadPublico;

    @FXML
    private TableColumn<AeropuertoPrivadoModel, Integer> tcNumeroSocios;

    @FXML
    private TableColumn<AeropuertoPublicoModel, Integer> tcNumeroTrabajadores;

    @FXML
    private TableColumn<AeropuertoPublicoModel, Double> tcFinanciacion;

    @FXML
    private TextField tfNombre;

    private ObservableList<AeropuertoPrivadoModel> listaTodasPrivado;
    private ObservableList<AeropuertoPublicoModel> listaTodasPublico;
    private FilteredList<AeropuertoPrivadoModel> filtroPrivado;
    private FilteredList<AeropuertoPublicoModel> filtroPublico;
    private boolean esPublico = true;
    private static Stage s;

    @FXML
    public void initialize() {
        // Inicializa las listas de datos y carga la tabla según el tipo de aeropuerto seleccionado
        cargarTabla();
    }

    @FXML
    void cargarTabla() {
        if (rbPublicos.isSelected()) {
            esPublico = true;
            cargarTablaPublico();
        } else {
            esPublico = false;
            cargarTablaPrivado();
        }
    }

    private void cargarTablaPublico() {
        // Realiza la consulta de datos y asigna los datos a la tabla de aeropuertos públicos
        listaTodasPublico = FXCollections.observableArrayList( /* consulta a la base de datos */ );
        filtroPublico = new FilteredList<>(listaTodasPublico, p -> true);
        tablaPubli.setItems(filtroPublico);
        tablaPubli.setVisible(true);
        tablaPriv.setVisible(false);
    }

    private void cargarTablaPrivado() {
        // Realiza la consulta de datos y asigna los datos a la tabla de aeropuertos privados
        listaTodasPrivado = FXCollections.observableArrayList( /* consulta a la base de datos */ );
        filtroPrivado = new FilteredList<>(listaTodasPrivado, p -> true);
        tablaPriv.setItems(filtroPrivado);
        tablaPubli.setVisible(false);
        tablaPriv.setVisible(true);
    }

    @FXML
    void filtrarPorNombre(ActionEvent event) {
        String filtroNombre = tfNombre.getText().toLowerCase();
        if (esPublico) {
            filtroPublico.setPredicate(aeropuerto -> aeropuerto.getNombre().toLowerCase().contains(filtroNombre));
        } else {
            filtroPrivado.setPredicate(aeropuerto -> aeropuerto.getNombre().toLowerCase().contains(filtroNombre));
        }
    }

    // Métodos de acción del menú
    @FXML
    void activarDesactivarAvion(ActionEvent event) {
        // Lógica para activar o desactivar un avión
    }

    @FXML
    void aniadirAeropuerto(ActionEvent event) {
        // Lógica para añadir un nuevo aeropuerto
    }

    @FXML
    void aniadirAvion(ActionEvent event) {
        // Lógica para añadir un nuevo avión
    }

    @FXML
    void borrarAeropuerto(ActionEvent event) {
        // Lógica para borrar un aeropuerto
    }

    @FXML
    void editarAeropuerto(ActionEvent event) {
        // Lógica para editar un aeropuerto
    }

    @FXML
    void eliminarAvion(ActionEvent event) {
        // Lógica para eliminar un avión
    }

    @FXML
    void informacionAeropuerto(ActionEvent event) {
        // Lógica para mostrar información detallada de un aeropuerto
    }
}
