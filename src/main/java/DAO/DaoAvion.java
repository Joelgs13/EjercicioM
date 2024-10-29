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

    public static ObservableList<AvionModel> listaAviones(int id){
        conection= ConexionBBDD.getConnection();
        String select="SELECT modelo,numero_asientos,velocidad_maxima,activado FROM aviones WHERE id_aeropuerto=?";
        ObservableList<AvionModel> lst=FXCollections.observableArrayList();
        try {
            PreparedStatement pstmt = conection.prepareStatement(select);
            pstmt.setInt(1,id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                AvionModel avion=new AvionModel(rs.getString("modelo"), rs.getInt("numero_asientos"),rs.getInt("velocidad_maxima"), rs.getInt("activado")==1,id);
                lst.add(avion);
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return lst;
    }
}

