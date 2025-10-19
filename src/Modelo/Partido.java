package Modelo;

import java.util.*;

public class Partido {
    private final Equipo local;
    private final Equipo visitante;
    private int golesLocal;
    private int golesVisitante;
    private final Random random;
    ArrayList<Gol> goleadores;

    public Partido(Equipo local, Equipo visitante) {
        this.local = local;
        this.visitante = visitante;
        this.golesLocal = 0;
        this.golesVisitante = 0;
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


    // ===================Metodos=======================
    public void simularInteractivo() throws InterruptedException {
        System.out.println("Empieza el partido");

        double probabilidadLocal = local.calcularMediaGeneral() * 0.0002;
        double probabilidadVisitante = visitante.calcularMediaGeneral() * 0.0002;

        for (int i = 1; i <= 90; i++) {
            System.out.println("Minuto " + i);

            simularMinuto(probabilidadLocal, probabilidadVisitante, true, i);

            Thread.sleep(400);
        }

        mostrarResultado();
    }

    public void simularRapido() {

        double probabilidadLocal = local.calcularMediaGeneral() * 0.0002;
        double probabilidadVisitante = visitante.calcularMediaGeneral() * 0.0002;

        for (int i = 1; i <= 90; i++) {//Calculo MINUTO A MINUTO
            simularMinuto(probabilidadLocal, probabilidadVisitante, false, i);
        }
    }

    public void simularMinuto (double probabilidadLocal, double probabilidadVisitante, boolean mostrar, int minuto) {
        if (random.nextDouble() < probabilidadLocal) {
            gestionarGolesAsistencias(this.local, true, mostrar, minuto);
        }

        if (random.nextDouble() < probabilidadVisitante) {
            gestionarGolesAsistencias(this.visitante, false, mostrar, minuto);
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
