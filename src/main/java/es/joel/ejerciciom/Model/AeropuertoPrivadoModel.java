package es.joel.ejerciciom.Model;

import java.sql.Blob;
import java.util.Objects;

/**
 * Clase que representa un Aeropuerto Privado, que extiende la clase AeropuertoModel.
 * Esta clase incluye información adicional sobre el número de socios del aeropuerto.
 */
public class AeropuertoPrivadoModel extends AeropuertoModel {

     int numSocios; // Número de socios del aeropuerto privado

    /**
     * Constructor de la clase AeropuertoPrivadoModel.
     *
     * @param nombre el nombre del aeropuerto
     * @param anioInauguracion el año de inauguración del aeropuerto
     * @param capacidad la capacidad del aeropuerto
     * @param direccion la dirección del aeropuerto
     * @param imagen la imagen del aeropuerto en formato Blob
     * @param numSocios el número de socios del aeropuerto privado
     */
    public AeropuertoPrivadoModel(String nombre, int anioInauguracion, int capacidad, DireccionModel direccion, Blob imagen, int numSocios) {
        super(nombre, anioInauguracion, capacidad, direccion, imagen);
        this.numSocios = numSocios;
    }

    /**
     * Obtiene el número de socios del aeropuerto.
     *
     * @return el número de socios
     */
    public int getNumSocios() {
        return numSocios;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(numSocios); // Incluye numSocios en el hash code
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        AeropuertoPrivadoModel other = (AeropuertoPrivadoModel) obj;
        return numSocios == other.numSocios; // Compara numSocios además de los atributos de AeropuertoModel
    }
}
