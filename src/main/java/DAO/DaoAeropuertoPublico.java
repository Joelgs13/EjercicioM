package DAO;

import Model.AeropuertoPublicoModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DaoAeropuertoPublico {
    private Connection connection;

    public DaoAeropuertoPublico(Connection connection) {
        this.connection = connection;
    }

    // Método para obtener un aeropuerto público por ID
    public AeropuertoPublicoModel getAeropuertoPublico(int id) throws SQLException {
        String query = "SELECT * FROM aeropuertos_publicos WHERE id_aeropuerto = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new AeropuertoPublicoModel(
                        rs.getInt("id_aeropuerto"),
                        rs.getBigDecimal("financiacion"),
                        rs.getInt("num_trabajadores")
                );
            } else {
                return null; // Si no se encuentra el aeropuerto
            }
        }
    }

    // Método para insertar un nuevo aeropuerto público
    public boolean insertarAeropuertoPublico(AeropuertoPublicoModel aeropuertoPublico) throws SQLException {
        String query = "INSERT INTO aeropuertos_publicos (id_aeropuerto, financiacion, num_trabajadores) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, aeropuertoPublico.getIdAeropuerto());
            stmt.setBigDecimal(2, aeropuertoPublico.getFinanciacion());
            stmt.setInt(3, aeropuertoPublico.getNumTrabajadores());

            return stmt.executeUpdate() > 0; // Retorna true si la inserción fue exitosa
        }
    }

    // Método para eliminar un aeropuerto público
    public boolean eliminarAeropuertoPublico(AeropuertoPublicoModel aeropuertoPublico) throws SQLException {
        String query = "DELETE FROM aeropuertos_publicos WHERE id_aeropuerto = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, aeropuertoPublico.getIdAeropuerto());

            return stmt.executeUpdate() > 0; // Retorna true si la eliminación fue exitosa
        }
    }

    // Método para modificar un aeropuerto público existente
    public boolean modificarAeropuertoPublico(AeropuertoPublicoModel aeropuertoActual, AeropuertoPublicoModel nuevoAeropuertoPublico) throws SQLException {
        String query = "UPDATE aeropuertos_publicos SET financiacion = ?, num_trabajadores = ? WHERE id_aeropuerto = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setBigDecimal(1, nuevoAeropuertoPublico.getFinanciacion());
            stmt.setInt(2, nuevoAeropuertoPublico.getNumTrabajadores());
            stmt.setInt(3, aeropuertoActual.getIdAeropuerto());

            return stmt.executeUpdate() > 0; // Retorna true si la modificación fue exitosa
        }
    }

    // Método para cargar todos los aeropuertos públicos en una lista
    public List<AeropuertoPublicoModel> CargarListaPublicos() throws SQLException {
        List<AeropuertoPublicoModel> listaAeropuertosPublicos = new ArrayList<>();
        String query = "SELECT * FROM aeropuertos_publicos";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                AeropuertoPublicoModel aeropuertoPublico = new AeropuertoPublicoModel(
                        rs.getInt("id_aeropuerto"),
                        rs.getBigDecimal("financiacion"),
                        rs.getInt("num_trabajadores")
                );
                listaAeropuertosPublicos.add(aeropuertoPublico);
            }
        }
        return listaAeropuertosPublicos; // Retorna la lista de aeropuertos públicos
    }
}
