package com.example.ejerciciol;

import BBDD.ConexionBBDD;
import DAO.*;
import Model.AeropuertoPrivadoModel;
import Model.AeropuertoPublicoModel;
import Model.AvionModel;
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
    private static boolean borrar=true;

    public static void setListaTodasPrivado(ObservableList<AeropuertoPrivadoModel> listaTodasPrivado) {
        ListaDeAeropuertosController.listaTodasPrivado = listaTodasPrivado;
    }

    public static void setListaTodasPublico(ObservableList<AeropuertoPublicoModel> listaTodasPublico) {
        ListaDeAeropuertosController.listaTodasPublico = listaTodasPublico;
    }

    public static boolean isBorrar() {
        return borrar;
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
        tcCallePublico.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDireccion().getCalle()));
        tcCapacidadPublico.setCellValueFactory(new PropertyValueFactory<>("capacidad"));
        tcCiudadPublico.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDireccion().getCiudad()));
        tcFinanciacion.setCellValueFactory(new PropertyValueFactory<>("financiacion"));
        tcIdPublico.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcNombrePublico.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tcNumeroPublico.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getDireccion().getNumero()).asObject());
        tcPaisPublico.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDireccion().getPais()));
        tcNumeroTrabajadores.setCellValueFactory(new PropertyValueFactory<>("numTrabajadores"));
        filtroPublico=new FilteredList<AeropuertoPublicoModel>(listaTodasPublico);
        //Tabla privado
        tcAnioPrivado.setCellValueFactory(new PropertyValueFactory<>("anioInauguracion"));
        tcCallePrivado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDireccion().getCalle()));
        tcCapacidadPrivado.setCellValueFactory(new PropertyValueFactory<>("capacidad"));
        tcCiudadPrivado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDireccion().getCiudad()));
        tcIdPrivado.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcNombrePrivado.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tcNumeroPrivado.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getDireccion().getNumero()).asObject());
        tcPaisPrivado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDireccion().getPais()));
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
        borrar=false;
        s=new Stage();
        Scene scene;
        try {
            FXMLLoader controlador = new FXMLLoader(HelloApplication.class.getResource("activarDesactivarAvion.fxml"));
            scene = new Scene(controlador.load());
            if (ListaDeAeropuertosController.isBorrar()){
                s.setTitle("ELIMINAR AVION");
            } else {
                s.setTitle("ACTIVAR / DESACTIVAR AVION");
            }
            s.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
        s.setResizable(false);
        s.initOwner(HelloApplication.getStage());
        s.initModality(javafx.stage.Modality.WINDOW_MODAL);
        s.showAndWait();
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
        s=new Stage();
        Scene scene;
        try {
            FXMLLoader controlador = new FXMLLoader(HelloApplication.class.getResource("aniadirAvion.fxml"));
            scene = new Scene(controlador.load());
            s.setTitle("AÑADIR NUEVO AVION");
            s.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
        s.setResizable(false);
        s.initOwner(HelloApplication.getStage());
        s.initModality(javafx.stage.Modality.WINDOW_MODAL);
        s.showAndWait();
    }

    @FXML
    void borrarAeropuerto(ActionEvent event) {
        if(tablaPriv.getSelectionModel().getSelectedItem()!=null||tablaPubli.getSelectionModel().getSelectedItem()!=null) {
            Alert al=new Alert(Alert.AlertType.CONFIRMATION);
            al.setHeaderText(null);
            if(tablaPriv.getSelectionModel().getSelectedItem()!=null||tablaPubli.getSelectionModel().getSelectedItem()!=null) {
                al.setContentText("¿Estas seguro de que quieres borrar ese aeropuerto?");
                al.showAndWait();
                if(al.getResult().getButtonData().name().equals("OK_DONE")) {
                    if(esPublico) {
                        DaoAeropuertoPublico.eliminar(tablaPubli.getSelectionModel().getSelectedItem().getId());
                        DaoAeropuerto.eliminar(tablaPubli.getSelectionModel().getSelectedItem().getId());
                        listaTodasPublico=DaoAeropuertoPublico.cargarListaAeropuertosPublicos();
                        filtrarPorNombre();
                        tablaPubli.refresh();
                    }else {
                        DaoAeropuertoPrivado.eliminar(tablaPriv.getSelectionModel().getSelectedItem().getId());
                        DaoAeropuerto.eliminar(tablaPriv.getSelectionModel().getSelectedItem().getId());
                        listaTodasPrivado=DaoAeropuertoPrivado.cargarListaAeropuertosPrivados();
                        filtrarPorNombre();
                        tablaPriv.refresh();
                    }
                }
            }
        }else {
            Alert al=new Alert(Alert.AlertType.ERROR);
            al.setHeaderText(null);
            al.setContentText("No hay ninguno seleccionado, asi que no se puede eliminar ninguno");
            al.showAndWait();
        }
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
        borrar=true;
        s=new Stage();
        Scene scene;
        try {
            FXMLLoader controlador = new FXMLLoader(HelloApplication.class.getResource("activarDesactivarAvion.fxml"));
            scene = new Scene(controlador.load());
            s.setTitle("ELIMINAR AVION");
            s.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
        s.setResizable(false);
        s.initOwner(HelloApplication.getStage());
        s.initModality(javafx.stage.Modality.WINDOW_MODAL);
        s.showAndWait();
    }

    @FXML
    void informacionAeropuerto(ActionEvent event) {
        if(tablaPriv.getSelectionModel().getSelectedItem()!=null||tablaPubli.getSelectionModel().getSelectedItem()!=null) {
            Alert al=new Alert(Alert.AlertType.INFORMATION);
            al.setHeaderText(null);
            String str="";
            if(tablaPriv.getSelectionModel().getSelectedItem()!=null) {
                AeropuertoPrivadoModel modelo = tablaPriv.getSelectionModel().getSelectedItem();
                str+="Nombre: "+modelo.getNombre()+"\n";
                str+="Pais: "+modelo.getDireccion().getPais()+"\n";
                str+="Direccion: C|"+modelo.getDireccion().getCalle()+" "+modelo.getDireccion().getNumero()+","+modelo.getDireccion().getCiudad()+"\n";
                str+="Año de inauguracion: "+modelo.getAnioInauguracion()+"\n";
                str+="Capacidad: "+modelo.getCapacidad()+"\n";
                str+="Aviones:\n";
                for(AvionModel avion: DaoAvion.listaAviones(DaoAeropuerto.conseguirID(
                        modelo.getNombre(),modelo.getAnioInauguracion(),modelo.getCapacidad(),
                        DaoDireccion.conseguirID(modelo.getDireccion().getPais(),modelo.
                                        getDireccion().getCiudad(),modelo.getDireccion().getCalle(),
                                modelo.getDireccion().getNumero()), modelo.getImagen()))) {
                    str+="\tModelo: "+avion.getModelo()+"\n";
                    str+="\tNúmero de asientos: "+avion.getNumeroAsientos()+"\n";
                    str+="\tVelocidad máxima: "+avion.getVelocidadMaxima()+"\n";
                    if(avion.isActivado()) {
                        str+="\tActivado\n";
                    }else {
                        str+="\tDesactivado\n";
                    }
                }
                str+="Privado\n";
                str+="Nº de socios: "+modelo.getNumSocios();

            }else {
                AeropuertoPublicoModel modelo=tablaPubli.getSelectionModel().getSelectedItem();
                str+="Nombre: "+modelo.getNombre()+"\n";
                str+="Pais: "+modelo.getDireccion().getPais()+"\n";
                str+="Direccion: "+modelo.getDireccion().getCalle()+" "+modelo.getDireccion().getNumero()+","+modelo.getDireccion().getCiudad()+"\n";
                str+="Año de inauguracion: "+modelo.getAnioInauguracion()+"\n";
                str+="Capacidad: "+modelo.getCapacidad()+"\n";
                str+="Aviones:\n";
                for(AvionModel avion:DaoAvion.listaAviones(DaoAeropuerto.conseguirID(
                        modelo.getNombre(),modelo.getAnioInauguracion(),modelo.getCapacidad(),
                        DaoDireccion.conseguirID(modelo.getDireccion().getPais(),modelo.
                                        getDireccion().getCiudad(),modelo.getDireccion().getCalle(),
                                modelo.getDireccion().getNumero()), modelo.getImagen()))) {
                    str+="\tModelo: "+avion.getModelo()+"\n";
                    str+="\tNúmero de asientos: "+avion.getNumeroAsientos()+"\n";
                    str+="\tVelocidad máxima: "+avion.getVelocidadMaxima()+"\n";
                    if(avion.isActivado()) {
                        str+="\tActivado\n";
                    }else {
                        str+="\tDesactivado\n";
                    }
                }
                str+="Público\n";
                str+="Financiacion: "+modelo.getFinanciacion()+"\n";
                str+="Número de trabajadores: "+modelo.getNumTrabajadores();
            }
            al.setContentText(str);
            al.showAndWait();
        }
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
