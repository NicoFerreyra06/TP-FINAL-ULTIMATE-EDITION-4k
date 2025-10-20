package Modelo;

public abstract class Torneo {
    protected String nombre;

    public Torneo(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void inscribirEquipo(){

    }

    public abstract void jugar();
}
