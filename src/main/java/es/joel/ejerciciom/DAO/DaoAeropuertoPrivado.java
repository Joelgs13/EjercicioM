package es.joel.ejerciciom.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import es.joel.ejerciciom.BBDD.ConexionBBDD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import es.joel.ejerciciom.Model.AeropuertoPrivadoModel;

/**
 * Clase de acceso a datos para la entidad Aeropuerto Privado.
 * Proporciona métodos para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar)
 * en la base de datos relacionada con los aeropuertos privados.
 */
public class DaoAeropuertoPrivado {

    /** La conexión a la base de datos. */
    private static Connection conection;

    /**
     * Carga una lista de aeropuertos privados desde la base de datos.
     *
     * @return una lista observable de modelos de aeropuertos privados
     */
    public static ObservableList<AeropuertoPrivadoModel> cargarListaAeropuertosPrivados(){
        ObservableList<AeropuertoPrivadoModel> lst = FXCollections.observableArrayList();
        try {
            conection = ConexionBBDD.getConnection();
            String select = "SELECT id, numero_socios, nombre, anio_inauguracion, capacidad, id_direccion, imagen FROM aeropuertos_privados, aeropuertos WHERE id = id_aeropuerto";
            PreparedStatement pstmt = conection.prepareStatement(select);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                AeropuertoPrivadoModel modelo = new AeropuertoPrivadoModel(rs.getString("nombre"), rs.getInt("anio_inauguracion"), rs.getInt("capacidad"), DaoDireccion.crearModeloDireccionPorID(rs.getInt("id_direccion")), rs.getBlob("imagen"), rs.getInt("numero_socios"));
                modelo.setId(rs.getInt("id"));
                lst.add(modelo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lst;
    }

    /**
     * Inserta un nuevo aeropuerto privado en la base de datos.
     *
     * @param aeropuertoPrivado el modelo del aeropuerto privado a insertar
     * @throws SQLException si hay un error durante la inserción en la base de datos
     */
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

    /**
     * Añade un nuevo registro de aeropuerto privado asociado a un ID de aeropuerto existente.
     *
     * @param idAeropuerto el ID del aeropuerto
     * @param numSocios    el número de socios del aeropuerto
     */
    public static void aniadir(int idAeropuerto, int numSocios) {
        conection = ConexionBBDD.getConnection();
        String insert = "INSERT INTO aeropuertos_privados VALUES (?, ?)";
        try {
            PreparedStatement pstmt;
            pstmt = conection.prepareStatement(insert);
            pstmt.setInt(1, idAeropuerto);
            pstmt.setInt(2, numSocios);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Modifica el número de socios de un aeropuerto privado existente.
     *
     * @param id       el ID del aeropuerto privado a modificar
     * @param numSocios el nuevo número de socios
     */
    public static void modificarPorID(int id, int numSocios) {
        conection = ConexionBBDD.getConnection();
        String update = "UPDATE aeropuertos_privados SET numero_socios = ? WHERE id_aeropuerto = ?";
        try {
            PreparedStatement pstmt;
            pstmt = conection.prepareStatement(update);
            pstmt.setInt(1, numSocios);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina un aeropuerto privado de la base de datos basado en su ID.
     *
     * @param id el ID del aeropuerto privado a eliminar
     */
    public static void eliminar(int id) {
        conection = ConexionBBDD.getConnection();
        String delete = "DELETE FROM aeropuertos_privados WHERE id_aeropuerto = ?";
        try {
            PreparedStatement pstmt = conection.prepareStatement(delete);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
