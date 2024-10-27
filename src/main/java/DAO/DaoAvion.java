package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.AeropuertoModel;
import Model.AvionModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class DaoAvion {
    private Connection conexion;

    // Constructor que recibe la conexión de la base de datos
    public DaoAvion(Connection conexion) {
        this.conexion = conexion;
    }

    // Método para listar aviones en un TableView por ID de aeropuerto
    public void listarAviones(int idAeropuerto, TableView<AvionModel> tableView) {
        ObservableList<AvionModel> aviones = FXCollections.observableArrayList();
        String sql = "SELECT * FROM aviones WHERE id_aeropuerto = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idAeropuerto);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                AvionModel avion = new AvionModel(
                        rs.getInt("id"),
                        rs.getString("modelo"),
                        rs.getInt("numero_asientos"),
                        rs.getInt("velocidad_maxima"),
                        rs.getInt("activado"),
                        rs.getInt("id_aeropuerto")
                );
                aviones.add(avion);
            }
            tableView.setItems(aviones);
        } catch (SQLException e) {
            System.out.println("Error al listar aviones por aeropuerto: " + e.getMessage());
        }
    }

    // Método para listar todos los aviones en un TableView
    public void listarAviones(TableView<AvionModel> tableView) {
        ObservableList<AvionModel> aviones = FXCollections.observableArrayList();
        String sql = "SELECT * FROM aviones";

        try (PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                AvionModel avion = new AvionModel(
                        rs.getInt("id"),
                        rs.getString("modelo"),
                        rs.getInt("numero_asientos"),
                        rs.getInt("velocidad_maxima"),
                        rs.getInt("activado"),
                        rs.getInt("id_aeropuerto")
                );
                aviones.add(avion);
            }
            tableView.setItems(aviones);
        } catch (SQLException e) {
            System.out.println("Error al listar todos los aviones: " + e.getMessage());
        }
    }

    // Método para añadir un avión a la base de datos
    public boolean añadirAvion(AvionModel avion) {
        String sql = "INSERT INTO aviones (modelo, numero_asientos, velocidad_maxima, activado, id_aeropuerto) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, avion.getModelo());
            stmt.setInt(2, avion.getNumeroAsientos());
            stmt.setInt(3, avion.getVelocidadMaxima());
            stmt.setInt(4, avion.getActivado());
            stmt.setInt(5, avion.getIdAeropuerto());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al añadir avión: " + e.getMessage());
            return false;
        }
    }

    // Método para eliminar un avión de la base de datos por instancia de AvionModel
    public boolean eliminarAvion(AvionModel avion) {
        String sql = "DELETE FROM aviones WHERE id = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, avion.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar avión: " + e.getMessage());
            return false;
        }
    }

    // Método para eliminar todos los aviones de un aeropuerto específico
    public boolean eliminarAvion(AeropuertoModel aeropuerto) {
        String sql = "DELETE FROM aviones WHERE id_aeropuerto = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, aeropuerto.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar aviones por aeropuerto: " + e.getMessage());
            return false;
        }
    }

    // Método para actualizar un avión en la base de datos
    public boolean actualizarAvion(AvionModel avionActual, AvionModel avionNuevo) {
        String sql = "UPDATE aviones SET modelo = ?, numero_asientos = ?, velocidad_maxima = ?, activado = ?, id_aeropuerto = ? WHERE id = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, avionNuevo.getModelo());
            stmt.setInt(2, avionNuevo.getNumeroAsientos());
            stmt.setInt(3, avionNuevo.getVelocidadMaxima());
            stmt.setInt(4, avionNuevo.getActivado());
            stmt.setInt(5, avionNuevo.getIdAeropuerto());
            stmt.setInt(6, avionActual.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar avión: " + e.getMessage());
            return false;
        }
    }
}

