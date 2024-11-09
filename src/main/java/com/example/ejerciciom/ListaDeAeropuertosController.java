package com.example.ejerciciom;

import com.example.ejerciciom.BBDD.ConexionBBDD;
import com.example.ejerciciom.DAO.*;
import com.example.ejerciciom.Model.AeropuertoPrivadoModel;
import com.example.ejerciciom.Model.AeropuertoPublicoModel;
import com.example.ejerciciom.Model.AvionModel;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

import static com.example.ejerciciom.EjercicioMApp.stage;


/**
 * Controlador de la vista que gestiona la lista de aeropuertos.
 * Proporciona funcionalidad para agregar, editar, eliminar y filtrar aeropuertos,
 * tanto privados como públicos, así como manejar la interacción con los aviones.
 */
public class ListaDeAeropuertosController {

    @FXML private ToggleGroup grupoRB;

    @FXML private MenuItem miActivarDesactivarAvion;

    @FXML private MenuItem miAniadirAeropuerto;

    @FXML private MenuItem miAniadirAvion;

    @FXML private MenuItem miBorrarAeropuerto;

    @FXML private MenuItem miEditarAeropuerto;

    @FXML private MenuItem miEliminarAvion;

    @FXML private MenuItem miInformacionAeropuerto;

    @FXML private RadioButton rbPrivados;

    @FXML private RadioButton rbPublicos;

    @FXML private TableView<AeropuertoPrivadoModel> tablaPriv;

    @FXML private TableView<AeropuertoPublicoModel> tablaPubli;

    @FXML private TableColumn<AeropuertoPrivadoModel, Integer> tcIdPrivado;

    @FXML private TableColumn<AeropuertoPublicoModel, Integer> tcIdPublico;

    @FXML private TableColumn<AeropuertoPrivadoModel, String> tcNombrePrivado;

    @FXML private TableColumn<AeropuertoPublicoModel, String> tcNombrePublico;

    @FXML private TableColumn<AeropuertoPrivadoModel, String> tcPaisPrivado;

    @FXML private TableColumn<AeropuertoPublicoModel, String> tcPaisPublico;

    @FXML private TableColumn<AeropuertoPrivadoModel, String> tcCiudadPrivado;

    @FXML private TableColumn<AeropuertoPublicoModel, String> tcCiudadPublico;

    @FXML private TableColumn<AeropuertoPrivadoModel, String> tcCallePrivado;

    @FXML private TableColumn<AeropuertoPublicoModel, String> tcCallePublico;

    @FXML private TableColumn<AeropuertoPrivadoModel, Integer> tcNumeroPrivado;

    @FXML private TableColumn<AeropuertoPublicoModel, Integer> tcNumeroPublico;

    @FXML private TableColumn<AeropuertoPrivadoModel, Integer> tcAnioPrivado;

    @FXML private TableColumn<AeropuertoPublicoModel, Integer> tcAnioPublico;

    @FXML private TableColumn<AeropuertoPrivadoModel, Integer> tcCapacidadPrivado;

    @FXML private TableColumn<AeropuertoPublicoModel, Integer> tcCapacidadPublico;

    @FXML private TableColumn<AeropuertoPrivadoModel, Integer> tcNumeroSocios;

    @FXML private TableColumn<AeropuertoPublicoModel, Integer> tcNumeroTrabajadores;

    @FXML private TableColumn<AeropuertoPublicoModel, Double> tcFinanciacion;

    @FXML private TextField tfNombre;

    private static ObservableList<AeropuertoPrivadoModel> listaTodasPrivado;
    private static ObservableList<AeropuertoPublicoModel> listaTodasPublico;
    private FilteredList<AeropuertoPrivadoModel> filtroPrivado;
    private FilteredList<AeropuertoPublicoModel> filtroPublico;
    private boolean esPublico = true;
    private static Stage s;
    private static boolean esAniadir;
    private static boolean borrar=true;

    // Almacena la opción seleccionada actualmente
    private MenuItem selectedMenuItem;

    /**
     * Establece la lista de aeropuertos privados.
     *
     * @param listaTodasPrivado la lista de aeropuertos privados que se va a asignar
     */
    public static void setListaTodasPrivado(ObservableList<AeropuertoPrivadoModel> listaTodasPrivado) {
        ListaDeAeropuertosController.listaTodasPrivado = listaTodasPrivado;
    }

