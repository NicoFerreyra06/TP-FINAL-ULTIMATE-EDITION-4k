package Modelo;

import java.util.ArrayList;

public class Copa extends Torneo{
    private ArrayList<Ronda> rondas;

    public Copa(String nombre) {
        super(nombre);
        this.rondas = new ArrayList<>();
    }

    @Override
    public void jugar() {
        
    }
}
