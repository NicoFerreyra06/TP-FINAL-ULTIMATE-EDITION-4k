package Modelo;
import Interfaces.*;
import enums.Posicion;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class Jugador extends Persona implements iEntrenable {
    private final String id;
    private Posicion posicion;
    private int habilidadAtaque;
    private int habilidadDefensa;
    private int estadoFisico;
    private int golesConvertidos;
    private int asistencias;
    private int tarjeta;
    private final Random rand;

    public Jugador(String nombre, int edad, String nacionalidad, Posicion posicion, int habilidadAtaque, int habilidadDefensa, int estadoFisico) {
        super(nombre, edad, nacionalidad);
        this.id = UUID.randomUUID().toString();//ID unico del jugador
        this.posicion = posicion;
        this.habilidadAtaque = habilidadAtaque;
        this.habilidadDefensa = habilidadDefensa;
        this.estadoFisico = estadoFisico;
        this.tarjeta = 0;
        this.golesConvertidos = 0;
        this.rand = new Random();
    }

    // ==================== Getters y Setters ====================
    public int getGolesConvertidos() {
        return golesConvertidos;
    }

    public void setGolesConvertidos(int golesConvertidos) {
        this.golesConvertidos = golesConvertidos;
    }

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

    public int getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(int tarjeta) {
        this.tarjeta = tarjeta;
    }

    public void entrenar(){
        if (Math.random() < 0.3){
            return;
        }

        //Entrenamiento DELANTERO
        if(Posicion.DELANTERO == this.posicion){
            if (habilidadAtaque < 100){
                habilidadAtaque++;
                System.out.println(getNombre() + " ha mejorado su ATAQUE a " + habilidadAtaque + "!");
            } else {
                if (habilidadDefensa < 100){
                    habilidadDefensa++;
                    System.out.println(getNombre() + " ha mejorado su DEFENSA a " + habilidadDefensa + "!");
                }
            }
        }

        //Entrenamiento mediocampista
        if (Posicion.MEDIOCAMPISTA == this.posicion){
            if (rand.nextBoolean()){
                if (habilidadAtaque < 100) {
                    habilidadAtaque++;
                    System.out.println(getNombre() + " ha mejorado su ATAQUE a " + habilidadAtaque + "!");
                }
            } else {
                if (habilidadDefensa < 100) {
                    habilidadDefensa++;
                    System.out.println(getNombre() + " ha mejorado su DEFENSA a " + habilidadDefensa + "!");
                }
            }
        }

        //Entrenamiento defensor y arquero
        if (Posicion.DEFENSOR == this.posicion || Posicion.ARQUERO == this.posicion){
            if (habilidadDefensa < 100){
                habilidadDefensa++;
                System.out.println(getNombre() + " ha mejorado su DEFENSA a " + habilidadDefensa + "!");
            } else {
                if (habilidadAtaque < 100){
                    habilidadAtaque++;
                    System.out.println(getNombre() + " ha mejorado su ATAQUE a " + habilidadAtaque + "!");
                }
            }
        }

        if(Math.random() < 0.5){
            if (estadoFisico < 100){
                estadoFisico++;
                System.out.println(getNombre() + " ha mejorado su ESTADO FISICO " + estadoFisico + "!");
            }
        }
    }

    public void anotarGoles (){
        this.golesConvertidos++;
    }

    public void anotarAsistencia(){
        this.asistencias++;
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
        return String.format("%-5s | Posición: %s | Ataque: %d, Defensa: %d | Estado Físico: %d | Goles: %d\", ",
                this.getNombre(),
                this.posicion,
                this.habilidadAtaque,
                this.habilidadDefensa,
                this.estadoFisico,
                this.golesConvertidos
        );
    }
}
