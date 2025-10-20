package Modelo;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Liga extends Torneo{
    protected ArrayList<Partido> fixture;

    public Liga(String nombre) {
        super(nombre);
        this.fixture = new ArrayList<>();
    }

    public void tablaPosiciones(){

    }

    @Override
    public void jugar() {
        
    }
}
