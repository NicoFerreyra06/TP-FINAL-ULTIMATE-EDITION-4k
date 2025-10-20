package Modelo;

import java.util.ArrayList;

public class Ronda
{
    private String nombre;
    private ArrayList <Partido> partidos;

    //falta completar metodo
    public void jugarRonda(Equipo equipo)
    {

    }

    //falta +getGanadores(): Arraylist-Equipo-


    //Constructor
    public Ronda(String nombre, ArrayList<Partido> partidos) {
        this.nombre = nombre;
        this.partidos = partidos;
    }
    
    
    //Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Partido> getPartidos() {
        return partidos;
    }

    public void setPartidos(ArrayList<Partido> partidos) {
        this.partidos = partidos;
    }
}
