package DAO;

import java.sql.*;

import BBDD.ConexionBBDD;
import Model.DireccionModel;

public class DaoDireccion {

    private static Connection conection;

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

    public static void aniadir(String pais, String ciudad,String calle,int numero) {
        conection=ConexionBBDD.getConnection();
        String insert="INSERT INTO direcciones (pais,ciudad,calle,numero) VALUES (?,?,?,?)";
        try {
            PreparedStatement pstmt;
            pstmt=conection.prepareStatement(insert);
            pstmt.setString(1,pais);
            pstmt.setString(2,ciudad);
            pstmt.setString(3,calle);
            pstmt.setInt(4,numero);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Integer conseguirID(String pais, String ciudad,String calle,int numero) {
        conection=ConexionBBDD.getConnection();
        String select="SELECT id FROM direcciones WHERE pais=? AND ciudad=? AND calle=? AND numero=?";
        try {
            PreparedStatement pstmt;
            pstmt=conection.prepareStatement(select);
            pstmt.setString(1,pais);
            pstmt.setString(2,ciudad);
            pstmt.setString(3,calle);
            pstmt.setInt(4,numero);
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

