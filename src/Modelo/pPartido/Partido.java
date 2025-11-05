package Modelo.pPartido;

import Exceptions.LimiteEntrenamientoException;
import Modelo.Equipo.Equipo;
import Modelo.Persona.Jugador;
import enums.EventoPartido;

import java.util.*;

public class Partido {
    private final Equipo local;
    private final Equipo visitante;
    private int golesLocal;
    private int golesVisitante;
    private int faltasLocal;
    private int faltasVisitante;
    private int amarillasLocal;
    private int amarillasVisitante;
    private int rojasLocal;
    private int rojasVisitante;
    private final Random random;
    ArrayList<Gol> goleadores;

    private static final double corner = 0.003;

    public Partido(Equipo local, Equipo visitante) {
        this.local = local;
        this.visitante = visitante;
        this.golesLocal = 0;
        this.golesVisitante = 0;
        this.faltasLocal = 0;
        this.faltasVisitante = 0;
        this.amarillasLocal = 0;
        this.amarillasVisitante = 0;
        this.rojasLocal = 0;
        this.rojasVisitante = 0;
        this.random = new Random();
        this.goleadores = new ArrayList<>();
    }

    // ==================== Getters y Setters ====================
    public Equipo getLocal() {
        return local;
    }

    public Equipo getVisitante() {
        return visitante;
    }

    public int getGolesLocal() {
        return golesLocal;
    }

    public void setGolesLocal(int golesLocal) {
        this.golesLocal = golesLocal;
    }

    public int getGolesVisitante() {
        return golesVisitante;
    }

    public void setGolesVisitante(int golesVisitante) {
        this.golesVisitante = golesVisitante;
    }

    public int getFaltasLocal() {
        return faltasLocal;
    }

    public void setFaltasLocal(int faltasLocal) {
        this.faltasLocal = faltasLocal;
    }

    public int getFaltasVisitante() {
        return faltasVisitante;
    }

    public void setFaltasVisitante(int faltasVisitante) {
        this.faltasVisitante = faltasVisitante;
    }

    public int getAmarillasLocal() {
        return amarillasLocal;
    }

    public void setAmarillasLocal(int amarillasLocal) {
        this.amarillasLocal = amarillasLocal;
    }

    public int getAmarillasVisitante() {
        return amarillasVisitante;
    }

    public void setAmarillasVisitante(int amarillasVisitante) {
        this.amarillasVisitante = amarillasVisitante;
    }

    public int getRojasLocal() {
        return rojasLocal;
    }

    public void setRojasLocal(int rojasLocal) {
        this.rojasLocal = rojasLocal;
    }

    public int getRojasVisitante() {
        return rojasVisitante;
    }

    public void setRojasVisitante(int rojasVisitante) {
        this.rojasVisitante = rojasVisitante;
    }

    // ===================Metodos=======================
    public void simularInteractivo(Equipo equipoUsuario) throws InterruptedException, LimiteEntrenamientoException {
        boolean check = false;
        int opcion = -1;
        int cambiosRestantes = 5;

        IO.println("Empieza el partido");

        double probabilidadLocal = local.calcularMediaGeneral() * 0.0002;
        double probabilidadVisitante = visitante.calcularMediaGeneral() * 0.0002;
        double probabilidadLocalCorner = local.calcularMediaGeneral() / (visitante.calcularMediaGeneral() + 10) * corner;
        double probabilidadVisitanteCorner = visitante.calcularMediaGeneral() / (local.calcularMediaGeneral() + 10) * corner;
        double probabilidadFalta = 0.10;

        Scanner scanner = new Scanner(System.in);

        local.verificarTitulares();
        visitante.verificarTitulares();

        for (int i = 1; i <= 90; i++) {
            IO.println("Minuto " + i);

            simularMinuto(probabilidadFalta, probabilidadLocal, probabilidadVisitante, true, i,probabilidadLocalCorner,probabilidadVisitanteCorner);

            if (i == 45){
                IO.println("Finalizo el primer tiempo! ");
                IO.println(local.getNombre() + " " + golesLocal + " - " + visitante.getNombre() + " " + golesVisitante + "\n");

                while (!check) {

                    IO.println("---------ENTRETIEMPO---------");
                    IO.println("Presione 1 para comenzar el ST");
                    IO.println("Presione 2 para realizar cambios" + " (Cambios restantes) " + cambiosRestantes + "\n");

                    opcion = scanner.nextInt();

                    if (opcion < 1 || opcion  > 2){
                        System.out.println("Ingrese una opcion valida");
                    }

                    if (opcion == 1) check = true;

                    if (opcion == 2) {
                        if (cambiosRestantes > 0){
                            if (local.equals(equipoUsuario)) {
                                cambiosRestantes -= realizarCambioPartido(scanner, local, cambiosRestantes);
                            } else if (visitante.equals(equipoUsuario)) {
                                cambiosRestantes -= realizarCambioPartido(scanner, visitante, cambiosRestantes);
                            }
                        } else {
                            throw new LimiteEntrenamientoException("Limite de cambios alcanzada");
                        }
                    }

                }
                IO.println("Empieza el segundo tiempo! ");
            }

            Thread.sleep(350);
        }

        mostrarResultado();

        visitante.bajarSancion();
        local.bajarSancion();

        local.verificarTitulares();
        visitante.verificarTitulares();
    }

