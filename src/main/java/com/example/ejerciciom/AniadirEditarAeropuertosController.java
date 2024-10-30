package com.example.ejerciciom;


import DAO.DaoAeropuerto;
import DAO.DaoAeropuertoPrivado;
import DAO.DaoAeropuertoPublico;
import DAO.DaoDireccion;
import Model.AeropuertoModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import Model.AeropuertoPrivadoModel;
import Model.AeropuertoPublicoModel;
import Model.DireccionModel;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javax.sql.rowset.serial.SerialBlob;

/**
 * Clase controladora para añadir y editar aeropuertos en la aplicación.
 * Esta clase maneja las interacciones del usuario y la validación de datos
 * tanto para aeropuertos públicos como privados.
 */
public class AniadirEditarAeropuertosController implements Initializable {

    @FXML public Button btnGuardar;
    @FXML public Button btnCancelar;
    @FXML private Label lbFinanciacion;
    @FXML private Blob imagenBlob;

    @FXML private Label lbNumeroDeSocios;

    @FXML private Label lbNumeroDeTrabajadores;

    @FXML private RadioButton rbPrivado;

    public RadioButton getRbPublico() {return rbPublico;}

    public RadioButton getRbPrivado() {return rbPrivado;}

    @FXML private RadioButton rbPublico;

    @FXML private ToggleGroup rbTipoAeropuerto;

    @FXML private TextField tfAnioDeInauguracion;

    @FXML private TextField tfCalle;

    @FXML private TextField tfCapacidad;

    @FXML private TextField tfCiudad;

    @FXML private TextField tfFinanciacion;

    @FXML private TextField tfNombre;

    @FXML private TextField tfNumero;

    @FXML private TextField tfNumeroDeSocios;

    @FXML private TextField tfPais;

    @FXML private TextField tfTrabajadores;

    private TableView<AeropuertoPrivadoModel> tablaPriv;

    private TableView<AeropuertoPublicoModel> tablaPubli;


