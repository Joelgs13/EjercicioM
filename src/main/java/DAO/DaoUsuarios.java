package DAO;

import BBDD.ConexionBBDD;
import Model.UsuarioModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase de acceso a datos para la entidad Usuario.
 * Proporciona métodos para realizar operaciones en la base de datos relacionadas con los usuarios.
 */
public class DaoUsuarios {

    /**
     * Obtiene un usuario de la base de datos por su nombre de usuario.
     *
     * @param nombreUsuario el nombre de usuario que se desea buscar
     * @return un objeto UsuarioModel que representa el usuario encontrado,
     *         o null si no se encuentra ningún usuario con ese nombre
     */
    public static UsuarioModel getUsuario(String nombreUsuario) {
        Connection connection = null;
        UsuarioModel usuario = null;
        String sql = "SELECT * FROM usuarios WHERE usuario = ?";

        try {
            ConexionBBDD cBD = new ConexionBBDD();
            connection = ConexionBBDD.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
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
        } finally {
            // Cerrar la conexión después de usarla
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }

        return usuario;
    }
}