    public void simularRapido() {

        double probabilidadLocal = local.calcularMediaGeneral() * 0.0002;
        double probabilidadVisitante = visitante.calcularMediaGeneral() * 0.0002;
        double probabilidadLocalCorner = local.calcularMediaGeneral() / (visitante.calcularMediaGeneral() + 10) * corner;
        double probabilidadVisitanteCorner = visitante.calcularMediaGeneral() / (local.calcularMediaGeneral() + 10) * corner;
        double probabilidadFalta = 0.10;

        for (int i = 1; i <= 90; i++) {
            simularMinuto(probabilidadFalta, probabilidadLocal, probabilidadVisitante, false, i,probabilidadLocalCorner,probabilidadVisitanteCorner);
        }

        local.verificarTitulares();
        visitante.verificarTitulares();
    }

    public void simularMinuto (double probabilidadFalta, double probabilidadLocal, double probabilidadVisitante, boolean mostrar, int minuto,double c_Local,double c_Visitante) {

        GestionEventoDePartido gestionEvento = new GestionEventoDePartido();
        EventoPartido evento = gestionEvento.generarlo(false);

        // ======================================
        // 1. SIMULACIÓN DE CÓRNERS
        // ======================================
        if(random.nextDouble() < c_Local){
            if (mostrar) System.out.println(minuto + ": Corner para el equipo local");

            // La probabilidad de gol aumenta para un córner (factor 1.5)
            double probGolAumentada = gestionEvento.modificarProbGol(EventoPartido.Corner, probabilidadLocal);

            // Simular si el córner termina en gol
            if (random.nextDouble() < probGolAumentada) {
                gestionarGolesAsistencias(this.local, true, mostrar, minuto);
            }
        }

        // Córner para el Equipo VISITANTE
        if(random.nextDouble() < c_Visitante){
            if (mostrar) System.out.println(minuto + ": Corner para el equipo visitante");

            // La probabilidad de gol aumenta para un córner (factor 1.5)
            double probGolAumentada = gestionEvento.modificarProbGol(EventoPartido.Corner, probabilidadVisitante);

            // Simular si el córner termina en gol
            if (random.nextDouble() < probGolAumentada) {
                gestionarGolesAsistencias(this.visitante, false, mostrar, minuto);
            }
        }

        // ======================================
        // 2. SIMULACIÓN DE GOL ATAQUE
        // ======================================


        if (random.nextDouble() < probabilidadLocal) {
            gestionarGolesAsistencias(this.local, true, mostrar, minuto);
        }

        if (random.nextDouble() < probabilidadVisitante) {
            gestionarGolesAsistencias(this.visitante, false, mostrar, minuto);
        }

        // ======================================
        // 3. SIMULACIÓN DE FALTAS
        // ======================================

        if (random.nextDouble() < probabilidadFalta) { // ¿Ocurre una falta?
            // Si ocurre, AHORA decidimos quién la hizo
            double ajusteFalta = (local.calcularMediaGeneral() > visitante.calcularMediaGeneral()) ? 0.45 : 0.55;

            if (random.nextDouble() > ajusteFalta) {
                gestionarFaltas(this.visitante, false, mostrar, minuto); // Falta visitante
                gestionFaltaEvento(this.local, true, mostrar, minuto);

            } else {
                gestionarFaltas(this.local, true, mostrar, minuto); // Falta local
                gestionFaltaEvento(this.visitante, false, mostrar, minuto);
            }
        }


    }

