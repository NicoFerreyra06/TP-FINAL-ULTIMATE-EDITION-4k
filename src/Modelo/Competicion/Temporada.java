package Modelo.Competicion;

import Modelo.Equipo.Equipo;
import Modelo.Podios.PodiosDeCompeticion;

public class Temporada {
    protected int anio;
    protected Torneo torneo;

    //Constructor
    public Temporada(int anio, Torneo torneo) {
        this.anio = anio;
        this.torneo = torneo;
    }

    //Getters y Setters
    public int getAnio() {
        return anio;
    }
    public void setAnio(int anio) {
        this.anio = anio;
    }

    public Torneo getTorneo() {
        return torneo;
    }
    public void setTorneo(Torneo torneo) {
        this.torneo = torneo;
    }

    try{

        boolean salir = false;
        int entrenamientosJornada = 0;
        final int limiteEntrenamiento = 1;

        while (!liga.isTerminada() && !salir) {
            int opcion = menuOpciones(sc, limiteEntrenamiento, entrenamientosJornada, liga);

            switch (opcion){
                case 1:
                    liga.jugarProximaFecha(usuarioEquipo);
                    entrenamientosJornada = 0;
                    break;
                case 2:
                    liga.mostrarTabla();
                    break;
                case 3:
                    mostrarEquipo(usuarioEquipo);
                    break;
                case 4:
                    entrenamientosJornada = menuEntrenamiento(entrenamientosJornada, limiteEntrenamiento,  usuarioEquipo);
                    break;

                case 5:
                    realizarCambios(usuarioEquipo, sc);
                    break;
                case 6:
                    buscarJugador(sc, liga);
                    break;
                case 7:
                    salir = true;
                    break;
            }
        }

        if (liga.isTerminada()) {
            System.out.println("\n--- ¡LA LIGA HA TERMINADO! ---");

            PodiosDeCompeticion<Liga> podiosDeCompeticion = new PodiosDeCompeticion<>(liga);
            podiosDeCompeticion.mostrarEstadisticasIndivuduales();
            liga.podioLiga();
        }
    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    }
}
