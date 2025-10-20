package Modelo;

public abstract class Torneo {
    protected String nombre;

    //Constructor
    public Torneo(String nombre) {
        this.nombre = nombre;
    }

    //Getter y Setter
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    //Metodos
    //Faltan terminar
    public void inscribirEquipo(){

    }

    public abstract void jugar();
}
