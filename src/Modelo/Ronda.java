package Modelo;

import java.util.ArrayList;
import java.util.Random;

public class Ronda
{
    private String nombre;
    private ArrayList <Partido> partidos;
    
    //Constructor
    public Ronda(String nombre, ArrayList<Partido> partidos) {
        this.nombre = nombre;
        this.partidos = partidos;
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


    //Recorre los partidos jugados y devuelve una lista con los ganadores.
    //Si hay empate, llama a la tandaDePenales para decidir un ganador.
    //return ArrayList<Equipo> con los equipos que avanzan a la siguiente ronda.

    public ArrayList<Equipo> getGanadores() {
        ArrayList<Equipo> ganadores = new ArrayList<>();

        for (Partido partido : this.partidos) {
            Equipo ganador = partido.getGanador();

            // Si el partido terminó en empate van a una tanda de penales.
            if (ganador == null) {
                System.out.println("El partido entre " + partido.getLocal().getNombre() + " y " +
                        partido.getVisitante().getNombre() + " terminó en empate.");

                ganador = tandaDePenales(partido.getLocal(), partido.getVisitante());

                System.out.println("\n¡" + ganador.getNombre() + " GANA LA TANDA DE PENALES Y AVANZA DE RONDA!\n");
            }

            // Se añade el ganador (decidido en el partido o por penales) a la lista.
            ganadores.add(ganador);
        }
        return ganadores;
    }


    //Simula una tanda de penales hasta obtener un unico ganador
    private Equipo tandaDePenales(Equipo local, Equipo visitante) {
        System.out.println("¡COMIENZA LA TANDA DE PENALES ENTRE " + local.getNombre() + " Y " + visitante.getNombre() + "!");

        int golesLocal = 0;
        int golesVisitante = 0;
        Random rand = new Random();

        // 1. Obtenemos la lista de jugadores titulares de cada equipo.
        // Como getTitulares() devuelve un HashSet, lo convertimos a ArrayList para poder elegir por índice.
        ArrayList<Jugador> pateadoresLocales = new ArrayList<>(local.getTitulares());
        ArrayList<Jugador> pateadoresVisitantes = new ArrayList<>(visitante.getTitulares());

        // Verificación para evitar errores si un equipo no tiene jugadores
        if (pateadoresLocales.isEmpty() || pateadoresVisitantes.isEmpty()) {
            System.out.println("Uno de los equipos no tiene jugadores para patear. Se decidirá por sorteo.");
            return rand.nextBoolean() ? local : visitante;
        }

        // --- Tanda de 5 penales ---
        for (int i = 1; i <= 5; i++) {
            System.out.println("\n--- Penal #" + i + " ---");

            // Patea el equipo local
            Jugador pateadorLocal = pateadoresLocales.get(rand.nextInt(pateadoresLocales.size()));
            if (rand.nextDouble() < pateadorLocal.getHabilidadAtaque() / 125.0)
                //Se divide por 125.00 para obtener un valor mas realista en la tanda de penales,
                // Ej: tiene 100 de Hab Ataque/ 125 da un total de 0.8 de probabilidad de anotar un gol.
            {
                golesLocal++;
                System.out.println("¡GOL de " + local.getNombre() + "! Anotó " + pateadorLocal.getNombre());
            } else {
                System.out.println("¡FALLÓ " + local.getNombre() + "! " + pateadorLocal.getNombre() + " no pudo convertir.");
            }

            // Patea el equipo visitante
            Jugador pateadorVisitante = pateadoresVisitantes.get(rand.nextInt(pateadoresVisitantes.size()));
            if (rand.nextDouble() < pateadorVisitante.getHabilidadAtaque() / 125.0) {
                golesVisitante++;
                System.out.println("¡GOL de " + visitante.getNombre() + "! Anotó " + pateadorVisitante.getNombre());
            } else {
                System.out.println("¡FALLÓ " + visitante.getNombre() + "! " + pateadorVisitante.getNombre() + " no pudo convertir.");
            }

            System.out.println("Resultado parcial: " + local.getNombre() + " " + golesLocal + " - " + visitante.getNombre() + " " + golesVisitante);
        }

        // --- Muerte súbita si siguen empatados ---
        if (golesLocal == golesVisitante) {
            System.out.println("\n--- ¡MUERTE SÚBITA! ---");
            while (golesLocal == golesVisitante) {
                // Se repite la misma lógica de un solo penal por equipo hasta que haya un ganador
                if (rand.nextDouble() < pateadoresLocales.get(rand.nextInt(pateadoresLocales.size())).getHabilidadAtaque() / 125.0) golesLocal++;
                if (rand.nextDouble() < pateadoresVisitantes.get(rand.nextInt(pateadoresVisitantes.size())).getHabilidadAtaque() / 125.0) golesVisitante++;
            }
        }

        // Devolver al ganador y anunciar el resultado final de la tanda
        System.out.println("Resultado final de la tanda: " + local.getNombre() + " " + golesLocal + " - " + visitante.getNombre() + " " + golesVisitante);
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
}
