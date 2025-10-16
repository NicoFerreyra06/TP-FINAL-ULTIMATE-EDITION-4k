package Modelo;

import enums.Posicion;

import java.util.Objects;
import java.util.UUID;

public class Jugador extends Persona {
    private final String id;
    private Posicion posicion;
    private int habilidadAtaque;
    private int habilidadDefensa;
    private int estadoFisico;

    public Jugador(String nombre, int edad, String nacionalidad, Posicion posicion, int habilidadAtaque, int habilidadDefensa, int estadoFisico) {
        super(nombre, edad, nacionalidad);
        this.id = UUID.randomUUID().toString();//ID unico del jugador
        this.posicion = posicion;
        this.habilidadAtaque = habilidadAtaque;
        this.habilidadDefensa = habilidadDefensa;
        this.estadoFisico = estadoFisico;
    }

    // ==================== Getters y Setters ====================
    public Posicion getPosicion() {
        return posicion;
    }

    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }

    public int getHabilidadAtaque() {
        return habilidadAtaque;
    }

    public void setHabilidadAtaque(int habilidadAtaque) {
        this.habilidadAtaque = habilidadAtaque;
    }

    public int getHabilidadDefensa() {
        return habilidadDefensa;
    }

    public void setHabilidadDefensa(int habilidadDefensa) {
        this.habilidadDefensa = habilidadDefensa;
    }

    public int getEstadoFisico() {
        return estadoFisico;
    }

    public void setEstadoFisico(int estadoFisico) {
        this.estadoFisico = estadoFisico;
    }

    public double calcularMediaJugador (){
        return (this.habilidadAtaque + this.habilidadDefensa) / 2.0;
    }

    public void entrenar(){

        if (Math.random() < 0.25){
            if (habilidadAtaque < 100){
                habilidadAtaque++;
                System.out.println(getNombre() + " ha mejorado su ATAQUE a " + habilidadAtaque + "!");
            } else {
                if (habilidadDefensa < 100){
                    habilidadDefensa++;
                    System.out.println(getNombre() + " ha mejorado su DEFENSA a " + habilidadAtaque + "!");
                }
            }
        }
    }
    //Equals & Hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jugador jugador = (Jugador) o;
        return Objects.equals(id, jugador.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("%-5s | Posición: %s | Ataque: %d, Defensa: %d | Estado Físico: %d",
                this.getNombre(),
                this.posicion,
                this.habilidadAtaque,
                this.habilidadDefensa,
                this.estadoFisico
        );
    }
}
