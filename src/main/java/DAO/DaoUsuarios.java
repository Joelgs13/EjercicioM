package DAO;

import Model.UsuarioModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoUsuarios {
    private Connection conexion;

    // Constructor que recibe la conexión de la base de datos
    public DaoUsuarios(Connection conexion) {
        this.conexion = conexion;
    }


    // Método para obtener un usuario de la base de datos por su nombre de usuario
    public UsuarioModel getUsuario(String nombreUsuario) {
        UsuarioModel usuario = null;
        String sql = "SELECT * FROM usuarios WHERE usuario = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, nombreUsuario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String usuarioNombre = rs.getString("usuario");
                String password = rs.getString("password");

                // Creación del objeto UsuarioModel con los datos obtenidos de la base de datos
                usuario = new UsuarioModel(usuarioNombre, password);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener el usuario: " + e.getMessage());
        }

        return usuario;
    }
}

