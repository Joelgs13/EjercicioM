package es.joel.ejerciciom.Model;

/**
 * Clase que representa un modelo de usuario en el sistema.
 * Incluye información sobre el nombre de usuario y la contraseña.
 */
public class UsuarioModel {

     String usuario;  // Nombre de usuario
     String password; // Contraseña del usuario

    /**
     * Constructor de la clase UsuarioModel.
     *
     * @param usuario el nombre de usuario
     * @param password la contraseña del usuario
     */
    public UsuarioModel(String usuario, String password) {
        this.usuario = usuario;
        this.password = password;
    }

    /**
     * Obtiene el nombre de usuario.
     *
     * @return el nombre de usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Establece el nombre de usuario.
     *
     * @param usuario el nuevo nombre de usuario
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtiene la contraseña del usuario.
     *
     * @return la contraseña
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece la contraseña del usuario.
     *
     * @param password la nueva contraseña
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
