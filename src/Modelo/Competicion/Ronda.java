package Modelo.Competicion;

import Modelo.Equipo.Equipo;
import Modelo.Persona.Jugador;
import Modelo.Partido.Partido;

import java.util.ArrayList;
import java.util.Random;

public class Ronda
{
    private String nombre;
    private ArrayList <Partido> partidos;
    private ArrayList<Equipo> ganadoresDeLaRonda;

    //Constructor
    public Ronda(String nombre, ArrayList<Partido> partidos) {
        this.nombre = nombre;
        this.partidos = partidos;
        this.ganadoresDeLaRonda = null; //Se le da valor cuando se llame getGanadores()
    }

    //falta completar metodo
    public void jugarRonda()
    {
        System.out.println("========= JUGANDO: " + this.nombre + " =========");
        for (Partido partido : this.partidos) {
            partido.simularRapido(); // Usamos la simulación rápida para avanzar
        }
        System.out.println("========================================\n");
    }


    //Simula una tanda de penales hasta obtener un unico ganador
    private Equipo tandaDePenales(Equipo local, Equipo visitante, Equipo equipoJugador) {
        boolean esPartidoDelJugador = local.equals(equipoJugador) || visitante.equals(equipoJugador);

        if (esPartidoDelJugador) {
            System.out.println("¡COMIENZA LA TANDA DE PENALES ENTRE " + local.getNombre() + " Y " + visitante.getNombre() + "!");
        }

        int golesLocal = 0;
        int golesVisitante = 0;
        Random rand = new Random();
        ArrayList<Jugador> pateadoresLocales = new ArrayList<>(local.getTitulares());
        ArrayList<Jugador> pateadoresVisitantes = new ArrayList<>(visitante.getTitulares());

        if (pateadoresLocales.isEmpty() || pateadoresVisitantes.isEmpty()) {
            System.out.println("Uno de los equipos no tiene jugadores para patear. Se decidirá por sorteo.");
            return rand.nextBoolean() ? local : visitante;
        }

        // --- Tanda de 5 penales ---
        for (int i = 1; i <= 5; i++) {
            // Simula el penal del local
            Jugador pateadorLocal = pateadoresLocales.get(rand.nextInt(pateadoresLocales.size()));
            boolean golLocal = rand.nextDouble() < pateadorLocal.getHabilidadAtaque() / 125.0;
            if (golLocal) golesLocal++;

            // Simula el penal del visitante
            Jugador pateadorVisitante = pateadoresVisitantes.get(rand.nextInt(pateadoresVisitantes.size()));
            boolean golVisitante = rand.nextDouble() < pateadorVisitante.getHabilidadAtaque() / 125.0;
            if (golVisitante) golesVisitante++;

            // Muestra el detalle SOLO si es el partido del jugador
            if (esPartidoDelJugador) {
                System.out.println("\n--- Penal #" + i + " ---");
                System.out.println(golLocal ? "¡GOL de " + local.getNombre() + "!" : "¡FALLÓ " + local.getNombre() + "!");
                System.out.println(golVisitante ? "¡GOL de " + visitante.getNombre() + "!" : "¡FALLÓ " + visitante.getNombre() + "!");
                System.out.println("Resultado parcial: " + local.getNombre() + " " + golesLocal + " - " + visitante.getNombre() + " " + golesVisitante);
            }
        }

        // --- Muerte súbita si siguen empatados ---
        if (golesLocal == golesVisitante) {
            if (esPartidoDelJugador) System.out.println("\n--- ¡MUERTE SÚBITA! ---");
            while (golesLocal == golesVisitante) {
                if (rand.nextDouble() < pateadoresLocales.get(rand.nextInt(pateadoresLocales.size())).getHabilidadAtaque() / 125.0) golesLocal++;
                if (rand.nextDouble() < pateadoresVisitantes.get(rand.nextInt(pateadoresVisitantes.size())).getHabilidadAtaque() / 125.0) golesVisitante++;
            }
        }

        if (esPartidoDelJugador) {
            System.out.println("Resultado final de la tanda: " + local.getNombre() + " " + golesLocal + " - " + visitante.getNombre() + " " + golesVisitante);
        }

        return (golesLocal > golesVisitante) ? local : visitante;
    }

    
    //Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Partido> getPartidos() {
        return partidos;
    }

    public void setPartidos(ArrayList<Partido> partidos) {
        this.partidos = partidos;
    }

    public ArrayList<Equipo> getGanadoresDeLaRonda() {
        return ganadoresDeLaRonda;
    }

    //El metodo getGanadores ahora guarda el resultado antes de devolverlo
    public ArrayList<Equipo> getGanadores(Equipo equipoJugador) {
        ArrayList<Equipo> ganadores = new ArrayList<>();
        for (Partido partido : this.partidos) {
            Equipo ganador = partido.getGanador();
            if (ganador == null) {
                // (Aquí va tu lógica de penales, que no cambia)
                ganador = tandaDePenales(partido.getLocal(), partido.getVisitante(), equipoJugador);
            }
            ganadores.add(ganador);
        }
        //Guardamos la lista de ganadores en el atributo.
        this.ganadoresDeLaRonda = ganadores;
        return ganadores;
    }
}
