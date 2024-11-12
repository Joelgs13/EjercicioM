package es.joel.ejerciciom.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import es.joel.ejerciciom.BBDD.ConexionBBDD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import es.joel.ejerciciom.Model.AeropuertoPublicoModel;

/**
 * Clase de acceso a datos para la entidad Aeropuerto Público.
 * Proporciona métodos para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar)
 * en la base de datos relacionada con los aeropuertos públicos.
 */
public class DaoAeropuertoPublico {

    /** La conexión a la base de datos. */
    private static Connection conection;

    /**
     * Carga una lista de aeropuertos públicos desde la base de datos.
     *
     * @return una lista observable de modelos de aeropuertos públicos
     */
    public static ObservableList<AeropuertoPublicoModel> cargarListaAeropuertosPublicos(){
        ObservableList<AeropuertoPublicoModel> lst = FXCollections.observableArrayList();
        try {
            conection = ConexionBBDD.getConnection();
            String select = "SELECT id, financiacion, num_trabajadores, nombre, anio_inauguracion, capacidad, id_direccion, imagen FROM aeropuertos_publicos, aeropuertos WHERE id = id_aeropuerto";
            PreparedStatement pstmt = conection.prepareStatement(select);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                AeropuertoPublicoModel modelo = new AeropuertoPublicoModel(rs.getString("nombre"), rs.getInt("anio_inauguracion"), rs.getInt("capacidad"), DaoDireccion.crearModeloDireccionPorID(rs.getInt("id_direccion")), rs.getBlob("imagen"), rs.getFloat("financiacion"), rs.getInt("num_trabajadores"));
                modelo.setId(rs.getInt("id"));
                lst.add(modelo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lst;
    }

    /**
     * Inserta un nuevo aeropuerto público en la base de datos.
     *
     * @param aeropuertoPublico el modelo del aeropuerto público a insertar
     * @throws SQLException si hay un error durante la inserción en la base de datos
     */
    public static void insertarAeropuertoPublico(AeropuertoPublicoModel aeropuertoPublico) throws SQLException {
        String sql = "INSERT INTO aeropuertos_publicos (nombre, calle, ciudad, pais, numero, anio_inauguracion, capacidad, num_trabajadores, financiacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBBDD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Configurar los parámetros de la consulta con los valores del objeto
            stmt.setString(1, aeropuertoPublico.getNombre());
            stmt.setString(2, aeropuertoPublico.getDireccion().getCalle());
            stmt.setString(3, aeropuertoPublico.getDireccion().getCiudad());
            stmt.setString(4, aeropuertoPublico.getDireccion().getPais());
            stmt.setInt(5, aeropuertoPublico.getDireccion().getNumero());
            stmt.setInt(6, aeropuertoPublico.getAnioInauguracion());
            stmt.setInt(7, aeropuertoPublico.getCapacidad());
            stmt.setInt(8, aeropuertoPublico.getNumTrabajadores());
            stmt.setDouble(9, aeropuertoPublico.getFinanciacion());

            // Ejecutar la inserción
            stmt.executeUpdate();
        }
    }

    /**
     * Añade un nuevo registro de aeropuerto público asociado a un ID de aeropuerto existente.
     *
     * @param idAeropuerto el ID del aeropuerto
     * @param financiacion la financiación del aeropuerto público
     * @param numTrabajadoes el número de trabajadores del aeropuerto
     */
    public static void aniadir(int idAeropuerto, float financiacion, int numTrabajadoes) {
        conection = ConexionBBDD.getConnection();
        String insert = "INSERT INTO aeropuertos_publicos VALUES (?, ?, ?)";
        try {
            PreparedStatement pstmt;
            pstmt = conection.prepareStatement(insert);
            pstmt.setInt(1, idAeropuerto);
            pstmt.setFloat(2, financiacion);
            pstmt.setInt(3, numTrabajadoes);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Modifica la financiación y el número de trabajadores de un aeropuerto público existente.
     *
     * @param id             el ID del aeropuerto público a modificar
     * @param financiacion   la nueva financiación del aeropuerto público
     * @param numTrabajadores el nuevo número de trabajadores
     */
    public static void modificarPorID(int id, float financiacion, int numTrabajadores) {
        conection = ConexionBBDD.getConnection();
        String update = "UPDATE aeropuertos_publicos SET financiacion = ?, num_trabajadores = ? WHERE id_aeropuerto = ?";
        try {
            PreparedStatement pstmt;
            pstmt = conection.prepareStatement(update);
            pstmt.setFloat(1, financiacion);
            pstmt.setInt(2, numTrabajadores);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina un aeropuerto público de la base de datos basado en su ID.
     *
     * @param id el ID del aeropuerto público a eliminar
     */
    public static void eliminar(int id) {
        conection = ConexionBBDD.getConnection();
        String delete = "DELETE FROM aeropuertos_publicos WHERE id_aeropuerto = ?";
        try {
            PreparedStatement pstmt = conection.prepareStatement(delete);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