    /**
     * Establece la lista de aeropuertos públicos.
     *
     * @param listaTodasPublico la lista de aeropuertos públicos que se va a asignar
     */
    public static void setListaTodasPublico(ObservableList<AeropuertoPublicoModel> listaTodasPublico) {
        ListaDeAeropuertosController.listaTodasPublico = listaTodasPublico;
    }

    /**
     * Devuelve el estado de la variable que indica si se está eliminando un avión.
     *
     * @return true si se está en el proceso de eliminación de un avión; false en caso contrario
     */
    public static boolean isBorrar() {
        return borrar;
    }



    /**
     * Inicializa el controlador.
     * Carga las listas de aeropuertos y establece los valores de las tablas.
     */
    @FXML
    private void initialize() {
        try {
            ConexionBBDD con=new ConexionBBDD();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tfNombre.setOnKeyReleased(event -> filtrarPorNombre());

        // Configurar el evento de doble clic en la tabla privada
        tablaPriv.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Doble clic
                informacionAeropuerto(null); // Llamamos a la función con un evento nulo
            }
        });

        // Configurar el evento de doble clic en la tabla pública
        tablaPubli.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Doble clic
                informacionAeropuerto(null); // Llamamos a la función con un evento nulo
            }
        });

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

        // Manejo de teclado en el menú
        addMenuNavigationHandlers();
    }

    private void addMenuNavigationHandlers() {
        // Asociar acciones a los elementos del menú
        miAniadirAeropuerto.setOnAction(this::aniadirAeropuerto);
        miActivarDesactivarAvion.setOnAction(this::activarDesactivarAvion);
        miAniadirAvion.setOnAction(this::aniadirAvion);
        miBorrarAeropuerto.setOnAction(this::borrarAeropuerto);
        miEditarAeropuerto.setOnAction(this::editarAeropuerto);
        miEliminarAvion.setOnAction(this::eliminarAvion);
        miInformacionAeropuerto.setOnAction(this::informacionAeropuerto);

        // Agregar un EventFilter al root de la escena
        Scene scene = stage.getScene();// Asumiendo que el MenuItem es parte de un Popup
        if (scene != null) {
            scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                if (selectedMenuItem != null) {
                    if (event.getCode() == KeyCode.UP) {
                        navigateMenu(-1);
                    } else if (event.getCode() == KeyCode.DOWN) {
                        navigateMenu(1);
                    } else if (event.getCode() == KeyCode.ENTER) {
                        selectedMenuItem.fire(); // Ejecutar la acción del menú seleccionado
                    }
                }
            });
        }
    }

    /**
     * Establece el elemento de menú actualmente seleccionado.
     *
     * @param menuItem El elemento de menú que se va a seleccionar
     */
    private void setSelectedMenuItem(MenuItem menuItem) {
        if (selectedMenuItem != null) {
            selectedMenuItem.setStyle(""); // Limpiar el estilo del anterior
        }
        selectedMenuItem = menuItem;
        // Resalta el elemento seleccionado
        menuItem.setStyle("-fx-background-color: lightblue;"); // Puedes cambiar el color según tus preferencias
    }

    /**
     * Navega por los elementos del menú.
     *
     * @param direction La dirección en la que navegar (1 para abajo, -1 para arriba)
     */
    private void navigateMenu(int direction) {
        // Obtener todos los elementos de menú en un array
        MenuItem[] menuItems = {
                miAniadirAeropuerto,
                miActivarDesactivarAvion,
                miAniadirAvion,
                miBorrarAeropuerto,
                miEditarAeropuerto,
                miEliminarAvion,
                miInformacionAeropuerto
        };

        // Encontrar el índice del elemento actualmente seleccionado
        int currentIndex = -1;
        for (int i = 0; i < menuItems.length; i++) {
            if (menuItems[i] == selectedMenuItem) {
                currentIndex = i;
                break;
            }
        }

        // Calcular el nuevo índice
        int newIndex = currentIndex + direction;

        // Ajustar el índice si está fuera de límites
        if (newIndex < 0) newIndex = menuItems.length - 1; // Volver al final si es el inicio
        else if (newIndex >= menuItems.length) newIndex = 0; // Volver al inicio si es el final

        // Cambiar la selección
        setSelectedMenuItem(menuItems[newIndex]);
    }

    /**
     * Filtra la lista de aeropuertos según el texto ingresado en el campo de texto.
     */
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


    /**
     * Maneja la acción de activar o desactivar un avión.
     * Este método muestra un nuevo escenario (ventana) donde el usuario puede
     * activar o desactivar un avión. El título de la ventana cambia dependiendo
     * de si se está en modo de eliminación o no.
     *
     * @param event el evento de acción que activa este método
     */
    @FXML
    void activarDesactivarAvion(ActionEvent event) {
        borrar=false;
        s=new Stage();
        Scene scene;
        try {
            FXMLLoader controlador = new FXMLLoader(EjercicioMApp.class.getResource("activarDesactivarAvion.fxml"));
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
        s.initOwner(EjercicioMApp.getStage());
        s.initModality(javafx.stage.Modality.WINDOW_MODAL);
        s.showAndWait();
    }


    /**
     * Maneja la acción de añadir un nuevo aeropuerto.
     * Este método muestra un nuevo escenario (ventana) donde el usuario puede
     * añadir un aeropuerto. Configura las tablas privadas y públicas y establece
     * la visibilidad de los botones de opción correspondientes.
     *
     * @param event el evento de acción que activa este método
     */
    @FXML
    void aniadirAeropuerto(ActionEvent event) {
        esAniadir=true;
        s=new Stage();
        Scene scene;
        try {
            FXMLLoader controlador = new FXMLLoader(EjercicioMApp.class.getResource("aniadirEditarAeropuertos.fxml"));
            scene = new Scene(controlador.load());
            s.setTitle("AÑADIR AEROPUERTO");
            s.setScene(scene);
            AniadirEditarAeropuertosController controller = controlador.getController();
            controller.setTablaPriv(tablaPriv);
            controller.setTablaPubli(tablaPubli);
            controller.getRbPublico().setVisible(true);
            controller.getRbPrivado().setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        s.setResizable(false);
        s.initOwner(EjercicioMApp.getStage());
        s.initModality(javafx.stage.Modality.WINDOW_MODAL);
        s.showAndWait();
        filtrarPorNombre();
        tablaPubli.refresh();
        tablaPriv.refresh();
        initialize();
    }


    /**
     * Maneja la acción de añadir un nuevo avión.
     * Este metodo muestra un nuevo escenario (ventana) donde el usuario puede
     * añadir un avión. Se establece el título de la ventana correspondiente.
     *
     * @param event el evento de acción que activa este metodo
     */
    @FXML
    void aniadirAvion(ActionEvent event) {
        s=new Stage();
        Scene scene;
        try {
            FXMLLoader controlador = new FXMLLoader(EjercicioMApp.class.getResource("aniadirAvion.fxml"));
            scene = new Scene(controlador.load());
            s.setTitle("AÑADIR NUEVO AVION");
            s.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
        s.setResizable(false);
        s.initOwner(EjercicioMApp.getStage());
        s.initModality(javafx.stage.Modality.WINDOW_MODAL);
        s.showAndWait();
    }


    /**
     * Maneja la acción de borrar un aeropuerto seleccionado.
     * Este método verifica si hay un aeropuerto seleccionado en las tablas
     * pública o privada y, si es así, muestra una alerta de confirmación
     * para eliminar el aeropuerto. Si el usuario confirma, se elimina el
     * aeropuerto de la base de datos y se actualizan las listas y tablas
     * correspondientes. Si no hay ningún aeropuerto seleccionado, se muestra
     * un mensaje de error.
     *
     * @param event el evento de acción que activa este método
     */
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
            initialize();
        }else {
            Alert al=new Alert(Alert.AlertType.ERROR);
            al.setHeaderText(null);
            al.setContentText("No hay ninguno seleccionado, asi que no se puede eliminar ninguno");
            al.showAndWait();
        }
    }


    /**
     * Maneja la acción de editar un aeropuerto seleccionado.
     * Este método verifica si hay un aeropuerto seleccionado en las tablas
     * pública o privada y, si es así, muestra un nuevo escenario (ventana)
     * donde el usuario puede editar la información del aeropuerto. Configura
     * los campos de texto con los datos del aeropuerto seleccionado. Si no
     * hay ningún aeropuerto seleccionado, se muestra un mensaje de error.
     *
     * @param event el evento de acción que activa este método
     */
    @FXML
    void editarAeropuerto(ActionEvent event) {
        esAniadir=false;
        if(tablaPriv.getSelectionModel().getSelectedItem()!=null||tablaPubli.getSelectionModel().getSelectedItem()!=null) {
            s=new Stage();
            Scene scene;
            try {
                FXMLLoader controlador = new FXMLLoader(EjercicioMApp.class.getResource("aniadirEditarAeropuertos.fxml"));
                scene = new Scene(controlador.load());
                s.setTitle("EDITAR AEROPUERTO");
                s.setScene(scene);
                AniadirEditarAeropuertosController controller = controlador.getController();
                controller.setTablaPriv(tablaPriv);
                controller.setTablaPubli(tablaPubli);;
                controller.getRbPublico().setVisible(false);
                controller.getRbPrivado().setVisible(false);
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
                    rbPrivados.setSelected(true);
                    rbPublicos.setSelected(false);
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
            s.initOwner(EjercicioMApp.getStage());
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
        initialize();
    }


    /**
     * Maneja la acción de eliminar un avión.
     * Este método muestra un nuevo escenario (ventana) donde el usuario puede
     * eliminar un avión. Se establece el título de la ventana correspondiente
     * para indicar que la acción es eliminar un avión.
     *
     * @param event el evento de acción que activa este método
     */
    @FXML
    void eliminarAvion(ActionEvent event) {
        borrar=true;
        s=new Stage();
        Scene scene;
        try {
            FXMLLoader controlador = new FXMLLoader(EjercicioMApp.class.getResource("activarDesactivarAvion.fxml"));
            scene = new Scene(controlador.load());
            s.setTitle("ELIMINAR AVION");
            s.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
        s.setResizable(false);
        s.initOwner(EjercicioMApp.getStage());
        s.initModality(javafx.stage.Modality.WINDOW_MODAL);
        s.showAndWait();
    }

    /**
     * Muestra información detallada sobre el aeropuerto seleccionado.
     * Si se ha seleccionado un aeropuerto en la tabla pública o privada, se
     * construye una cadena con la información relevante y se muestra en una
     * ventana de alerta informativa. La información incluye nombre, país,
     * dirección, año de inauguración, capacidad, aviones asociados y otros
     * detalles dependiendo de si es un aeropuerto privado o público.
     *
     * @param event el evento de acción que activa este método
     */
    @FXML
    void informacionAeropuerto(ActionEvent event) {
        if (tablaPriv.getSelectionModel().getSelectedItem() != null ||
                tablaPubli.getSelectionModel().getSelectedItem() != null) {

            Alert al = new Alert(Alert.AlertType.INFORMATION);
            al.setHeaderText(null);
            StringBuilder str = new StringBuilder();

            if (tablaPriv.getSelectionModel().getSelectedItem() != null) {
                AeropuertoPrivadoModel modelo = tablaPriv.getSelectionModel().getSelectedItem();
                str.append("Nombre: ").append(modelo.getNombre()).append("\n");
                str.append("Pais: ").append(modelo.getDireccion().getPais()).append("\n");
                str.append("Direccion: C|").append(modelo.getDireccion().getCalle())
                        .append(" ").append(modelo.getDireccion().getNumero()).append(", ")
                        .append(modelo.getDireccion().getCiudad()).append("\n");
                str.append("Año de inauguracion: ").append(modelo.getAnioInauguracion()).append("\n");
                str.append("Capacidad: ").append(modelo.getCapacidad()).append("\n");
                str.append("Aviones:\n");

                for (AvionModel avion : DaoAvion.listaAviones(DaoAeropuerto.conseguirID(
                        modelo.getNombre(), modelo.getAnioInauguracion(), modelo.getCapacidad(),
                        DaoDireccion.conseguirID(modelo.getDireccion().getPais(),
                                modelo.getDireccion().getCiudad(), modelo.getDireccion().getCalle(),
                                modelo.getDireccion().getNumero()), modelo.getImagen()))) {

                    str.append("\tModelo: ").append(avion.getModelo()).append("\n");
                    str.append("\tNúmero de asientos: ").append(avion.getNumeroAsientos()).append("\n");
                    str.append("\tVelocidad máxima: ").append(avion.getVelocidadMaxima()).append("\n");
                    str.append("\t").append(avion.isActivado() ? "Activado" : "Desactivado").append("\n");
                }
                str.append("Privado\n");
                str.append("Nº de socios: ").append(modelo.getNumSocios());

            } else {
                AeropuertoPublicoModel modelo = tablaPubli.getSelectionModel().getSelectedItem();
                str.append("Nombre: ").append(modelo.getNombre()).append("\n");
                str.append("Pais: ").append(modelo.getDireccion().getPais()).append("\n");
                str.append("Direccion: ").append(modelo.getDireccion().getCalle())
                        .append(" ").append(modelo.getDireccion().getNumero()).append(", ")
                        .append(modelo.getDireccion().getCiudad()).append("\n");
                str.append("Año de inauguracion: ").append(modelo.getAnioInauguracion()).append("\n");
                str.append("Capacidad: ").append(modelo.getCapacidad()).append("\n");
                str.append("Aviones:\n");

                for (AvionModel avion : DaoAvion.listaAviones(DaoAeropuerto.conseguirID(
                        modelo.getNombre(), modelo.getAnioInauguracion(), modelo.getCapacidad(),
                        DaoDireccion.conseguirID(modelo.getDireccion().getPais(),
                                modelo.getDireccion().getCiudad(), modelo.getDireccion().getCalle(),
                                modelo.getDireccion().getNumero()), modelo.getImagen()))) {

                    str.append("\tModelo: ").append(avion.getModelo()).append("\n");
                    str.append("\tNúmero de asientos: ").append(avion.getNumeroAsientos()).append("\n");
                    str.append("\tVelocidad máxima: ").append(avion.getVelocidadMaxima()).append("\n");
                    str.append("\t").append(avion.isActivado() ? "Activado" : "Desactivado").append("\n");
                }
                str.append("Público\n");
                str.append("Financiacion: ").append(modelo.getFinanciacion()).append("\n");
                str.append("Número de trabajadores: ").append(modelo.getNumTrabajadores());
            }

            al.setContentText(str.toString());
            al.showAndWait();
        }
    }


    /**
     * Carga y muestra la tabla correspondiente a aeropuertos públicos o privados
     * según la selección del usuario. Si el botón de radio de aeropuertos públicos
     * está seleccionado, se muestra la tabla pública; de lo contrario, se muestra
     * la tabla privada.
     *
     * @param actionEvent el evento de acción que activa este método
     */
    public void cargarTabla(ActionEvent actionEvent) {
        esPublico = rbPublicos.isSelected();
        if (rbPublicos.isSelected()) {
            tablaPubli.setVisible(true);
            tablaPriv.setVisible(false);
        } else {
            tablaPubli.setVisible(false);
            tablaPriv.setVisible(true);
        }
    }

    /**
     * Devuelve la lista de todos los aeropuertos privados.
     *
     * @return una ObservableList de AeropuertoPrivadoModel que contiene
     *         todos los aeropuertos privados.
     */
    public static ObservableList<AeropuertoPrivadoModel> getListaTodasPrivado() {
        return listaTodasPrivado;
    }

    /**
     * Devuelve la lista de todos los aeropuertos públicos.
     *
     * @return una ObservableList de AeropuertoPublicoModel que contiene
     *         todos los aeropuertos públicos.
     */
    public static ObservableList<AeropuertoPublicoModel> getListaTodasPublico() {
        return listaTodasPublico;
    }

    /**
     * Devuelve la instancia actual de la ventana (Stage) utilizada por
     * la aplicación.
     *
     * @return el Stage actual que representa la ventana principal de la aplicación.
     */
    public static Stage getS() {
        return s;
    }

    /**
     * Indica si se está en el modo de añadir un nuevo aeropuerto.
     *
     * @return true si se está añadiendo un aeropuerto, false en caso contrario.
     */
    public static boolean isEsAniadir() {
        return esAniadir;
    }

}
