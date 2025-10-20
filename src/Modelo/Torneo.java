package Modelo;

import java.util.HashMap;
import java.util.LinkedHashMap;

public abstract class Torneo {
    protected String nombre;
    protected LinkedHashMap<String, Equipo> equiposTorneo;

    public Torneo(String nombre) {
        this.nombre = nombre;
        this.equiposTorneo = new LinkedHashMap<>();
    }

    // ==================== Getters y Setters ====================
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public HashMap<String, Equipo> getEquipos() {
        return equiposTorneo;
    }

    // ==================== Metodos ====================
    public abstract void jugarProximaFecha(Equipo equipoJugador) throws InterruptedException;

    public boolean anotarEquipo (Equipo equipo) {
        if (equipo == null) return false;

        if (equiposTorneo.containsKey(equipo.getNombre())) {
            System.out.println("Equipo ya inscripto");
            return false;
        }
        equiposTorneo.put(equipo.getNombre(), equipo);
        return true;
    }


}
