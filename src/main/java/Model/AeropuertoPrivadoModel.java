package Model;

import java.sql.Blob;
import java.util.Objects;

public class AeropuertoPrivadoModel extends AeropuertoModel {

    int numSocios;

    public AeropuertoPrivadoModel(String nombre, int anioInauguracion, int capacidad, DireccionModel direccion, Blob imagen, int numSocios) {
        super(nombre, anioInauguracion, capacidad, direccion, imagen);
        this.numSocios = numSocios;
    }

    public int getNumSocios() {
        return numSocios;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(numSocios);
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
        return anioInauguracion == other.anioInauguracion && capacidad == other.capacidad
                && Objects.equals(direccion, other.direccion) && id == other.id && Objects.equals(imagen, other.imagen)
                && Objects.equals(nombre, other.nombre) && numSocios == other.numSocios;
    }
}
