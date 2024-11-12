package es.joel.ejerciciom.Model;

/**
 * Clase que representa un modelo de dirección.
 * Incluye información sobre el país, la ciudad, la calle y el número de la dirección.
 */
public class DireccionModel {

     String pais;   // País de la dirección
     String ciudad; // Ciudad de la dirección
     String calle;  // Calle de la dirección
     int numero;    // Número de la dirección

    /**
     * Constructor de la clase DireccionModel.
     *
     * @param pais el país de la dirección
     * @param ciudad la ciudad de la dirección
     * @param calle la calle de la dirección
     * @param numero el número de la dirección
     */
    public DireccionModel(String pais, String ciudad, String calle, int numero) {
        super();
        this.pais = pais;
        this.ciudad = ciudad;
        this.calle = calle;
        this.numero = numero;
    }

    /**
     * Obtiene el país de la dirección.
     *
     * @return el país
     */
    public String getPais() {
        return pais;
    }

    /**
     * Obtiene la ciudad de la dirección.
     *
     * @return la ciudad
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * Obtiene la calle de la dirección.
     *
     * @return la calle
     */
    public String getCalle() {
        return calle;
    }

    /**
     * Obtiene el número de la dirección.
     *
     * @return el número
     */
    public int getNumero() {
        return numero;
    }
}
