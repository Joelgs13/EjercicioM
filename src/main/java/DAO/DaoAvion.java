package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import BBDD.ConexionBBDD;
import Model.AeropuertoModel;
import Model.AvionModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class DaoAvion {
    private static Connection conection;

    public static ObservableList<AvionModel> listaAviones(int id) {
        conection = ConexionBBDD.getConnection();
        String select = "SELECT modelo,numero_asientos,velocidad_maxima,activado FROM aviones WHERE id_aeropuerto=?";
        ObservableList<AvionModel> lst = FXCollections.observableArrayList();
        try {
            PreparedStatement pstmt = conection.prepareStatement(select);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                AvionModel avion = new AvionModel(rs.getString("modelo"), rs.getInt("numero_asientos"), rs.getInt("velocidad_maxima"), rs.getInt("activado") == 1, id);
                lst.add(avion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lst;
    }

    public static ArrayList<AvionModel> conseguirListaTodos() {
        ArrayList<AvionModel> lst = new ArrayList<AvionModel>();
        conection = ConexionBBDD.getConnection();
        String select = "SELECT modelo,numero_asientos,velocidad_maxima,activado,id_aeropuerto FROM aviones";
        try {
            PreparedStatement pstmt = conection.prepareStatement(select);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                AvionModel avion = new AvionModel(rs.getString("modelo"), rs.getInt("numero_asientos"), rs.getInt("velocidad_maxima"), rs.getInt("activado") == 1, rs.getInt("id_aeropuerto"));
                lst.add(avion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lst;
    }

    public static void aniadir(String modelo,int numAsientos,int velMax, boolean activado, int idAeropuerto) {
        conection=ConexionBBDD.getConnection();
        String insert="INSERT INTO aviones (modelo,numero_asientos,velocidad_maxima,activado,id_aeropuerto) VALUES(?,?,?,?,?)";
        try {
            PreparedStatement pstmt = conection.prepareStatement(insert);
            pstmt.setString(1, modelo);
            pstmt.setInt(2,numAsientos);
            pstmt.setInt(3,velMax);
            if(activado) {
                pstmt.setInt(4,1);
            }else {
                pstmt.setInt(4,0);
            }
            pstmt.setInt(5,idAeropuerto);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
