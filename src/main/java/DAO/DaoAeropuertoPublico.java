package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import BBDD.ConexionBBDD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Model.AeropuertoPublicoModel;

public class DaoAeropuertoPublico {

    private static Connection conection;

    public static ObservableList<AeropuertoPublicoModel> cargarListaAeropuertosPublicos(){
        ObservableList<AeropuertoPublicoModel>lst=FXCollections.observableArrayList();
        try {
            conection=ConexionBBDD.getConnection();
            String select="SELECT id,financiacion,num_trabajadores,nombre,anio_inauguracion,capacidad,id_direccion,imagen FROM aeropuertos_publicos,aeropuertos WHERE id=id_aeropuerto";
            PreparedStatement pstmt = conection.prepareStatement(select);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                AeropuertoPublicoModel modelo=new AeropuertoPublicoModel(rs.getString("nombre"),rs.getInt("anio_inauguracion"),rs.getInt("capacidad"), DaoDireccion.crearModeloDireccionPorID(rs.getInt("id_direccion")), rs.getBlob("imagen"),rs.getFloat("financiacion"), rs.getInt("num_trabajadores"));
                modelo.setId(rs.getInt("id"));
                lst.add(modelo);
                //System.out.println("añadido objeto: "+modelo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lst;
    }

    public static void aniadir(int idAeropuerto,float financiacion,int numTrabajadoes) {
        conection=ConexionBBDD.getConnection();
        String insert="INSERT INTO aeropuertos_publicos VALUES (?,?,?)";
        try {
            PreparedStatement pstmt;
            pstmt=conection.prepareStatement(insert);
            pstmt.setInt(1,idAeropuerto);
            pstmt.setFloat(2,financiacion);
            pstmt.setInt(3,numTrabajadoes);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void modificarPorID(int id,float financiacion, int numTrabajadores) {
        conection=ConexionBBDD.getConnection();
        String update="UPDATE aeropuertos_publicos SET financiacion=?,num_trabajadores=? WHERE id_aeropuerto=?";
        try {
            PreparedStatement pstmt;
            pstmt=conection.prepareStatement(update);
            pstmt.setFloat(1,financiacion);
            pstmt.setInt(2,numTrabajadores);
            pstmt.setInt(3,id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void eliminar(int id) {
        conection=ConexionBBDD.getConnection();
        String delete="DELETE FROM aeropuertos_publicos WHERE id_aeropuerto=?";
        try {
            PreparedStatement pstmt=conection.prepareStatement(delete);
            pstmt.setInt(1,id);
            pstmt.executeUpdate();
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

}
