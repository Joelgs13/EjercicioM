package es.joel.ejerciciom.DAO;

import java.sql.*;

import es.joel.ejerciciom.BBDD.ConexionBBDD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import es.joel.ejerciciom.Model.AeropuertoModel;

/**
 * Clase de acceso a datos para la entidad Aeropuerto. Proporciona métodos para
 * realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) en la base de datos
 * relacionada con los aeropuertos.
 */
public class DaoAeropuerto {

    private static Connection connection;

    public DaoAeropuerto() throws SQLException {
    }

    /**
     * Inserta un nuevo aeropuerto en la base de datos.
     *
     * @param nombre             el nombre del aeropuerto
     * @param direccionId       el ID de la dirección asociada al aeropuerto
     * @param anioInauguracion  el año de inauguración del aeropuerto
     * @param capacidad         la capacidad del aeropuerto
     * @param trabajadores       el número de trabajadores del aeropuerto
     * @param financiacion       la financiación del aeropuerto
     * @return el ID del nuevo aeropuerto insertado
     * @throws SQLException si hay un error durante la inserción en la base de datos
     */
    public static int insertarAeropuerto(String nombre, int direccionId, int anioInauguracion, int capacidad, int trabajadores, double financiacion) throws SQLException {
        String sql = "INSERT INTO aeropuertos (nombre, direccion_id, anio_inauguracion, capacidad, num_trabajadores, financiacion) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBBDD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombre);
            pstmt.setInt(2, direccionId);
            pstmt.setInt(3, anioInauguracion);
            pstmt.setInt(4, capacidad);
            pstmt.setInt(5, trabajadores);
            pstmt.setDouble(6, financiacion);

            pstmt.executeUpdate();

            // Obtener el ID generado
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Devuelve el ID del aeropuerto
                } else {
                    throw new SQLException("No se pudo obtener el ID del aeropuerto.");
                }
            }
        }
    }
    public static void agregarAeropuerto(AeropuertoModel aeropuerto) {

        String sql = "INSERT INTO aeropuerto (nombre, direccion, imagen) VALUES (?, ?, ?)";
        try (Connection connection = ConexionBBDD.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, aeropuerto.getNombre());
            ps.setString(2, aeropuerto.getDireccion().toString()); // Ajusta según sea necesario
            ps.setBlob(3, aeropuerto.getImagen()); // Guardar imagen
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejo de errores
        }
    }


    /**
     * Carga una lista de todos los aeropuertos, tanto privados como públicos.
     *
     * @return una lista observable de modelos de aeropuertos
     */
    public static ObservableList<AeropuertoModel> listaTodas() {
        ObservableList<AeropuertoModel> lst = FXCollections.observableArrayList();
        lst.addAll(DaoAeropuertoPrivado.cargarListaAeropuertosPrivados());
        lst.addAll(DaoAeropuertoPublico.cargarListaAeropuertosPublicos());
        return lst;
    }

    /**
     * Inserta un nuevo aeropuerto en la base de datos con los detalles especificados.
     *
     * @param nombre             el nombre del aeropuerto
     * @param anioInauguracion  el año de inauguración del aeropuerto
     * @param capacidad         la capacidad del aeropuerto
     * @param idDireccion       el ID de la dirección asociada al aeropuerto
     * @param imagen            la imagen del aeropuerto en forma de Blob
     */
    public static void aniadir(String nombre, int anioInauguracion, int capacidad, int idDireccion, Blob imagen) {
        connection = ConexionBBDD.getConnection();
        String insert = "INSERT INTO aeropuertos (nombre, anio_inauguracion, capacidad, id_direccion, imagen) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstmt;
            pstmt = connection.prepareStatement(insert);
            pstmt.setString(1, nombre);
            pstmt.setInt(2, anioInauguracion);
            pstmt.setInt(3, capacidad);
            pstmt.setInt(4, idDireccion);
            pstmt.setBlob(5, imagen);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtiene el ID de un aeropuerto específico a partir de sus atributos.
     *
     * @param nombre             el nombre del aeropuerto
     * @param anioInauguracion  el año de inauguración del aeropuerto
     * @param capacidad         la capacidad del aeropuerto
     * @param idDireccion       el ID de la dirección asociada al aeropuerto
     * @param imagen            la imagen del aeropuerto en forma de Blob
     * @return el ID del aeropuerto, o null si no se encuentra
     */
    public static Integer conseguirID(String nombre, int anioInauguracion, int capacidad, int idDireccion, Blob imagen) {
        connection = ConexionBBDD.getConnection();
        String select = "SELECT id FROM aeropuertos WHERE nombre=? AND anio_inauguracion=? AND capacidad=? AND id_direccion=?";
        try {
            PreparedStatement pstmt;
            pstmt = connection.prepareStatement(select);
            pstmt.setString(1, nombre);
            pstmt.setInt(2, anioInauguracion);
            pstmt.setInt(3, capacidad);
            pstmt.setInt(4, idDireccion);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Modifica los detalles de un aeropuerto existente en la base de datos.
     *
     * @param id                el ID del aeropuerto a modificar
     * @param nombre            el nuevo nombre del aeropuerto
     * @param anioInauguracion  el nuevo año de inauguración del aeropuerto
     * @param capacidad         la nueva capacidad del aeropuerto
     * @param idDireccion       el nuevo ID de la dirección asociada al aeropuerto
     * @param imagen            la nueva imagen del aeropuerto en forma de Blob
     */
    public static void modificarPorId(int id, String nombre, int anioInauguracion, int capacidad, int idDireccion, Blob imagen) {
        connection = ConexionBBDD.getConnection();
        String update = "UPDATE aeropuertos SET nombre=?, anio_inauguracion=?, capacidad=?, id_direccion=?, imagen=? WHERE id=?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(update);
            pstmt.setString(1, nombre);
            pstmt.setInt(2, anioInauguracion);
            pstmt.setInt(3, capacidad);
            pstmt.setInt(4, idDireccion);
            pstmt.setBlob(5, imagen);
            pstmt.setInt(6, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina un aeropuerto de la base de datos basado en su ID.
     *
     * @param id el ID del aeropuerto a eliminar
     */
    public static void eliminar(int id) {
        connection = ConexionBBDD.getConnection();
        String delete = "DELETE FROM aeropuertos WHERE id=?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(delete);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
