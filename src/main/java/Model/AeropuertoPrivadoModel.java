package Model;

public class AeropuertoPrivadoModel {
    private int idAeropuerto;
    private int numeroSocios;

    public AeropuertoPrivadoModel(int idAeropuerto, int numeroSocios) {
        this.idAeropuerto = idAeropuerto;
        this.numeroSocios = numeroSocios;
    }

    // Getters y Setters
    public int getIdAeropuerto() { return idAeropuerto; }
    public void setIdAeropuerto(int idAeropuerto) { this.idAeropuerto = idAeropuerto; }

    public int getNumeroSocios() { return numeroSocios; }
    public void setNumeroSocios(int numeroSocios) { this.numeroSocios = numeroSocios; }
}
