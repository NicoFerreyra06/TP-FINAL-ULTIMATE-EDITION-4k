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

    //Metodo
    //Falta terminar
    public boolean registrarse(String nombre, String contrasenia, Equipo equipo) {
        if (verificarNombre(nombre) && verificarContrasenia(contrasenia)) {
            System.out.println("El usuario ya existe.");
            return false;
        }else {
            setNombre(nombre);
            setContrasenia(contrasenia);
            setEquipoGestionado(equipo);
            return true;
        }
    }

    public boolean inicioSesion(String nombre, String contrasenia) {
        if (verificarNombre(nombre) && verificarContrasenia(contrasenia)) {
            return true;
        }
        return false;
    }

    public boolean verificarNombre(String nombre) {
        if (nombre.equals(this.nombre)) {
            return false;
        }
        return true;
    }

    public boolean verificarContrasenia(String contra) {
        if (contra.equals(getContrasenia())) {
            return false;
        }
        return true;
    }
}

