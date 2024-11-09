package com.example.ejerciciom.Model;

import java.sql.Blob;
import java.util.Objects;

/**
 * Clase que representa un Aeropuerto.
 * Esta clase contiene información sobre un aeropuerto, incluyendo su nombre,
 * año de inauguración, capacidad, dirección e imagen.
 */
public class AeropuertoModel {
     int id; // Identificador del aeropuerto
     String nombre; // Nombre del aeropuerto
     int anioInauguracion; // Año en que se inauguró el aeropuerto
     int capacidad; // Capacidad del aeropuerto
     DireccionModel direccion; // Dirección del aeropuerto
     Blob imagen; // Imagen del aeropuerto
    // Otros campos


    /**
     * Constructor de la clase AeropuertoModel.
     *
     * @param nombre el nombre del aeropuerto
     * @param anioInauguracion el año de inauguración del aeropuerto
     * @param capacidad la capacidad del aeropuerto
     * @param direccion la dirección del aeropuerto
     * @param imagen la imagen del aeropuerto en formato Blob
     */
    public AeropuertoModel(String nombre, int anioInauguracion, int capacidad, DireccionModel direccion, Blob imagen) {
        super();
        this.nombre = nombre;
        this.anioInauguracion = anioInauguracion;
        this.capacidad = capacidad;
        this.direccion = direccion;
        this.imagen = imagen;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public int getAnioInauguracion() { return anioInauguracion; }
    public int getCapacidad() { return capacidad; }
    public DireccionModel getDireccion() { return direccion; }
    public Blob getImagen() { return imagen; }
    public void setImagen(Blob imagen) {
        this.imagen = imagen;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AeropuertoModel other = (AeropuertoModel) obj;
        return anioInauguracion == other.anioInauguracion && capacidad == other.capacidad
                && Objects.equals(direccion, other.direccion) && id == other.id && Objects.equals(imagen, other.imagen)
                && Objects.equals(nombre, other.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(anioInauguracion, capacidad, direccion, id, imagen, nombre);
    }

    @Override
    public String toString() {
        return this.nombre; // Retorna el nombre del aeropuerto como representación de cadena
    }
}
