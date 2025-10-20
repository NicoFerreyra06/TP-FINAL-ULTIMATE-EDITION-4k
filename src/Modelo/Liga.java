package Modelo;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Liga extends Torneo{
    private ArrayList<Partido> fixture;

    //Constructor
    public Liga(String nombre) {
        super(nombre);
        this.fixture = new ArrayList<>();
    }

    //Metodos
    //Faltan terminar
    private void tablaPosiciones(){

    }

    @Override
    public void jugar() {

    }
}
