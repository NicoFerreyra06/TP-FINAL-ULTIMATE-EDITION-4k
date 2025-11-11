package Modelo.Competicion;

import Exceptions.LimiteEntrenamientoException;
import Gestora.JsonUtiles;
import Modelo.Equipo.Equipo;
import Modelo.pPartido.Partido;
import java.util.ArrayList;
import java.util.Collections; // Necesaria para el sorteo (barajar la lista)
import java.util.Scanner;
import org.json.*;
import Interfaces.iToJSON;

public class Copa extends Torneo implements iToJSON{
    private ArrayList<Ronda> rondas;
    private ArrayList<Equipo> ganadores;

    //Constructor
    public Copa(String nombre) {
        super(nombre);
        this.rondas = new ArrayList<>();
        this.ganadores = new ArrayList<>();
    }

    public Copa (JSONObject json) {
        super(json);
        this.rondas = new ArrayList<>();

        JSONArray jsonRondas = json.getJSONArray("rondas");
        for (int i = 0; i < jsonRondas.length(); i++) {
            JSONObject jsonRonda = jsonRondas.getJSONObject(i);

            Ronda ronda = new Ronda(jsonRonda);

            this.rondas.add(ronda);
        }

        this.ganadores = new ArrayList<>();

        JSONArray jsonGanadores = json.getJSONArray("ganadores");
        for (int i = 0; i < jsonGanadores.length(); i++) {
            // Leemos el nombre del equipo
            String nombreGanador = jsonGanadores.getString(i);

            // Buscamos el objeto Equipo real en el mapa cargado por super(json)
            Equipo equipoGanador = super.equiposTorneo.get(nombreGanador);

            if (equipoGanador != null) {
                this.ganadores.add(equipoGanador);
            }
        }

    }

    public JSONObject toJSON() {
        JSONObject jsonObject = super.toJSON();

        JSONArray jsonRondas = new JSONArray();

        for (Ronda ronda : this.rondas) {
            jsonRondas.put(ronda.toJSON());
        }
        jsonObject.put("rondas", jsonRondas);

        JSONArray jsonGanadores = new JSONArray();
            for (Equipo equipo : this.ganadores) {
                jsonGanadores.put(equipo.getNombre());
            }

        jsonObject.put("ganadores", jsonGanadores);

        return jsonObject;
    }


    public ArrayList<Ronda> getRondas() {
        return rondas;
    }

    public void setRondas(ArrayList<Ronda> rondas) {
        this.rondas = rondas;
    }

    private String getNombreRonda(int numeroDeEquipos) {
        return switch (numeroDeEquipos) {
            case 16 -> "Octavos de Final";
            case 8 -> "Cuartos de Final";
            case 4 -> "Semifinales";
            case 2 -> "Final";
            default -> "Ronda de " + numeroDeEquipos;
        };
    }

    @Override
    public void jugarProximaFecha(Equipo equipoJugador, Scanner sc) throws InterruptedException, LimiteEntrenamientoException {
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
                partido.simularInteractivo(equipoJugador, sc); // Simulación interactiva para el usuario
            } else {
                partido.simularRapido();
            }
        }

        //  Obtener ganadores y actualizar la lista de equipos en la copa
        this.ganadores = rondaActual.getGanadores(equipoJugador);
        rondas.add(rondaActual);

        // Actualizamos la lista de equipos en la copa con los ganadores.
        equiposTorneo.clear(); // Vaciamos la lista de equipos viejos
        for (Equipo ganador : ganadores) {
            equiposTorneo.put(ganador.getNombre(), ganador); // Y la llenamos con los ganadores
        }

        // Declarar  campeón si es la última ronda
        if (equiposTorneo.size() == 1) {
            System.out.println("\n ¡¡" + ganadores.get(0).getNombre() + " ES EL CAMPEÓN DE LA COPA " + getNombre().toUpperCase() + "!!");
        }
    }

    public void campeonCopa (){
        if (equiposTorneo.size() == 1) {
            System.out.println("\n ¡¡" + ganadores.get(0).getNombre() + " ES EL CAMPEÓN DE LA COPA " + getNombre().toUpperCase() + "!!");
        } else {
            System.out.println("Aun quedan equipos en la competencia");
        }
    }

    public void mostrarBracket() {
        System.out.println("\n=============================================");
        System.out.println("        BRACKET DE LA " + getNombre().toUpperCase());
        System.out.println("=============================================");

        if (rondas.isEmpty()) {
            System.out.println("\n ...El torneo aún no ha comenzado...");
            return;
        }

        for (Ronda ronda : this.rondas) {
            System.out.println("\n--- " + ronda.getNombre().toUpperCase() + " ---");

            ArrayList<Partido> partidos = ronda.getPartidos();
            ArrayList<Equipo> ganadores = ronda.getGanadoresDeLaRonda();

            if (ganadores == null) continue; // Si la ronda no se jugó, la saltamos

            // Usamos UN SOLO BUCLE con un índice para emparejar partido y ganador
            for (int i = 0; i < partidos.size(); i++) {
                Partido partido = partidos.get(i);
                Equipo ganador = ganadores.get(i); // El ganador del partido i

                String resultado = String.format(" [%d] %-20s (%d) vs (%d) %-20s",
                        i + 1,
                        partido.getLocal().getNombre(),
                        partido.getGolesLocal(),
                        partido.getGolesVisitante(),
                        partido.getVisitante().getNombre());

                System.out.println(resultado + " -> Ganador: " + ganador.getNombre());
            }
        }
        System.out.println("\n=============================================");
    }

}