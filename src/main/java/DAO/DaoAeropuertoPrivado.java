package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import BBDD.ConexionBBDD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Model.AeropuertoPrivadoModel;

/**
 * Clase DaoAeropuertoPrivado.
 */
public class DaoAeropuertoPrivado {

    /** EL conection. */
    private static Connection conection;

    /**
     * Cargar lista aeropuertos privados.
     *
     * @return the observable list
     */
    public static ObservableList<AeropuertoPrivadoModel> cargarListaAeropuertosPrivados(){
        ObservableList<AeropuertoPrivadoModel>lst=FXCollections.observableArrayList();
        try {
            conection=ConexionBBDD.getConnection();
            String select="SELECT id,numero_socios,nombre,anio_inauguracion,capacidad,id_direccion,imagen FROM aeropuertos_privados,aeropuertos WHERE id=id_aeropuerto";
            PreparedStatement pstmt = conection.prepareStatement(select);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                AeropuertoPrivadoModel modelo=new AeropuertoPrivadoModel(rs.getString("nombre"),rs.getInt("anio_inauguracion"),rs.getInt("capacidad"), DaoDireccion.crearModeloDireccionPorID(rs.getInt("id_direccion")), rs.getBlob("imagen"),rs.getInt("numero_socios"));
                modelo.setId(rs.getInt("id"));
                lst.add(modelo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lst;
    }

    public static void insertarAeropuertoPrivado(AeropuertoPrivadoModel aeropuertoPrivado) throws SQLException {
        String sql = "INSERT INTO aeropuertos_privados (nombre, calle, ciudad, pais, numero, anio_inauguracion, capacidad, num_socios) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBBDD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Configurar los parámetros de la consulta con los valores del objeto
            stmt.setString(1, aeropuertoPrivado.getNombre());
            stmt.setString(2, aeropuertoPrivado.getDireccion().getCalle());
            stmt.setString(3, aeropuertoPrivado.getDireccion().getCiudad());
            stmt.setString(4, aeropuertoPrivado.getDireccion().getPais());
            stmt.setInt(5, aeropuertoPrivado.getDireccion().getNumero());
            stmt.setInt(6, aeropuertoPrivado.getAnioInauguracion());
            stmt.setInt(7, aeropuertoPrivado.getCapacidad());
            stmt.setInt(8, aeropuertoPrivado.getNumSocios());

            // Ejecutar la inserción
            stmt.executeUpdate();
        }
    }

    public static void aniadir(int idAeropuerto,int numSocios) {
        conection=ConexionBBDD.getConnection();
        String insert="INSERT INTO aeropuertos_privados VALUES (?,?)";
        try {
            PreparedStatement pstmt;
            pstmt=conection.prepareStatement(insert);
            pstmt.setInt(1,idAeropuerto);
            pstmt.setInt(2,numSocios);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}