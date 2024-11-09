package com.example.ejerciciom.DAO;

import java.sql.*;

import com.example.ejerciciom.BBDD.ConexionBBDD;
import com.example.ejerciciom.Model.DireccionModel;

/**
 * Clase de acceso a datos para la entidad Dirección.
 * Proporciona métodos para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar)
 * en la base de datos relacionadas con las direcciones.
 */
public class DaoDireccion {

    /** La conexión a la base de datos. */
    private static Connection conection;

    /**
     * Crea un modelo de dirección a partir del ID proporcionado.
     *
     * @param id el ID de la dirección a buscar
     * @return un objeto DireccionModel que representa la dirección,
     *         o null si no se encuentra
     */
    public static DireccionModel crearModeloDireccionPorID(int id) {
        conection = ConexionBBDD.getConnection();
        String select = "SELECT pais, ciudad, calle, numero FROM direcciones WHERE id=?";
        try {
            PreparedStatement pstmt = conection.prepareStatement(select);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new DireccionModel(rs.getString("pais"), rs.getString("ciudad"), rs.getString("calle"), rs.getInt("numero"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Inserta una nueva dirección en la base de datos.
     *
     * @param direccion el objeto DireccionModel que se va a insertar
     * @return el ID generado para la nueva dirección
     * @throws SQLException si ocurre un error al insertar la dirección
     */
    public static int insertarDireccion(DireccionModel direccion) throws SQLException {
        String sql = "INSERT INTO direcciones (calle, ciudad, pais, numero) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionBBDD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, direccion.getCalle());
            pstmt.setString(2, direccion.getCiudad());
            pstmt.setString(3, direccion.getPais());
            pstmt.setInt(4, direccion.getNumero());

            pstmt.executeUpdate();

            // Obtener el ID generado
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Devuelve el ID de la dirección
                } else {
                    throw new SQLException("No se pudo obtener el ID de la dirección.");
                }
            }
        }
    }

    /**
     * Añade una nueva dirección a la base de datos.
     *
     * @param pais   el país de la dirección
     * @param ciudad la ciudad de la dirección
     * @param calle  la calle de la dirección
     * @param numero el número de la dirección
     */
    public static void aniadir(String pais, String ciudad, String calle, int numero) {
        conection = ConexionBBDD.getConnection();
        String insert = "INSERT INTO direcciones (pais, ciudad, calle, numero) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement pstmt = conection.prepareStatement(insert);
            pstmt.setString(1, pais);
            pstmt.setString(2, ciudad);
            pstmt.setString(3, calle);
            pstmt.setInt(4, numero);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtiene el ID de una dirección basada en sus atributos.
     *
     * @param pais   el país de la dirección
     * @param ciudad la ciudad de la dirección
     * @param calle  la calle de la dirección
     * @param numero el número de la dirección
     * @return el ID de la dirección, o null si no se encuentra
     */
    public static Integer conseguirID(String pais, String ciudad, String calle, int numero) {
        conection = ConexionBBDD.getConnection();
        String select = "SELECT id FROM direcciones WHERE pais=? AND ciudad=? AND calle=? AND numero=?";
        try {
            PreparedStatement pstmt = conection.prepareStatement(select);
            pstmt.setString(1, pais);
            pstmt.setString(2, ciudad);
            pstmt.setString(3, calle);
            pstmt.setInt(4, numero);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
