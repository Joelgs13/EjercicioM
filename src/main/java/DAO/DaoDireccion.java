package DAO;

import Model.DireccionModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoDireccion {
    private Connection connection;

    public DaoDireccion(Connection connection) {
        this.connection = connection;
    }

    // Método para obtener una dirección por ID
    public DireccionModel getDireccion(int id) throws SQLException {
        String query = "SELECT * FROM direcciones WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new DireccionModel(
                        rs.getInt("id"),
                        rs.getString("pais"),
                        rs.getString("ciudad"),
                        rs.getString("calle"),
                        rs.getInt("numero")
                );
            } else {
                return null; // Si no se encuentra la dirección
            }
        }
    }

    // Método para insertar una nueva dirección
    public boolean insertarDireccion(DireccionModel direccion) throws SQLException {
        String query = "INSERT INTO direcciones (pais, ciudad, calle, numero) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, direccion.getPais());
            stmt.setString(2, direccion.getCiudad());
            stmt.setString(3, direccion.getCalle());
            stmt.setInt(4, direccion.getNumero());

            return stmt.executeUpdate() > 0;
        }
    }

    // Método para eliminar una dirección
    public boolean eliminarDireccion(DireccionModel direccion) throws SQLException {
        String query = "DELETE FROM direcciones WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, direccion.getId());

            return stmt.executeUpdate() > 0;
        }
    }

    // Método para actualizar una dirección
    public boolean actualizarDireccion(DireccionModel direccionActual, DireccionModel nuevaDireccion) throws SQLException {
        String query = "UPDATE direcciones SET pais = ?, ciudad = ?, calle = ?, numero = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nuevaDireccion.getPais());
            stmt.setString(2, nuevaDireccion.getCiudad());
            stmt.setString(3, nuevaDireccion.getCalle());
            stmt.setInt(4, nuevaDireccion.getNumero());
            stmt.setInt(5, direccionActual.getId());

            return stmt.executeUpdate() > 0;
        }
    }
}
