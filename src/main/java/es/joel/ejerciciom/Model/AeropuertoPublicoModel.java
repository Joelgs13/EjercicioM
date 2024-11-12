package es.joel.ejerciciom.Model;

import java.sql.Blob;
import java.util.Objects;

/**
 * Clase que representa un Aeropuerto Público, que extiende la clase AeropuertoModel.
 * Esta clase incluye información adicional sobre la financiación y el número de trabajadores del aeropuerto.
 */
public class AeropuertoPublicoModel extends AeropuertoModel {

     float financiacion;      // Financiación del aeropuerto
     int numTrabajadores;      // Número de trabajadores del aeropuerto

    /**
     * Constructor de la clase AeropuertoPublicoModel.
     *
     * @param nombre el nombre del aeropuerto
     * @param anioInauguracion el año de inauguración del aeropuerto
     * @param capacidad la capacidad del aeropuerto
     * @param direccion la dirección del aeropuerto
     * @param imagen la imagen del aeropuerto en formato Blob
     * @param financiacion la financiación del aeropuerto
     * @param numTrabajadores el número de trabajadores del aeropuerto
     */
    public AeropuertoPublicoModel(String nombre, int anioInauguracion, int capacidad, DireccionModel direccion, Blob imagen, float financiacion, int numTrabajadores) {
        super(nombre, anioInauguracion, capacidad, direccion, imagen);
        this.financiacion = financiacion;
        this.numTrabajadores = numTrabajadores;
    }

    /**
     * Obtiene la financiación del aeropuerto.
     *
     * @return la financiación
     */
    public float getFinanciacion() {
        return financiacion;
    }

    /**
     * Obtiene el número de trabajadores del aeropuerto.
     *
     * @return el número de trabajadores
     */
    public int getNumTrabajadores() {
        return numTrabajadores;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(financiacion, numTrabajadores); // Incluye financiacion y numTrabajadores en el hash code
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
        AeropuertoPublicoModel other = (AeropuertoPublicoModel) obj;
        // Compara financiacion y numTrabajadores además de los atributos de AeropuertoModel
        return Float.floatToIntBits(financiacion) == Float.floatToIntBits(other.financiacion)
                && numTrabajadores == other.numTrabajadores;
    }
}
