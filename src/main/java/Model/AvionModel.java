package Model;

public class AvionModel {
     String modelo;
     int numeroAsientos;
     int velocidadMaxima;
     boolean activado; // Cambiado a int para representar 1 (activo) o 0 (inactivo)
     int idAeropuerto;

    public AvionModel(String modelo, int numeroAsientos, int velocidadMaxima, Boolean activado, int idAeropuerto) {
        super();
        this.modelo = modelo;
        this.numeroAsientos = numeroAsientos;
        this.velocidadMaxima = velocidadMaxima;
        this.activado = activado;
        this.idAeropuerto = idAeropuerto;
    }

    @Override
    public String toString() {
        return this.modelo;
    }

    public String getModelo() { return modelo; }
    public int getNumeroAsientos() { return numeroAsientos; }
    public int getVelocidadMaxima() { return velocidadMaxima; }
    public boolean isActivado() { return activado; } // Devuelve el valor int 1 o 0
    public int getIdAeropuerto() { return idAeropuerto; }
}
