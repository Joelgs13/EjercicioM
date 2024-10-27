package DAO;

import Model.AeropuertoPrivadoModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DaoAeropuertoPrivado {
    private Connection connection;

    public DaoAeropuertoPrivado(Connection connection) {
        this.connection = connection;
    }

    // Método para obtener un aeropuerto privado por ID
    public AeropuertoPrivadoModel getAeropuertoPrivado(int id) throws SQLException {
        String query = "SELECT * FROM aeropuertos_privados WHERE id_aeropuerto = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new AeropuertoPrivadoModel(
                        rs.getInt("id_aeropuerto"),
                        rs.getInt("numero_socios")
                );
            } else {
                return null; // Si no se encuentra el aeropuerto
            }
        }
    }

    // Método para insertar un nuevo aeropuerto privado
    public boolean insertarAeropuertoPrivado(AeropuertoPrivadoModel aeropuertoPrivado) throws SQLException {
        String query = "INSERT INTO aeropuertos_privados (id_aeropuerto, numero_socios) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, aeropuertoPrivado.getIdAeropuerto());
            stmt.setInt(2, aeropuertoPrivado.getNumeroSocios());

            return stmt.executeUpdate() > 0;
        }
    }

    // Método para eliminar un aeropuerto privado
    public boolean eliminarAeropuertoPrivado(AeropuertoPrivadoModel aeropuertoPrivado) throws SQLException {
        String query = "DELETE FROM aeropuertos_privados WHERE id_aeropuerto = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, aeropuertoPrivado.getIdAeropuerto());

            return stmt.executeUpdate() > 0;
        }
    }

    // Método para modificar un aeropuerto privado existente
    public boolean modificarAeropuertoPrivado(AeropuertoPrivadoModel aeropuertoActual, AeropuertoPrivadoModel nuevoAeropuertoPrivado) throws SQLException {
        String query = "UPDATE aeropuertos_privados SET numero_socios = ? WHERE id_aeropuerto = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, nuevoAeropuertoPrivado.getNumeroSocios());
            stmt.setInt(2, aeropuertoActual.getIdAeropuerto());

            return stmt.executeUpdate() > 0;
        }
    }

    // Método para cargar todos los aeropuertos privados en una lista
    public List<AeropuertoPrivadoModel> CargarListaPrivados() throws SQLException {
        List<AeropuertoPrivadoModel> listaAeropuertosPrivados = new ArrayList<>();
        String query = "SELECT * FROM aeropuertos_privados";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                AeropuertoPrivadoModel aeropuertoPrivado = new AeropuertoPrivadoModel(
                        rs.getInt("id_aeropuerto"),
                        rs.getInt("numero_socios")
                );
                listaAeropuertosPrivados.add(aeropuertoPrivado);
            }
        }
        return listaAeropuertosPrivados;
    }
}
