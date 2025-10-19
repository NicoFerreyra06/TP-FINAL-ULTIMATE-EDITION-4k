package Modelo;

import java.util.HashMap;

public abstract class Torneo {
    protected String nombre;
    protected HashMap<String, Equipo> equipos;

    public Torneo(String nombre) {
        this.nombre = nombre;
        this.equipos = new HashMap<>();
    }

    // ==================== Getters y Setters ====================
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public HashMap<String, Equipo> getEquipos() {
        return equipos;
    }

    // ==================== Metodos ====================
    public abstract void jugarProximaFecha();

}