    public void gestionFaltaEvento(Equipo equipoQueRecibe, boolean esLocal, boolean mostrar, int minuto){

        GestionEventoDePartido gestionEvento = new GestionEventoDePartido();
        EventoPartido evento = gestionEvento.generarlo(false);


        if(EventoPartido.Ninguno != evento && EventoPartido.Corner != evento){
            if (mostrar) {
                System.out.println(minuto + ": Se produce un " + evento + " a favor de " + equipoQueRecibe.getNombre());
            }
        }
        double proBase = equipoQueRecibe.calcularMediaGeneral() * 0.0002;
        double probGol = gestionEvento.modificarProbGol(evento, proBase);

        // SIMULAR EL GOL con la probabilidad AUMENTADA
        if(random.nextDouble() < probGol){
            gestionarGolesAsistencias(equipoQueRecibe, esLocal, mostrar, minuto);
        }
    }

    public void gestionarGolesAsistencias (Equipo equipo, boolean local, boolean mostrar, int minuto) {

        Jugador goleador = equipo.elegirAutorGol();
        Jugador asistidor = equipo.elegirAutorAsistencia(goleador);


        if (local){
            this.golesLocal++;
        } else {
            this.golesVisitante++;
        }

        goleador.anotarGoles();
        asistidor.anotarAsistencia();

        if (mostrar){
            System.out.println("⚽ ¡Goool de " + equipo.getNombre() + "! Anotó: " + goleador.getNombre());
            goleadores.add(new Gol(minuto, goleador, asistidor));
        }

        //VERIFICA QUE NO SEA FUERA DE JUEGO

        GestionEventoDePartido eventoOFF = new GestionEventoDePartido();
        EventoPartido offside = eventoOFF.generarlo(true);

        if(offside == EventoPartido.Pos_Adelantada){
            if(mostrar) {
                System.out.println("Gol anulado por fuera de juego...");
                goleadores.remove(goleadores.size()-1);
            }

            //anular gol y asistencia.
            if(local){
                if(this.golesLocal > 0) this.golesLocal--;

            }else{
                if(this.golesVisitante>0){this.golesVisitante--;

                }
            }
            goleador.cancelarGoles();
            asistidor.cancelarAsistencia();
        }
    }

    public void gestionarFaltas (Equipo equipo, boolean local, boolean mostrar, int minuto) {
        Jugador autorFalta = equipo.elegirAutorFalta();

        int tipoTarjeta = determinarTarjeta();

        if (local){
            this.faltasLocal++;
            if (tipoTarjeta == 1){
                this.amarillasLocal++;
                autorFalta.setTarjetaLiga(autorFalta.getTarjetaLiga() + 1);

                if (mostrar){
                    System.out.println("️Minuto " + minuto + ": Falta de " + autorFalta.getNombre() + ". Amarilla para " + autorFalta.getNombre());
                }

                if (autorFalta.getTarjetaLiga() >= 2){
                    this.rojasLocal++; // Contabiliza la roja
                    if(mostrar) System.out.println("¡Segunda amarilla! ROJA para " + autorFalta.getNombre());
                    equipo.ExpulsarJugador(autorFalta);
                }
            } else if (tipoTarjeta == 2){
                this.rojasLocal++;
                autorFalta.setTarjetaLiga(2);

                if (mostrar){
                    System.out.println("Minuto " + minuto + ": ¡Falta grave de " + autorFalta.getNombre() + "! ROJA para " + autorFalta.getNombre());
                }

                equipo.ExpulsarJugador(autorFalta);

            } else {
                if (mostrar){
                    System.out.println("Minuto " + minuto + ": Falta de " + autorFalta.getNombre());
                }
            }
        } else {
            this.faltasVisitante++;
            if (tipoTarjeta == 1){
                this.amarillasVisitante++;
                autorFalta.setTarjetaLiga(autorFalta.getTarjetaLiga() + 1);

                if (mostrar){
                    System.out.println("️Minuto " + minuto + ": Falta de " + autorFalta.getNombre() + ". Amarilla para " + autorFalta.getNombre());
                }

                if (autorFalta.getTarjetaLiga() >= 2) {
                    this.rojasVisitante++;
                    if(mostrar) System.out.println("¡Segunda amarilla! ROJA para " + autorFalta.getNombre());
                    equipo.ExpulsarJugador(autorFalta); // ¡Expulsar ahora!
                }

            } else if (tipoTarjeta == 2){
                this.rojasVisitante++;
                autorFalta.setTarjetaLiga(2);
                if (mostrar){
                    System.out.println("Minuto " + minuto + ": ¡Falta grave de " + autorFalta.getNombre() + "! ROJA para " + autorFalta.getNombre());
                }

                // *** ¡LLAMADA A EXPULSAR MOVIDA AQUÍ! ***
                equipo.ExpulsarJugador(autorFalta);
            } else {
                if (mostrar){
                    System.out.println("Minuto " + minuto +": Falta de " + autorFalta.getNombre());
                }
            }
        }
    }

