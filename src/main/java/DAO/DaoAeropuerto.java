package DAO;

import BBDD.ConexionBBDD;
import Model.AeropuertoModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DaoAeropuerto {
    private static Connection connection;

    // Método para obtener un aeropuerto por ID
    public AeropuertoModel getAeropuerto(int id) throws SQLException {
        connection = ConexionBBDD.getConnection();
        String query = "SELECT * FROM aeropuertos WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new AeropuertoModel(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getInt("anio_inauguracion"),
                        rs.getInt("capacidad"),
                        rs.getInt("id_direccion"),
                        rs.getBytes("imagen")
                );
            } else {
                return null; // Si no se encuentra el aeropuerto
            }
        }
    }

    // Método para insertar un nuevo aeropuerto
    public boolean insertarAeropuerto(AeropuertoModel aeropuerto) throws SQLException {
        String query = "INSERT INTO aeropuertos (nombre, anio_inauguracion, capacidad, id_direccion, imagen) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, aeropuerto.getNombre());
            stmt.setInt(2, aeropuerto.getAnioInauguracion());
            stmt.setInt(3, aeropuerto.getCapacidad());
            stmt.setInt(4, aeropuerto.getIdDireccion());
            stmt.setBytes(5, aeropuerto.getImagen());

            return stmt.executeUpdate() > 0;
        }
    }

    // Método para eliminar un aeropuerto
    public boolean eliminarAeropuerto(AeropuertoModel aeropuerto) throws SQLException {
        String query = "DELETE FROM aeropuertos WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, aeropuerto.getId());

            return stmt.executeUpdate() > 0;
        }
    }

    // Método para modificar un aeropuerto existente con datos de un nuevo aeropuerto
    public boolean modificarAeropuerto(AeropuertoModel aeropuertoActual, AeropuertoModel nuevoAeropuerto) throws SQLException {
        String query = "UPDATE aeropuertos SET nombre = ?, anio_inauguracion = ?, capacidad = ?, id_direccion = ?, imagen = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nuevoAeropuerto.getNombre());
            stmt.setInt(2, nuevoAeropuerto.getAnioInauguracion());
            stmt.setInt(3, nuevoAeropuerto.getCapacidad());
            stmt.setInt(4, nuevoAeropuerto.getIdDireccion());
            stmt.setBytes(5, nuevoAeropuerto.getImagen());
            stmt.setInt(6, aeropuertoActual.getId());

            return stmt.executeUpdate() > 0;
        }
    }

    public List<AeropuertoModel> obtenerAeropuertosPublicos() throws SQLException {
        List<AeropuertoModel> aeropuertosPublicos = new ArrayList<>();
        String query = "SELECT * FROM aeropuertos WHERE id IN (SELECT id_aeropuerto FROM aeropuertos_publicos)"; // Asumiendo que el id de aeropuerto está en aeropuertos_publicos

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                AeropuertoModel aeropuerto = new AeropuertoModel(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getInt("anio_inauguracion"),
                        rs.getInt("capacidad"),
                        rs.getInt("id_direccion"),
                        rs.getBytes("imagen")
                );
                aeropuertosPublicos.add(aeropuerto);
            }
        }
        return aeropuertosPublicos; // Retorna la lista de aeropuertos públicos
    }

}
