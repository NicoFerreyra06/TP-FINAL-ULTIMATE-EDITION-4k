package Modelo.Competicion;

import Modelo.Equipo.Equipo;

public class Temporada {
    protected int anio;
    protected Torneo torneo;

    //Constructor
    public Temporada(int anio, Torneo torneo) {
        this.anio = anio;
        this.torneo = torneo;
    }

    //Getters y Setters
    public int getAnio() {
        return anio;
    }
    public void setAnio(int anio) {
        this.anio = anio;
    }

    public Torneo getTorneo() {
        return torneo;
    }
    public void setTorneo(Torneo torneo) {
        this.torneo = torneo;
    }
}
