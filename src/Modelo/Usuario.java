package Modelo;

import java.util.Objects;

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

    //Métodos
    //Falta terminar

    //Registra un jugador en la base de datos, si este no existe
    public boolean registrarse(String nombre, String contrasenia) {
        if (verificarNombre(nombre) && verificarContrasenia(contrasenia)) {
            setNombre(nombre);
            setContrasenia(contrasenia);
            return true;
        }else {
            System.out.println("El usuario ya existe. Debe iniciar sesión.");
            return false;
        }
    }

    //Entra a la cuenta del usuario, si esta existe
    public boolean inicioSesion(String nombre, String contrasenia) {
        if (verificarNombre(nombre) && verificarContrasenia(contrasenia)) {
            return true;
        } else {
            System.out.println("El usuario no existe. Debe registrarse.");
            return false;
        }
    }

    //Verifica que el nombre de usuario no exista
    public boolean verificarNombre(String nombre) {
        if (nombre.equals(this.nombre)) {
            return false;
        }
        return true;
    }

    //Verifica que la contraseña no exista
    public boolean verificarContrasenia(String contra) {
        if (contra.equals(getContrasenia())) {
            return false;
        }
        return true;
    }
}

