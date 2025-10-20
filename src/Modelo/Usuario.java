package Modelo;

public class Usuario {
    protected String nombre;
    protected String contrasenia;
    protected Equipo equipoGestionado;

    //Constructor
    public Usuario(String nombre, String contrasenia, Equipo equipoGestionado) {
        this.nombre = nombre;
        this.contrasenia = contrasenia;
        this.equipoGestionado = equipoGestionado;
    }

    //Getters y Setters
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasenia() {
        return contrasenia;
    }
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public Equipo getEquipoGestionado() {
        return equipoGestionado;
    }
    public void setEquipoGestionado(Equipo equipoGestionado) {
        this.equipoGestionado = equipoGestionado;
    }

    //Metodo
    //Falta terminar
    public void verificarContrasenia() {

    }
}
