package es.joel.ejerciciom.Model;

/**
 * Clase que representa un modelo de avión.
 * Incluye información sobre el modelo, número de asientos, velocidad máxima,
 * estado de activación y el ID del aeropuerto al que pertenece.
 */
public class AvionModel {

     String modelo;            // Modelo del avión
     int numeroAsientos;       // Número de asientos disponibles en el avión
     int velocidadMaxima;      // Velocidad máxima del avión en km/h
     boolean activado;          // Indica si el avión está activo (true) o inactivo (false)
     int idAeropuerto;         // ID del aeropuerto al que está asignado el avión

    /**
     * Constructor de la clase AvionModel.
     *
     * @param modelo el modelo del avión
     * @param numeroAsientos el número de asientos disponibles
     * @param velocidadMaxima la velocidad máxima del avión
     * @param activado indica si el avión está activado
     * @param idAeropuerto el ID del aeropuerto al que pertenece el avión
     */
    public AvionModel(String modelo, int numeroAsientos, int velocidadMaxima, Boolean activado, int idAeropuerto) {
        super();
        this.modelo = modelo;
        this.numeroAsientos = numeroAsientos;
        this.velocidadMaxima = velocidadMaxima;
        this.activado = activado;
        this.idAeropuerto = idAeropuerto;
    }

    /**
     * Devuelve una representación en cadena del modelo del avión.
     *
     * @return el modelo del avión
     */
    @Override
    public String toString() {
        return this.modelo;
    }

    /**
     * Obtiene el modelo del avión.
     *
     * @return el modelo
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * Obtiene el número de asientos disponibles en el avión.
     *
     * @return el número de asientos
     */
    public int getNumeroAsientos() {
        return numeroAsientos;
    }

    /**
     * Obtiene la velocidad máxima del avión.
     *
     * @return la velocidad máxima en km/h
     */
    public int getVelocidadMaxima() {
        return velocidadMaxima;
    }

    /**
     * Indica si el avión está activado.
     *
     * @return true si está activado, false si está inactivo
     */
    public boolean isActivado() {
        return activado;
    }

    /**
     * Obtiene el ID del aeropuerto al que pertenece el avión.
     *
     * @return el ID del aeropuerto
     */
    public int getIdAeropuerto() {
        return idAeropuerto;
    }
}
