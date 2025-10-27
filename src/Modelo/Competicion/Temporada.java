package Modelo.Competicion;

import Modelo.Equipo.Equipo;
import Modelo.Persona.Jugador;
import Modelo.Podios.PodiosDeCompeticion;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

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

    public void jugarLiga(Scanner sc, Liga liga, Equipo usuarioEquipo){
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

    public static void realizarCambios (Equipo usuarioEquipo, Scanner sc) {

        ArrayList<Jugador> titularesArray = new ArrayList<>(usuarioEquipo.getTitulares());
        ArrayList <Jugador> suplentesArray = new ArrayList<>(usuarioEquipo.getSuplentes());

        boolean check = false;

        while (!check) {
            try{
                int i = 0;
                System.out.println("\n=== TITULARES ===");
                for (Jugador j : usuarioEquipo.getTitulares()){
                    System.out.println(i + 1 + "-" +j.getNombre());
                    i++;
                }

                System.out.println("\nIngrese el numero del titular a cambiar");
                int indiceTitular = sc.nextInt();

                if (indiceTitular < 1 || indiceTitular > titularesArray.size()) {
                    throw new ArrayIndexOutOfBoundsException();
                }

                i = 0;
                System.out.println("\n=== SUPLENTES ===");
                for (Jugador j : usuarioEquipo.getSuplentes()){
                    System.out.println(i+ 1 + "-" +j.getNombre());
                    i++;
                }

                System.out.println("Ingrese el numero del suplente a cambiar");
                int indiceSuplente = sc.nextInt();

                if (indiceSuplente < 1 ||  indiceSuplente > suplentesArray.size()){
                    throw new IndexOutOfBoundsException();
                }

                Jugador jugadorTitular = titularesArray.get(indiceTitular - 1);
                Jugador jugadorSuplente = suplentesArray.get(indiceSuplente - 1);

                usuarioEquipo.realizarCambio(jugadorTitular,jugadorSuplente);

                check = true;
            } catch (InputMismatchException e) {
                System.out.println("Error, debe ingresar un numero valido");
                sc.nextLine();
            } catch (IllegalArgumentException e) {
                System.out.println("Error, numero fuera de rango. Intente nuevamente ");
            } catch (Exception e){
                System.out.println("Ocurrio un error inesperado. Intente nuevamente");
                sc.nextLine();
            }
        }
    }

    public static int menuOpciones (Scanner sc, int limiteEntrenamiento, int entrenamientosJornada, Liga liga){

        boolean check = false;
        int opcion = -1;

        while (!check) {
            try {
                System.out.println("\n--- Jornada " + liga.getJornada() + " ---");
                System.out.println("Menú de Acciones:");
                System.out.println("1. Jugar próxima fecha");
                System.out.println("2. Ver tabla de posiciones");
                int restantes = limiteEntrenamiento - entrenamientosJornada;
                System.out.println("3. Mostrar el equipo");
                System.out.println("4. Entrenar tus jugadores, (entrenamientos restantes: " + restantes + ")");
                System.out.println("5. Hacer cambios");
                System.out.println("6. Buscar jugador");
                System.out.println("7. Salir del juego");
                System.out.print("Elegi una opción: ");

                opcion = sc.nextInt();
                if (opcion < 1 || opcion > 7) {
                    throw new InputMismatchException();
                }

                check = true;

            } catch (InputMismatchException e) {
                System.out.println("Seleccione un numero valido entre 1 y 5");
                sc.nextLine();
            }
        }
        return opcion;
    }

    public static int menuEntrenamiento (int entrenamientosJornada, int limiteEntrenamiento, Equipo usuarioEquipo){
        if (entrenamientosJornada < limiteEntrenamiento) {
            usuarioEquipo.entrenarEquipo();
            return entrenamientosJornada + 1;
        } else {
            IO.println("Limites por jornada alcanzados");
            return entrenamientosJornada;
        }
    }

    public static void mostrarEquipo (Equipo equipo){
        ArrayList <Jugador> titularesArray = new ArrayList<>(equipo.getTitulares());
        ArrayList <Jugador> suplentesArray = new ArrayList<>(equipo.getSuplentes());

        System.out.println("\n=== TITULARES ===");
        for (Jugador jugador : titularesArray) {
            System.out.println(jugador.toString());
        }

        System.out.println("\n=== SUPLENTES ===");
        for (Jugador jugador : suplentesArray) {
            System.out.println(jugador.toString());
        }
    }

    public static void buscarJugador (Scanner sc, Liga liga){
        sc.nextLine();
        System.out.println("Ingrese el equipo en el que quiere buscar ");
        String nombreEquipo = sc.nextLine();

        System.out.println("Ingrese el nombre del jugador a buscar");
        String nombreJugador = sc.nextLine();

        liga.buscarJugador(nombreEquipo, nombreJugador);
    }
}
