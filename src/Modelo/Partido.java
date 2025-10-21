package Modelo;

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
    public void simularInteractivo() throws InterruptedException {
        System.out.println("Empieza el partido");

        double probabilidadLocal = local.calcularMediaGeneral() * 0.0002;
        double probabilidadVisitante = visitante.calcularMediaGeneral() * 0.0002;

        double probabilidadFalta = 0.20;
        Scanner scanner = new Scanner(System.in);

        for (int i = 1; i <= 90; i++) {
            System.out.println("Minuto " + i);

            simularMinuto(probabilidadFalta, probabilidadLocal, probabilidadVisitante, true, i);

            if (i == 45){
                System.out.println("Finalizo el primer tiempo! ");

                IO.println("Presione enter para comenzar el ST");
                scanner.nextLine();
            }

            Thread.sleep(00);
        }

        System.out.println("Expulsados de local: ");
        local.mostrarExpulsados();
        System.out.println("Expulsados de visitante");
        visitante.mostrarExpulsados();

        visitante.bajarSancion();
        local.bajarSancion();
        mostrarResultado();
    }

    public void simularRapido() {

        double probabilidadLocal = local.calcularMediaGeneral() * 0.0002;
        double probabilidadVisitante = visitante.calcularMediaGeneral() * 0.0002;

        double probabilidadFalta = 0.10;

        for (int i = 1; i <= 90; i++) {
            simularMinuto(probabilidadFalta, probabilidadLocal, probabilidadVisitante, false, i);
        }
    }

    public void simularMinuto (double probabilidadFalta, double probabilidadLocal, double probabilidadVisitante, boolean mostrar, int minuto) {
        if (random.nextDouble() < probabilidadLocal) {
            gestionarGolesAsistencias(this.local, true, mostrar, minuto);
        }

        if (random.nextDouble() < probabilidadVisitante) {
            gestionarGolesAsistencias(this.visitante, false, mostrar, minuto);
        }

        if (random.nextDouble() < probabilidadFalta) { // ¿Ocurre una falta?
            // Si ocurre, AHORA decidimos quién la hizo
            double ajusteFalta = (local.calcularMediaGeneral() > visitante.calcularMediaGeneral()) ? 0.45 : 0.55; // Ejemplo
            if (random.nextDouble() > ajusteFalta) {
                gestionarFaltas(this.visitante, false, mostrar, minuto); // Falta visitante
            } else {
                gestionarFaltas(this.local, true, mostrar, minuto); // Falta local
            }
        }
    }

    public void gestionarGolesAsistencias (Equipo equipo, boolean local, boolean mostrar, int minuto) {
        if (local){
            this.golesLocal++;
        } else {
            this.golesVisitante++;
        }

        Jugador goleador = equipo.elegirAutorGol();
        Jugador asistidor = equipo.elegirAutorAsistencia(goleador);

        goleador.anotarGoles();
        asistidor.anotarAsistencia();

        if (mostrar){
            System.out.println("⚽ ¡Goool de " + equipo.getNombre() + "! Anotó: " + goleador.getNombre());
            goleadores.add(new Gol(minuto, goleador, asistidor));
        }
    }

    public void gestionarFaltas (Equipo equipo, boolean local, boolean mostrar, int minuto) {
        Jugador autorFalta = equipo.elegirAutorFalta();

        int tipoTarjeta = determinarTarjeta();

        if (local){
            this.faltasLocal++;
            if (tipoTarjeta == 1){
                this.amarillasLocal++;
                if (mostrar){
                    System.out.println("️Minuto " + minuto + ": Falta de " + autorFalta.getNombre() + ". Amarilla para " + autorFalta.getNombre());
                }
            } else if (tipoTarjeta == 2){
                this.rojasLocal++;
                if (mostrar){
                    System.out.println("Minuto " + minuto + ": ¡Falta grave de " + autorFalta.getNombre() + "! ROJA para " + autorFalta.getNombre());
                }
            } else {
                if (mostrar){
                    System.out.println("Minuto " + minuto + ": Falta de " + autorFalta.getNombre());
                }
            }
        } else {
            this.faltasVisitante++;
            if (tipoTarjeta == 1){
                this.amarillasVisitante++;
                if (mostrar){
                    System.out.println("️Minuto " + minuto + ": Falta de " + autorFalta.getNombre() + ". Amarilla para " + autorFalta.getNombre());
                }
            } else if (tipoTarjeta == 2){
                this.rojasVisitante++;
                if (mostrar){
                    System.out.println("Minuto " + minuto + ": ¡Falta grave de " + autorFalta.getNombre() + "! ROJA para " + autorFalta.getNombre());
                }
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

}