    /**
     * Cierra la ventana actual cuando se hace clic en el botón de cancelar.
     *
     * @param event El evento de acción que se desencadena al hacer clic en el botón de cancelar.
     */
    @FXML
    void cancelar(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Genera los campos de entrada según el tipo de aeropuerto seleccionado (público/privado).
     *
     * @param event El evento de acción que se desencadena al cambiar el tipo de aeropuerto.
     */
    @FXML
    void generarCampos(ActionEvent event) {
        if (rbPrivado.isSelected()) {
            // Mostrar campos para aeropuerto privado
            lbNumeroDeSocios.setVisible(true);
            tfNumeroDeSocios.setVisible(true);

            // Ocultar campos para aeropuerto público
            lbFinanciacion.setVisible(false);
            tfFinanciacion.setVisible(false);
            lbNumeroDeTrabajadores.setVisible(false);
            tfTrabajadores.setVisible(false);
        } else if (rbPublico.isSelected()) {
            // Mostrar campos para aeropuerto público
            lbFinanciacion.setVisible(true);
            tfFinanciacion.setVisible(true);
            lbNumeroDeTrabajadores.setVisible(true);
            tfTrabajadores.setVisible(true);

            // Ocultar campos para aeropuerto privado
            lbNumeroDeSocios.setVisible(false);
            tfNumeroDeSocios.setVisible(false);
        }
    }


    /**
     * Guarda los datos del aeropuerto después de validar los campos de entrada.
     *
     * @param event El evento de acción que se desencadena al hacer clic en el botón de guardar.
     */
    @FXML
    void guardar(ActionEvent event) {
        String error="";
        String nombre=tfNombre.getText();
        String pais=tfPais.getText();
        String ciudad=tfCiudad.getText();
        String calle=tfCalle.getText();
        int numero=-1;
        int anioInauguracion=-1;
        int capacidad=-1;
        boolean esPublico=rbPublico.isSelected();
        float financiacion=-1;
        int numTrabajadores=-1;
        int numSocios=-1;
        boolean existe=false;
        //Validacion
        error = validarStrings(error);
        if(tfNumero.getText().isEmpty()) {
            error+="El campo numero es obligatorio\n";
        }else {
            try {
                numero=Integer.parseInt(tfNumero.getText());
                if(numero<=0) {
                    throw new Exception();
                }
            }catch(NumberFormatException e) {
                error+="El numero de la calle debe ser un numero\n";
            } catch (Exception e) {
                error+="El numero de la calle no puede ser menor que 1\n";
            }
        }
        if(tfAnioDeInauguracion.getText().isEmpty()) {
            error+="El campo año de inauguracion es obligatorio\n";
        }else {
            try {
                anioInauguracion=Integer.parseInt(tfAnioDeInauguracion.getText());
                if(anioInauguracion<1900) {
                    throw new Exception();
                }
            }catch(NumberFormatException e) {
                error+="El año de inauguracion debe ser un numero\n";
            } catch (Exception e) {
                error+="El año de inauguracion no puede ser menor al 1900\n";
            }
        }
        if(tfCapacidad.getText().isEmpty()) {
            error+="El campo capacidad es obligatorio\n";
        }else {
            try {
                capacidad=Integer.parseInt(tfCapacidad.getText());
                if(capacidad<=0) {
                    throw new Exception();
                }
            }catch(NumberFormatException e) {
                error+="La capacidad debe ser un numero\n";
            } catch (Exception e) {
                error+="La capacidad no puede ser menor a 1\n";
            }
        }
        if(esPublico) {
            if(esPublico&&tfFinanciacion.getText().isEmpty()) {
                error+="El campo financiacion es obligatorio\n";
            }else {
                if(!tfFinanciacion.getText().matches("^\\d{1,10}(\\.\\d{1,2})?$")){
                    error+="La financiacion puede tener como mucho 10 digitos antes del punto y 2 despues\n";
                }else {
                    try {
                        financiacion=Float.parseFloat(tfFinanciacion.getText());
                        if(financiacion<=0) {
                            throw new Exception();
                        }
                    }catch(NumberFormatException e) {
                        error+="La financiacion debe ser un numero\n";
                    } catch (Exception e) {
                        error+="La financiacion no puede ser menor que 1\n";
                    }
                }
            }
        }
        if(esPublico) {
            if(tfTrabajadores.getText().isEmpty()) {
                error+="El campo numero de trabajadores es obligatorio\n";
            }else {
                try {
                    numTrabajadores=Integer.parseInt(tfTrabajadores.getText());
                    if(numTrabajadores<=0) {
                        throw new Exception();
                    }
                }catch(NumberFormatException e) {
                    error+="El numero de trabajadores debe ser un numero\n";
                } catch (Exception e) {
                    error+="El numero de trabajadores no puede ser menor que 1\n";
                }
            }
        }
        if(!esPublico) {
            if(tfNumeroDeSocios.getText().isEmpty()) {
                error+="El campo Nº socios es obligatorio\n";
            }else {
                try {
                    numSocios=Integer.parseInt(tfNumeroDeSocios.getText());
                    if(numSocios<=0) {
                        throw new Exception();
                    }
                }catch(NumberFormatException e) {
                    error+="El numero de socios debe ser un numero\n";
                } catch (Exception e) {
                    error+="El numero de socios no puede ser menor que 1\n";
                }
            }
        }
        //Una vez validado
        Alert al=new Alert(Alert.AlertType.INFORMATION);
        al.setHeaderText(null);
        if(ListaDeAeropuertosController.isEsAniadir()) {
            aniadirAeropuerto(error, nombre, pais, ciudad, calle, numero, anioInauguracion, capacidad, esPublico,
                    financiacion, numTrabajadores, numSocios, existe, al);
        }else {
            modificarAeropuerto(error, nombre, pais, ciudad, calle, numero, anioInauguracion, capacidad, esPublico,
                    financiacion, numTrabajadores, numSocios, existe, al);
        }
        al.showAndWait();
        tablaPriv.getSelectionModel().clearSelection();
        tablaPubli.getSelectionModel().clearSelection();
        ListaDeAeropuertosController.getS().close();
    }
    /**
     * Modifica un aeropuerto existente basado en los datos proporcionados.
     *
     * @param error             La cadena de mensaje de error para acumular errores de validación.
     * @param nombre            El nombre del aeropuerto.
     * @param pais              El país del aeropuerto.
     * @param ciudad            La ciudad del aeropuerto.
     * @param calle             La calle del aeropuerto.
     * @param numero            El número de la calle del aeropuerto.
     * @param anioInauguracion  El año en que se inauguró el aeropuerto.
     * @param capacidad         La capacidad del aeropuerto.
     * @param esPublico         Un booleano que indica si el aeropuerto es público.
     * @param financiacion      La cantidad de financiación para aeropuertos públicos.
     * @param numTrabajadores   El número de trabajadores para aeropuertos públicos.
     * @param numSocios        El número de socios para aeropuertos privados.
     * @param existe            Un booleano que indica si el aeropuerto ya existe.
     * @param al                El diálogo de alerta para mostrar mensajes al usuario.
     */
    void modificarAeropuerto(String error, String nombre, String pais, String ciudad, String calle, int numero,
                             int anioInauguracion, int capacidad, boolean esPublico, float financiacion, int numTrabajadores,
                             int numSocios, boolean existe, Alert al) {
        existe = validarExistencia(nombre, pais, ciudad, calle, numero, anioInauguracion, capacidad, esPublico,
                financiacion, numTrabajadores, numSocios, existe);
        if(!existe&&error.equals("")) {
            Integer idDireccion=DaoDireccion.conseguirID(pais, ciudad, calle, numero);
            if(idDireccion==null) {
                DaoDireccion.aniadir(pais, ciudad, calle, numero);
                idDireccion=DaoDireccion.conseguirID(pais, ciudad, calle, numero);
            }
            Integer idAeropuerto=DaoAeropuerto.conseguirID(nombre, anioInauguracion, capacidad, idDireccion, null);
            if(idAeropuerto==null) {
                if(esPublico) {
                    DaoAeropuerto.modificarPorId(tablaPubli.getSelectionModel().getSelectedItem().getId(), nombre, anioInauguracion, capacidad, idDireccion, imagenBlob);
                }else {
                    DaoAeropuerto.modificarPorId(tablaPriv.getSelectionModel().getSelectedItem().getId(), nombre, anioInauguracion, capacidad, idDireccion, imagenBlob);
                }
                idAeropuerto=DaoAeropuerto.conseguirID(nombre, anioInauguracion, capacidad,idDireccion, imagenBlob);
            }
            if(esPublico) {
                DaoAeropuertoPublico.modificarPorID(idAeropuerto, financiacion, numTrabajadores);
                ListaDeAeropuertosController.setListaTodasPublico(DaoAeropuertoPublico.cargarListaAeropuertosPublicos());
                tablaPubli.refresh();
            }else {
                DaoAeropuertoPrivado.modificarPorID(idAeropuerto, numSocios);
                ListaDeAeropuertosController.setListaTodasPrivado(DaoAeropuertoPrivado.cargarListaAeropuertosPrivados());
                tablaPriv.refresh();
            }
            al.setContentText("Aeropuerto modificado correctamente");
        }else {
            if(error.equals("")) {
                al.setAlertType(AlertType.WARNING);
                error="La persona ya estaba en la lista";
            }else {
                al.setAlertType(AlertType.ERROR);
            }
            al.setContentText(error);
        }
    }
    /**
     * Añade un nuevo aeropuerto basado en los datos proporcionados.
     *
     * @param error             La cadena de mensaje de error para acumular errores de validación.
     * @param nombre            El nombre del aeropuerto.
     * @param pais              El país del aeropuerto.
     * @param ciudad            La ciudad del aeropuerto.
     * @param calle             La calle del aeropuerto.
     * @param numero            El número de la calle del aeropuerto.
     * @param anioInauguracion  El año en que se inauguró el aeropuerto.
     * @param capacidad         La capacidad del aeropuerto.
     * @param esPublico         Un booleano que indica si el aeropuerto es público.
     * @param financiacion      La cantidad de financiación para aeropuertos públicos.
     * @param numTrabajadores   El número de trabajadores para aeropuertos públicos.
     * @param numSocios        El número de socios para aeropuertos privados.
     * @param existe            Un booleano que indica si el aeropuerto ya existe.
     * @param al                El diálogo de alerta para mostrar mensajes al usuario.
     */
    void aniadirAeropuerto(String error, String nombre, String pais, String ciudad, String calle, int numero,
                           int anioInauguracion, int capacidad, boolean esPublico, float financiacion, int numTrabajadores,
                           int numSocios, boolean existe, Alert al) {
        existe = validarExistencia(nombre, pais, ciudad, calle, numero, anioInauguracion, capacidad, esPublico,
                financiacion, numTrabajadores, numSocios, existe);
        if(error.equals("")&&!existe) {
            Integer idDireccion=DaoDireccion.conseguirID(pais, ciudad, calle, numero);
            if(idDireccion==null) {
                DaoDireccion.aniadir(pais, ciudad, calle, numero);
                idDireccion=DaoDireccion.conseguirID(pais, ciudad, calle, numero);
            }
            Integer idAeropuerto=DaoAeropuerto.conseguirID(nombre, anioInauguracion, capacidad, idDireccion, null);
            if(idAeropuerto==null) {
                DaoAeropuerto.aniadir(nombre, anioInauguracion, capacidad, idDireccion, null);
                idAeropuerto=DaoAeropuerto.conseguirID(nombre, anioInauguracion, capacidad,idDireccion, null);
            }
            if(esPublico) {
                DaoAeropuertoPublico.aniadir(idAeropuerto, financiacion, numTrabajadores);
                ListaDeAeropuertosController.setListaTodasPublico(DaoAeropuertoPublico.cargarListaAeropuertosPublicos());
                tablaPubli.refresh();
            }else {
                DaoAeropuertoPrivado.aniadir(idAeropuerto, numSocios);
                ListaDeAeropuertosController.setListaTodasPrivado(DaoAeropuertoPrivado.cargarListaAeropuertosPrivados());
                tablaPriv.refresh();
            }
            al.setContentText("Aeropuerto añadido correctamente");
        }else {
            if(error.equals("")) {
                al.setAlertType(AlertType.WARNING);
                error="La persona ya estaba en la lista";
            }else {
                al.setAlertType(AlertType.ERROR);
            }
            al.setContentText(error);
        }
    }

    /**
     * Valida si un aeropuerto ya existe en la lista de aeropuertos.
     *
     * Este método comprueba si un aeropuerto, dado sus atributos, ya está presente
     * en la lista de aeropuertos públicos o privados. Se crea una instancia del aeropuerto
     * según el tipo (público o privado) y se compara con los existentes en las listas correspondientes.
     *
     * @param nombre            El nombre del aeropuerto.
     * @param pais              El país del aeropuerto.
     * @param ciudad            La ciudad del aeropuerto.
     * @param calle             La calle del aeropuerto.
     * @param numero            El número de la calle del aeropuerto.
     * @param anioInauguracion  El año en que se inauguró el aeropuerto.
     * @param capacidad         La capacidad del aeropuerto.
     * @param esPublico         Un booleano que indica si el aeropuerto es público.
     * @param financiacion      La cantidad de financiación para aeropuertos públicos.
     * @param numTrabajadores   El número de trabajadores para aeropuertos públicos.
     * @param numSocios        El número de socios para aeropuertos privados.
     * @param existe            Un booleano que indica si el aeropuerto ya existe (se pasa por referencia).
     * @return true si el aeropuerto ya existe en la lista; false en caso contrario.
     */
    boolean validarExistencia(String nombre, String pais, String ciudad, String calle, int numero, int anioInauguracion,
                              int capacidad, boolean esPublico, float financiacion, int numTrabajadores, int numSocios, boolean existe) {
        if(esPublico) {
            AeropuertoPublicoModel aeropuerto=new AeropuertoPublicoModel(nombre, anioInauguracion, capacidad, new DireccionModel(pais, ciudad, calle, numero), null, financiacion, numTrabajadores);
            for(AeropuertoPublicoModel airport:ListaDeAeropuertosController.getListaTodasPublico()) {
                if(airport.equals(aeropuerto)) {
                    existe=true;
                }
            }
        }else {
            AeropuertoPrivadoModel aeropuerto=new AeropuertoPrivadoModel(nombre, anioInauguracion, capacidad, new DireccionModel(pais, ciudad, calle, numero), null, numSocios);
            for(AeropuertoPrivadoModel airport:ListaDeAeropuertosController.getListaTodasPrivado()) {
                if(airport.equals(aeropuerto)) {
                    existe=true;
                }
            }
        }
        return existe;
    }

    /**
     * Valida los campos de tipo cadena para valores vacíos.
     *
     * @param error La cadena de mensaje de error para acumular errores de validación.
     * @return La cadena de mensaje de error actualizada.
     */
    String validarStrings(String error) {
        if(tfNombre.getText().isEmpty()) {
            error+="El campo nombre es obligatorio\n";
        }
        if(tfPais.getText().isEmpty()) {
            error+="El campo pais es obligatorio\n";
        }
        if(tfCiudad.getText().isEmpty()) {
            error+="El campo ciudad es obligatorio\n";
        }
        if(tfCalle.getText().isEmpty()) {
            error+="El campo calle es obligatorio\n";
        }
        return error;
    }

    /**
     * Establece la tabla para los aeropuertos privados.
     *
     * @param idTablaPrivado La tabla de aeropuertos privados a establecer.
     */
    public void setTablaPriv(TableView<AeropuertoPrivadoModel> idTablaPrivado) {
        this.tablaPriv = idTablaPrivado;
    }

    /**
     * Establece la tabla para los aeropuertos públicos.
     *
     * @param idTablaPublico La tabla de aeropuertos públicos a establecer.
     */
    public void setTablaPubli(TableView<AeropuertoPublicoModel> idTablaPublico) {
        this.tablaPubli = idTablaPublico;
    }

    /**
     * Establece el texto del campo de la calle.
     *
     * @param txtCalle El nuevo texto para el campo de la calle.
     */
    public void setTxtCalleText(String txtCalle) {
        this.tfCalle.setText(txtCalle);
    }

    /**
     * Establece el texto del campo de capacidad.
     *
     * @param txtCapacidad El nuevo texto para el campo de capacidad.
     */
    public void setTxtCapacidadText(String txtCapacidad) {
        this.tfCapacidad.setText(txtCapacidad);
    }

    /**
     * Establece el texto del campo de ciudad.
     *
     * @param txtCiudad El nuevo texto para el campo de ciudad.
     */
    public void setTxtCiudadText(String txtCiudad) {
        this.tfCiudad.setText(txtCiudad);
    }

    /**
     * Establece el texto del campo de financiación.
     *
     * @param txtFinanciacion El nuevo texto para el campo de financiación.
     */
    public void setTxtFinanciacionText(String txtFinanciacion) {
        this.tfFinanciacion.setText(txtFinanciacion);
    }

    /**
     * Establece el texto del campo de nombre.
     *
     * @param txtNombre El nuevo texto para el campo de nombre.
     */
    public void setTxtNombreText(String txtNombre) {
        this.tfNombre.setText(txtNombre);
    }

    /**
     * Establece el texto del campo de número de socios.
     *
     * @param txtNumSocios El nuevo texto para el campo de número de socios.
     */
    public void setTxtNumSociosText(String txtNumSocios) {
        this.tfNumeroDeSocios.setText(txtNumSocios);
    }

    /**
     * Establece el texto del campo de número de trabajadores.
     *
     * @param txtNumTrabajadores El nuevo texto para el campo de número de trabajadores.
     */
    public void setTxtNumTrabajadoresText(String txtNumTrabajadores) {
        this.tfTrabajadores.setText(txtNumTrabajadores);
    }

    /**
     * Establece el texto del campo de número.
     *
     * @param txtNumero El nuevo texto para el campo de número.
     */
    public void setTxtNumeroText(String txtNumero) {
        this.tfNumero.setText(txtNumero);
    }

    /**
     * Establece el texto del campo de país.
     *
     * @param txtPais El nuevo texto para el campo de país.
     */
    public void setTxtPaisText(String txtPais) {
        this.tfPais.setText(txtPais);
    }

    /**
     * Establece el texto del campo de año de inauguración.
     *
     * @param txtAnioInauguracion El nuevo texto para el campo de año de inauguración.
     */
    public void setTxtAnioInauguracionText(String txtAnioInauguracion) {
        this.tfAnioDeInauguracion.setText(txtAnioInauguracion);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (btnGuardar != null) {
            btnGuardar.setDefaultButton(true); // Establecer el botón Guardar como predeterminado
        }
        if (btnCancelar != null) {
            btnCancelar.setCancelButton(true); // Establecer el botón Cancelar como botón de tipo cancel
        }
    }


    @FXML
    void cargarImagen(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Imagen del Aeropuerto");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.gif")
        );

        File file = fileChooser.showOpenDialog(null); // Cambia null por la ventana principal si es necesario
        if (file != null) {
            try (FileInputStream fis = new FileInputStream(file)) {
                // Leer todos los bytes del InputStream y crear un Blob
                byte[] imageBytes = fis.readAllBytes();
                imagenBlob = new SerialBlob(imageBytes); // Asignar el Blob a la variable local

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Imagen cargada correctamente");
                alert.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
                // Manejo de errores
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error al cargar la imagen");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            } catch (SQLException e) {
                e.printStackTrace();
                // Manejo de errores para la base de datos
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error al crear el Blob");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }
}
