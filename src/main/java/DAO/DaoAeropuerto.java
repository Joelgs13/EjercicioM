package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import BBDD.ConexionBBDD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Model.AeropuertoModel;
import Model.AeropuertoPrivadoModel;
import Model.AeropuertoPublicoModel;

public class DaoAeropuerto {

    private static Connection connection;

    public DaoAeropuerto() throws SQLException {
    }

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

    public static ObservableList<AeropuertoModel> listaTodas() {
        ObservableList<AeropuertoModel> lst = FXCollections.observableArrayList();
        lst.addAll(DaoAeropuertoPrivado.cargarListaAeropuertosPrivados());
        lst.addAll(DaoAeropuertoPublico.cargarListaAeropuertosPublicos());
        return lst;
    }

    public static void aniadir(String nombre,int anioInauguracion,int capacidad,int idDireccion,Blob imagen) {
        connection=ConexionBBDD.getConnection();
        String insert="INSERT INTO aeropuertos (nombre,anio_inauguracion,capacidad,id_direccion,imagen) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement pstmt;
            pstmt=connection.prepareStatement(insert);
            pstmt.setString(1,nombre);
            pstmt.setInt(2,anioInauguracion);
            pstmt.setInt(3,capacidad);
            pstmt.setInt(4,idDireccion);
            pstmt.setBlob(5,imagen);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Integer conseguirID(String nombre,int anioInauguracion,int capacidad,int idDireccion,Blob imagen) {
        connection=ConexionBBDD.getConnection();
        String select="SELECT id FROM aeropuertos WHERE nombre=? AND anio_inauguracion=? AND capacidad=? AND id_direccion=?";
        //select +=" AND imagen=?";
        try {
            PreparedStatement pstmt;
            pstmt=connection.prepareStatement(select);
            pstmt.setString(1,nombre);
            pstmt.setInt(2,anioInauguracion);
            pstmt.setInt(3,capacidad);
            pstmt.setInt(4,idDireccion);
            //pstmt.setBlob(5,imagen);
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