    private int determinarTarjeta() {
        double valorAleatorio = random.nextDouble(); // Un número entre 0.0 y 1.0
        double probRoja = 0.02; // 3% de probabilidad de roja directa
        double probAmarilla = 0.3; // 20% de probabilidad de amarilla (adicional al 3% de roja)

        if (valorAleatorio < probRoja) {
            return 2; // Roja
        } else if (valorAleatorio < probRoja + probAmarilla) {
            return 1; // Amarilla
        } else {
            return 0; // Sin tarjeta
        }
    }

    public boolean involucraEquipoUsuario(Equipo equipo) {
        return local.equals(equipo) || visitante.equals(equipo);
    }

    public void mostrarResultado (){
        System.out.println("Termina el partido");
        System.out.println(local.getNombre() + " " + golesLocal + " - " + visitante.getNombre() + " " + golesVisitante);

        for (Gol gol : goleadores) {
            System.out.println("Minuto " + gol.getMinuto() +
                    ": Gol de " + gol.getAutor().getNombre() +
                    " (Asistencia: " + gol.getAsistidor().getNombre() + ")");
        }
    }
    /**
     * Metodo para determinar el equipo ganador del partido. va a servir para copa y liga..
     * @return El objeto Equipo ganador, o null si fue empate.
     */

    public Equipo getGanador() {
        if (this.golesLocal == this.golesVisitante) {
            return null;
        }

        if (this.golesLocal > this.golesVisitante) {
            return local;
        } else {
            return visitante;
        }
    }

    public int realizarCambioPartido (Scanner sc, Equipo equipo, int cambiosRestantes){

        ArrayList <Jugador> titularesArray = new ArrayList<>(equipo.getTitulares());
        ArrayList <Jugador> suplentesArray = new ArrayList<>(equipo.getSuplentes());

        boolean check = false;

        while (!check) {
            try{
                int i = 0;
                System.out.println("\n=== TITULARES ===");
                for (Jugador j : equipo.getTitulares()){
                    System.out.println(i + 1 + "-" +j.getNombre());
                    i++;
                }

                System.out.println("\nIngrese el numero del titular a cambiar");
                int indiceTitular = sc.nextInt();

                if (indiceTitular < 1 || indiceTitular > equipo.getTitulares().size()) {
                    throw new ArrayIndexOutOfBoundsException();
                }

                i = 0;
                System.out.println("\n=== SUPLENTES ===");
                for (Jugador j : equipo.getSuplentes()){
                    System.out.println(i+ 1 + "-" +j.getNombre());
                    i++;
                }

                System.out.println("Ingrese el numero del suplente a cambiar");
                int indiceSuplente = sc.nextInt();

                if (indiceSuplente < 1 ||  indiceSuplente > equipo.getSuplentes().size()){
                    throw new IndexOutOfBoundsException();
                }

                Jugador jugadorTitular = titularesArray.get(indiceTitular - 1);
                Jugador jugadorSuplente = suplentesArray.get(indiceSuplente - 1);


                equipo.realizarCambio(jugadorTitular,jugadorSuplente);

                check = true;

                return 1;
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
        return 0;
    }
}
