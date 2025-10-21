package Modelo.Competicion;

import Modelo.Equipo.Equipo;
import Modelo.Partido.Partido;

import java.util.ArrayList;
import java.util.Collections; // Necesaria para el sorteo (barajar la lista)

public  class Copa extends Torneo {
    private ArrayList<Ronda> rondas;

    //Constructor
    public Copa(String nombre) {
        super(nombre);
        this.rondas = new ArrayList<>();
    }

    private String getNombreRonda(int numeroDeEquipos) {
        return switch (numeroDeEquipos) {
            case 16 -> "Octavos de Final";
            case 8  -> "Cuartos de Final";
            case 4  -> "Semifinales";
            case 2  -> "GRAN FINAL";
            default -> "Ronda de " + numeroDeEquipos;
        };
    }

    @Override
    public void jugarProximaFecha(Equipo equipoJugador) throws InterruptedException {
        // Si solo queda un equipo ya define al campeón de la copa.
        if (equiposTorneo.size() <= 1) {
            System.out.println("¡La copa ha finalizado!");
            return;
        }
        // Convertimos el HashMap a una lista para poder barajarla y sortear los cruces.
        ArrayList<Equipo> equiposEnCompetencia = new ArrayList<>(equiposTorneo.values());

        // Sorteo y creación de partidos
        Collections.shuffle(equiposEnCompetencia);
        ArrayList<Partido> partidosDeRonda = new ArrayList<>();

        System.out.println("--- Sorteo de la " + getNombreRonda(equiposEnCompetencia.size()) + " ---");
        for (int i = 0; i < equiposEnCompetencia.size(); i += 2) {
            Equipo local = equiposEnCompetencia.get(i);
            Equipo visitante = equiposEnCompetencia.get(i + 1);
            partidosDeRonda.add(new Partido(local, visitante));
            System.out.println("Partido definido: " + local.getNombre() + " vs " + visitante.getNombre());
        }

        // Jugar la ronda
        Ronda rondaActual = new Ronda(getNombreRonda(equiposEnCompetencia.size()), partidosDeRonda);

        // Aquí puedes diferenciar el partido del jugador del resto
        for (Partido partido : rondaActual.getPartidos()) {
            if (partido.involucraEquipoUsuario(equipoJugador)) {
                System.out.println("\n¡ES TU TURNO DE JUGAR!");
                partido.simularInteractivo(); // Simulación interactiva para el usuario
            } else {
                partido.simularRapido(); // Simulación rápida para la IA
            }
        }

        //  Obtener ganadores y actualizar la lista de equipos en la copa
        ArrayList<Equipo> ganadores = rondaActual.getGanadores(equipoJugador);
        equiposTorneo.clear(); // Vaciamos la lista de equipos viejos
        for (Equipo ganador : ganadores) {
            equiposTorneo.put(ganador.getNombre(), ganador); // Y la llenamos con los ganadores
        }

        // Declarar  campeón si es la última ronda
        if (equiposTorneo.size() == 1) {
            System.out.println("\n ¡¡" + ganadores.get(0).getNombre() + " ES EL CAMPEÓN DE LA COPA " + getNombre().toUpperCase() + "!!");
        }
    }


    public ArrayList<Ronda> getRondas() {
        return rondas;
    }

    public void setRondas(ArrayList<Ronda> rondas) {
        this.rondas = rondas;
    }


}
