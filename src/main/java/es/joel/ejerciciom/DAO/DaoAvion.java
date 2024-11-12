package es.joel.ejerciciom.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import es.joel.ejerciciom.BBDD.ConexionBBDD;
import es.joel.ejerciciom.Model.AvionModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Clase de acceso a datos para la entidad Avión.
 * Proporciona métodos para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar)
 * en la base de datos relacionadas con los aviones.
 */
public class DaoAvion {
    /** La conexión a la base de datos. */
    private static Connection conection;

    /**
     * Carga una lista de aviones asociados a un aeropuerto específico.
     *
     * @param id el ID del aeropuerto del cual se desean cargar los aviones
     * @return una lista observable de modelos de aviones
     */
    public static ObservableList<AvionModel> listaAviones(int id) {
        conection = ConexionBBDD.getConnection();
        String select = "SELECT modelo, numero_asientos, velocidad_maxima, activado FROM aviones WHERE id_aeropuerto=?";
        ObservableList<AvionModel> lst = FXCollections.observableArrayList();
        try {
            PreparedStatement pstmt = conection.prepareStatement(select);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                AvionModel avion = new AvionModel(rs.getString("modelo"), rs.getInt("numero_asientos"), rs.getInt("velocidad_maxima"), rs.getInt("activado") == 1, id);
                lst.add(avion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lst;
    }

    /**
     * Obtiene una lista de todos los aviones en la base de datos.
     *
     * @return una lista de todos los modelos de aviones
     */
    public static ArrayList<AvionModel> conseguirListaTodos() {
        ArrayList<AvionModel> lst = new ArrayList<AvionModel>();
        conection = ConexionBBDD.getConnection();
        String select = "SELECT modelo, numero_asientos, velocidad_maxima, activado, id_aeropuerto FROM aviones";
        try {
            PreparedStatement pstmt = conection.prepareStatement(select);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                AvionModel avion = new AvionModel(rs.getString("modelo"), rs.getInt("numero_asientos"), rs.getInt("velocidad_maxima"), rs.getInt("activado") == 1, rs.getInt("id_aeropuerto"));
                lst.add(avion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lst;
    }

    /**
     * Añade un nuevo avión a la base de datos.
     *
     * @param modelo      el modelo del avión
     * @param numAsientos el número de asientos del avión
     * @param velMax     la velocidad máxima del avión
     * @param activado   indica si el avión está activado
     * @param idAeropuerto el ID del aeropuerto al que pertenece el avión
     */
    public static void aniadir(String modelo, int numAsientos, int velMax, boolean activado, int idAeropuerto) {
        conection = ConexionBBDD.getConnection();
        String insert = "INSERT INTO aviones (modelo, numero_asientos, velocidad_maxima, activado, id_aeropuerto) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstmt = conection.prepareStatement(insert);
            pstmt.setString(1, modelo);
            pstmt.setInt(2, numAsientos);
            pstmt.setInt(3, velMax);
            pstmt.setInt(4, activado ? 1 : 0);
            pstmt.setInt(5, idAeropuerto);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Actualiza el estado de un avión específico en la base de datos.
     *
     * @param modelo      el modelo del avión
     * @param idAeropuerto el ID del aeropuerto al que pertenece el avión
     * @param estado     indica si el avión debe ser activado o desactivado
     */
    public static void update(String modelo, int idAeropuerto, boolean estado) {
        conection = ConexionBBDD.getConnection();
        String update = "UPDATE aviones SET activado=? WHERE modelo=? AND id_aeropuerto=?";
        try {
            PreparedStatement pstmt = conection.prepareStatement(update);
            pstmt.setInt(1, estado ? 1 : 0);
            pstmt.setString(2, modelo);
            pstmt.setInt(3, idAeropuerto);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina un avión de la base de datos basado en su modelo y el ID del aeropuerto.
     *
     * @param modelo      el modelo del avión a eliminar
     * @param idAeropuerto el ID del aeropuerto al que pertenece el avión
     */
    public static void delete(String modelo, int idAeropuerto) {
        conection = ConexionBBDD.getConnection();
        String delete = "DELETE FROM aviones WHERE modelo=? AND id_aeropuerto=?";
        try {
            PreparedStatement pstmt = conection.prepareStatement(delete);
            pstmt.setString(1, modelo);
            pstmt.setInt(2, idAeropuerto);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
