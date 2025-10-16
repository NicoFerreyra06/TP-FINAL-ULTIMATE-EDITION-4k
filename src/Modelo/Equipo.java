package Modelo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Random;

public class Equipo {
    private String nombre;
    private Estadio estadio;
    private DirectorTecnico tecnico;
    private double presupuesto;
    private HashSet<Jugador> titulares;
    private HashSet<Jugador> suplentes;
    private int puntos;

    public Equipo(String nombre, Estadio estadio, DirectorTecnico tecnico, double presupuesto) {
        this.nombre = nombre;
        this.estadio = estadio;
        this.tecnico = tecnico;
        this.presupuesto = presupuesto;
        this.titulares = new HashSet<>();
        this.suplentes = new HashSet<>();
        this.puntos = 0;
    }

    // ==================== Getters y Setters ====================
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Estadio getEstadio() {
        return estadio;
    }

    public void setEstadio(Estadio estadio) {
        this.estadio = estadio;
    }

    public DirectorTecnico getTecnico() {
        return tecnico;
    }

    public void setTecnico(DirectorTecnico tecnico) {
        this.tecnico = tecnico;
    }

    public double getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(double presupuesto) {
        this.presupuesto = presupuesto;
    }

    public HashSet<Jugador> getTitulares() {
        return titulares;
    }

    public void setTitulares(HashSet<Jugador> titulares) {
        this.titulares = titulares;
    }

    public HashSet<Jugador> getSuplentes() {
        return suplentes;
    }

    public void setSuplentes(HashSet<Jugador> suplentes) {
        this.suplentes = suplentes;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    /**
     * Agrega jugador al equipo asegurandose que no haya duplicados
     * Si no existe, intenta agregarlo a la lista de titulares. Si esta ya tiene 11 jugadores,
     * lo agrega a la lista de suplentes.
     *
     * @return true si se agrego, falso si no.
     */
    public boolean agregarJugador(Jugador jugador) {
        if (jugador == null) return false;

        if (titulares.contains(jugador) || suplentes.contains(jugador)) {
            return false;
        }

        if (titulares.size() < 11) {
            return titulares.add(jugador);
        } else {
            return suplentes.add(jugador);
        }
    }

    /**
     * Calcula la media de habilidad general del equipo, basándose en el
     * promedio de habilidad de sus jugadores titulares.
     *
     * @return La valoración general del equipo en una escala de 0 a 100.
     */
    public double calcularMediaGeneral() {
        if (titulares.isEmpty()) return 0.0;
        double media = 0;
        for (Jugador jugador : titulares) {
            media += jugador.calcularMediaJugador();
        }
        return media / this.titulares.size();
    }

    public Jugador getJugadorAzar (){
        if (titulares.isEmpty()) return null;

        ArrayList<Jugador> listaT = new ArrayList<>(this.titulares);

        Random rand = new Random();

        int indice = rand.nextInt(listaT.size());

        return listaT.get(indice);
    }

    //Equals & HashCode
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Equipo equipo = (Equipo) o;
        return Objects.equals(nombre, equipo.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nombre);
    }

    @Override
    public String toString() {
        return "Equipo: " + nombre + "\n" +
                "  - Estadio: " + estadio.getNombre() + "\n" +
                "  - Director Técnico: " + tecnico.getNombre() + "\n" +
                "  - Presupuesto: $" + presupuesto + "\n" +
                "  - Puntos: " + puntos + "\n" +
                "  - Jugadores Titulares: " + titulares.size() + "\n" +
                "  - Jugadores Suplentes: " + suplentes.size();
    }
}
