package com.example.ejerciciol;


import DAO.DaoAeropuerto;
import DAO.DaoAeropuertoPrivado;
import DAO.DaoAeropuertoPublico;
import DAO.DaoDireccion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import Model.AeropuertoPrivadoModel;
import Model.AeropuertoPublicoModel;
import Model.DireccionModel;
import javafx.stage.Stage;

public class AniadirEditarAeropuertosController {

    @FXML
    private Label lbFinanciacion;

    @FXML
    private Label lbNumeroDeSocios;

    @FXML
    private Label lbNumeroDeTrabajadores;

    @FXML
    private RadioButton rbPrivado;

    @FXML
    private RadioButton rbPublico;

    @FXML
    private ToggleGroup rbTipoAeropuerto;

    @FXML
    private TextField tfAnioDeInauguracion;

    @FXML
    private TextField tfCalle;

    @FXML
    private TextField tfCapacidad;

    @FXML
    private TextField tfCiudad;

    @FXML
    private TextField tfFinanciacion;

    @FXML
    private TextField tfNombre;

    @FXML
    private TextField tfNumero;

    @FXML
    private TextField tfNumeroDeSocios;

    @FXML
    private TextField tfPais;

    @FXML
    private TextField tfTrabajadores;

    private TableView<AeropuertoPrivadoModel> tablaPriv;

    private TableView<AeropuertoPublicoModel> tablaPubli;

    @FXML
    void cancelar(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    // Método para mostrar/ocultar campos según el tipo de aeropuerto seleccionado
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
                    DaoAeropuerto.modificarPorId(tablaPubli.getSelectionModel().getSelectedItem().getId(), nombre, anioInauguracion, capacidad, idDireccion, null);
                }else {
                    DaoAeropuerto.modificarPorId(tablaPriv.getSelectionModel().getSelectedItem().getId(), nombre, anioInauguracion, capacidad, idDireccion, null);
                }
                idAeropuerto=DaoAeropuerto.conseguirID(nombre, anioInauguracion, capacidad,idDireccion, null);
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
     * Validar strings.
     *
     * @param error the error
     * @return the string
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

    public void setTablaPriv(TableView<AeropuertoPrivadoModel> idTablaPrivado) {
        this.tablaPriv = idTablaPrivado;
    }

    public void setTablaPubli(TableView<AeropuertoPublicoModel> idTablaPublico) {
        this.tablaPubli = idTablaPublico;
    }

    /**
     * Sets the txt calle text.
     *
     * @param txtCalle the new txt calle text
     */
    public void setTxtCalleText(String txtCalle) {
        this.tfCalle.setText(txtCalle);
    }

    /**
     * Sets the txt capacidad text.
     *
     * @param txtCapacidad the new txt capacidad text
     */
    public void setTxtCapacidadText(String txtCapacidad) {
        this.tfCapacidad.setText(txtCapacidad);
    }

    /**
     * Sets the txt ciudad text.
     *
     * @param txtCiudad the new txt ciudad text
     */
    public void setTxtCiudadText(String txtCiudad) {
        this.tfCiudad.setText(txtCiudad);
    }

    /**
     * Sets the txt financiacion text.
     *
     * @param txtFinanciacion the new txt financiacion text
     */
    public void setTxtFinanciacionText(String txtFinanciacion) {
        this.tfFinanciacion.setText(txtFinanciacion);
    }

    /**
     * Sets the txt nombre text.
     *
     * @param txtNombre the new txt nombre text
     */
    public void setTxtNombreText(String txtNombre) {
        this.tfNombre.setText(txtNombre);
    }

    /**
     * Sets the txt num socios text.
     *
     * @param txtNumSocios the new txt num socios text
     */
    public void setTxtNumSociosText(String txtNumSocios) {
        this.tfNumeroDeSocios.setText(txtNumSocios);
    }

    /**
     * Sets the txt num trabajadores text.
     *
     * @param txtNumTrabajadores the new txt num trabajadores text
     */
    public void setTxtNumTrabajadoresText(String txtNumTrabajadores) {
        this.tfTrabajadores.setText(txtNumTrabajadores);
    }

    /**
     * Sets the txt numero text.
     *
     * @param txtNumero the new txt numero text
     */
    public void setTxtNumeroText(String txtNumero) {
        this.tfNumero.setText(txtNumero);
    }

    /**
     * Sets the txt pais text.
     *
     * @param txtPais the new txt pais text
     */
    public void setTxtPaisText(String txtPais) {
        this.tfPais.setText(txtPais);
    }

    /**
     * Sets the txt anio inauguracion text.
     *
     * @param txtAnioInauguracion the new txt anio inauguracion text
     */
    public void setTxtAnioInauguracionText(String txtAnioInauguracion) {
        this.tfAnioDeInauguracion.setText(txtAnioInauguracion);
    }
}
