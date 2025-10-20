package Modelo;

import java.util.HashMap;
import java.util.LinkedHashSet;

public class Temporada {
    protected int anio;
    protected Liga liga;
    protected LinkedHashSet<Equipo> equipos;

    //Constructor
    public Temporada(int anio, Liga liga) {
        this.anio = anio;
        this.liga = liga;
        this.equipos = new LinkedHashSet<>();
    }

    //Getters y Setters
    public int getAnio() {
        return anio;
    }
    public void setAnio(int anio) {
        this.anio = anio;
    }

    public Liga getLiga() {
        return liga;
    }
    public void setLiga(Liga liga) {
        this.liga = liga;
    }


}
