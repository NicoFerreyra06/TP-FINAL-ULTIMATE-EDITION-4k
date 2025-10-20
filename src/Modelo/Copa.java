package Modelo;

import java.util.ArrayList;

public class Copa extends Torneo{
    private ArrayList<Ronda> rondas;

    //Constructor
    public Copa(String nombre) {
        super(nombre);
        this.rondas = new ArrayList<>();
    }

    //Implementación del metodo jugar de la clase padre torneo
    //Falta terminar
    @Override
    public void jugar() {

    }
}
