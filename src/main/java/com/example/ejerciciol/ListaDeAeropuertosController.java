package com.example.ejerciciol;

import BBDD.ConexionBBDD;
import DAO.DaoAeropuerto;
import DAO.DaoAeropuertoPrivado;
import DAO.DaoAeropuertoPublico;
import Model.AeropuertoPrivadoModel;
import Model.AeropuertoPublicoModel;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

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

    private static ObservableList<AeropuertoPrivadoModel> listaTodasPrivado;
    private static ObservableList<AeropuertoPublicoModel> listaTodasPublico;
    private FilteredList<AeropuertoPrivadoModel> filtroPrivado;
    private FilteredList<AeropuertoPublicoModel> filtroPublico;
    private boolean esPublico = true;
    private static Stage s;
    private static boolean esAniadir;

    public static void setListaTodasPrivado(ObservableList<AeropuertoPrivadoModel> listaTodasPrivado) {
        ListaDeAeropuertosController.listaTodasPrivado = listaTodasPrivado;
    }

    public static void setListaTodasPublico(ObservableList<AeropuertoPublicoModel> listaTodasPublico) {
        ListaDeAeropuertosController.listaTodasPublico = listaTodasPublico;
    }

    @FXML
    private void initialize() {
        try {
            ConexionBBDD con=new ConexionBBDD();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tfNombre.setOnKeyReleased(event -> filtrarPorNombre());
        //Tabla publico
        listaTodasPublico= DaoAeropuertoPublico.cargarListaAeropuertosPublicos();
        tablaPubli.setItems(listaTodasPublico);
        tcAnioPublico.setCellValueFactory(new PropertyValueFactory<>("anioInauguracion"));
        tcCallePublico.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDireccion().getCalle()));
        tcCapacidadPublico.setCellValueFactory(new PropertyValueFactory<>("capacidad"));
        tcCiudadPublico.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDireccion().getCiudad()));
        tcFinanciacion.setCellValueFactory(new PropertyValueFactory<>("financiacion"));
        tcIdPublico.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcNombrePublico.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tcNumeroPublico.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getDireccion().getNumero()).asObject());
        tcPaisPublico.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDireccion().getPais()));
        tcNumeroTrabajadores.setCellValueFactory(new PropertyValueFactory<>("numTrabajadores"));
        filtroPublico=new FilteredList<AeropuertoPublicoModel>(listaTodasPublico);
        //Tabla privado
        tcAnioPrivado.setCellValueFactory(new PropertyValueFactory<>("anioInauguracion"));
        tcCallePrivado.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDireccion().getCalle()));
        tcCapacidadPrivado.setCellValueFactory(new PropertyValueFactory<>("capacidad"));
        tcCiudadPrivado.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDireccion().getCiudad()));
        tcIdPrivado.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcNombrePrivado.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tcNumeroPrivado.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getDireccion().getNumero()).asObject());
        tcPaisPrivado.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDireccion().getPais()));
        tcNumeroSocios.setCellValueFactory(new PropertyValueFactory<>("numSocios"));
        listaTodasPrivado= DaoAeropuertoPrivado.cargarListaAeropuertosPrivados();
        filtroPrivado=new FilteredList<AeropuertoPrivadoModel>(listaTodasPrivado);
        tablaPriv.setItems(listaTodasPrivado);
    }

    private void filtrarPorNombre() {
        String filtroTexto = tfNombre.getText().toLowerCase();  // Convertir el texto del filtro a minúsculas para hacer una búsqueda no sensible a mayúsculas/minúsculas

        if (esPublico) {
            filtroPublico.setPredicate(aeropuerto -> {
                if (filtroTexto == null || filtroTexto.isEmpty()) {
                    return true;  // Muestra todos si el filtro está vacío
                }
                String nombreAeropuerto = aeropuerto.getNombre().toLowerCase();
                return nombreAeropuerto.contains(filtroTexto);  // Verifica si el nombre contiene el texto del filtro
            });
            tablaPubli.setItems(filtroPublico);  // Actualiza la tabla pública con el filtro
        } else {
            filtroPrivado.setPredicate(aeropuerto -> {
                if (filtroTexto == null || filtroTexto.isEmpty()) {
                    return true;  // Muestra todos si el filtro está vacío
                }
                String nombreAeropuerto = aeropuerto.getNombre().toLowerCase();
                return nombreAeropuerto.contains(filtroTexto);  // Verifica si el nombre contiene el texto del filtro
            });
            tablaPriv.setItems(filtroPrivado);  // Actualiza la tabla privada con el filtro
        }
    }


    // Métodos de acción del menú
    @FXML
    void activarDesactivarAvion(ActionEvent event) {
        // Lógica para activar o desactivar un avión
    }

    @FXML
    void aniadirAeropuerto(ActionEvent event) {
        esAniadir=true;
        s=new Stage();
        Scene scene;
        try {
            FXMLLoader controlador = new FXMLLoader(HelloApplication.class.getResource("aniadirEditarAeropuertos.fxml"));
            scene = new Scene(controlador.load());
            s.setTitle("AÑADIR AEROPUERTO");
            s.setScene(scene);
            AniadirEditarAeropuertosController controller = controlador.getController();
            controller.setTablaPriv(tablaPriv);
            controller.setTablaPubli(tablaPubli);
        } catch (IOException e) {
            e.printStackTrace();
        }
        s.setResizable(false);
        s.initOwner(HelloApplication.getStage());
        s.initModality(javafx.stage.Modality.WINDOW_MODAL);
        s.showAndWait();
        filtrarPorNombre();
        tablaPubli.refresh();
        tablaPriv.refresh();
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
        esAniadir=false;
        if(tablaPriv.getSelectionModel().getSelectedItem()!=null||tablaPubli.getSelectionModel().getSelectedItem()!=null) {
            s=new Stage();
            Scene scene;
            try {
                FXMLLoader controlador = new FXMLLoader(HelloApplication.class.getResource("aniadirEditarAeropuertos.fxml"));
                scene = new Scene(controlador.load());
                s.setTitle("EDITAR AEROPUERTO");
                s.setScene(scene);
                AniadirEditarAeropuertosController controller = controlador.getController();
                controller.setTablaPriv(tablaPriv);
                controller.setTablaPubli(tablaPubli);
                if(esPublico) {
                    AeropuertoPublicoModel modelo=tablaPubli.getSelectionModel().getSelectedItem();
                    controller.setTxtAnioInauguracionText(modelo.getAnioInauguracion()+"") ;
                    controller.setTxtCalleText(modelo.getDireccion().getCalle());
                    controller.setTxtCapacidadText(modelo.getCapacidad()+"");
                    controller.setTxtCiudadText(modelo.getDireccion().getCiudad());
                    controller.setTxtFinanciacionText(modelo.getFinanciacion()+"");
                    controller.setTxtNombreText(modelo.getNombre());
                    controller.setTxtNumeroText(modelo.getDireccion().getNumero()+"");
                    controller.setTxtNumTrabajadoresText(modelo.getNumTrabajadores()+"");
                    controller.setTxtPaisText(modelo.getDireccion().getPais());
                }else {
                    AeropuertoPrivadoModel modelo=tablaPriv.getSelectionModel().getSelectedItem();
                    controller.setTxtAnioInauguracionText(modelo.getAnioInauguracion()+"") ;
                    controller.setTxtCalleText(modelo.getDireccion().getCalle());
                    controller.setTxtCapacidadText(modelo.getCapacidad()+"");
                    controller.setTxtCiudadText(modelo.getDireccion().getCiudad());
                    controller.setTxtNombreText(modelo.getNombre());
                    controller.setTxtNumeroText(modelo.getDireccion().getNumero()+"");
                    controller.setTxtNumSociosText(modelo.getNumSocios()+"");
                    controller.setTxtPaisText(modelo.getDireccion().getPais());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            s.setResizable(false);
            s.initOwner(HelloApplication.getStage());
            s.initModality(javafx.stage.Modality.WINDOW_MODAL);
            s.showAndWait();
            filtrarPorNombre();
            tablaPriv.refresh();
            tablaPubli.refresh();
        }else {
            Alert al=new Alert(Alert.AlertType.ERROR);
            al.setHeaderText(null);
            al.setContentText("NINGUN AEROPUERTO SELECCIONADO");
            al.showAndWait();
        }
    }

    @FXML
    void eliminarAvion(ActionEvent event) {
        // Lógica para eliminar un avión
    }

    @FXML
    void informacionAeropuerto(ActionEvent event) {
        // Lógica para mostrar información detallada de un aeropuerto
    }

    public void cargarTabla(ActionEvent actionEvent) {
        esPublico=rbPublicos.isSelected();
        if(rbPublicos.isSelected()) {
            tablaPubli.setVisible(true);
            tablaPriv.setVisible(false);
        }else {
            tablaPubli.setVisible(false);
            tablaPriv.setVisible(true);;
        }
    }

    public static ObservableList<AeropuertoPrivadoModel> getListaTodasPrivado() {
        return listaTodasPrivado;
    }

    public static ObservableList<AeropuertoPublicoModel> getListaTodasPublico() {
        return listaTodasPublico;
    }

    public static Stage getS() {
        return s;
    }

    public static boolean isEsAniadir() {
        return esAniadir;
    }
}
