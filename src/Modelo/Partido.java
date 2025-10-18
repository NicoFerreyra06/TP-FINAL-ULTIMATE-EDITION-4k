package Modelo;

import java.util.*;

public class Partido {
    private final Equipo local;
    private final Equipo visitante;
    private int golesLocal;
    private int golesVisitante;
    private final Random random;

    public Partido(Equipo local, Equipo visitante) {
        this.local = local;
        this.visitante = visitante;
        this.golesLocal = 0;
        this.golesVisitante = 0;
        this.random = new Random();
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
        Jugador goleador;
        Jugador asistidor;
        Thread.sleep(1000);

        double probabilidadLocal = local.calcularMediaGeneral() * 0.0002;
        double probabilidadVisitante = visitante.calcularMediaGeneral() * 0.0002;

        ArrayList<Gol> goleadores = new ArrayList<>();

        for (int i = 1; i <= 90; i++) {
            System.out.println("Minuto " + i);
            //probabilidad meter gol local
            if (random.nextDouble() < probabilidadLocal) {
                //autor del gol
                goleador = local.elegirAutorGol();
                //Asistente
                asistidor = local.elegirAutorAsistencia(goleador);
                System.out.println("Goool de " + getLocal().getNombre() + " " + goleador.getNombre());

                goleador.anotarGoles();
                asistidor.anotarAsistencia();
                goleadores.add(new Gol(i, goleador, asistidor));

                this.golesLocal++;
            }

            //probabilidad meter gol visitante
            if (random.nextDouble() < probabilidadVisitante) {
                //autor del gol
                goleador = visitante.elegirAutorGol();
                //asistente
                asistidor = visitante.elegirAutorAsistencia(goleador);
                System.out.println("Goool de " + getVisitante().getNombre() + " " + goleador.getNombre());

                goleador.anotarGoles();
                asistidor.anotarAsistencia();
                goleadores.add(new Gol(i, goleador, asistidor));

                this.golesVisitante++;
            }

            //Falta probabilidades faltas y sucesos randoms del partido
            //con souts para que el usuario vea

            Thread.sleep(400);
        }

        System.out.println("Termina el partido");
        System.out.println(local.getNombre() + " " + golesLocal + " - " + visitante.getNombre() + " " + golesVisitante);

        for (Gol gol : goleadores) {
            System.out.println("Minuto " + gol.getMinuto() +
                    ": Gol de " + gol.getAutor().getNombre() +
                    " (Asistencia: " + gol.getAsistidor().getNombre() + ")");
        }

    }

    public void simularRapido() {
        Jugador goleador;
        Jugador asistidor;
        double probabilidadLocal = local.calcularMediaGeneral() * 0.0002;
        double probabilidadVisitante = visitante.calcularMediaGeneral() * 0.0002;

        for (int i = 1; i <= 90; i++) {//Calculo MINUTO A MINUTO
            //probabilidad meter gol local
            if (random.nextDouble() < probabilidadLocal) {
                goleador = local.elegirAutorGol();
                asistidor = local.elegirAutorAsistencia(goleador);
                goleador.anotarGoles();
                asistidor.anotarAsistencia();
                this.golesLocal++;
            }

            //probabilidad meter gol visitante
            if (random.nextDouble() < probabilidadVisitante) {
                goleador = visitante.elegirAutorGol();
                asistidor = visitante.elegirAutorAsistencia(goleador);

                goleador.anotarGoles();
                asistidor.anotarAsistencia();
                this.golesVisitante++;
            }

            //Falta probabilidades faltas y sucesos randoms del partido


        }

        System.out.println("Simulado rapido " + local.getNombre() + " " + golesLocal + " - " + visitante.getNombre() + " " + golesVisitante);
    }

    public boolean involucraEquipoUsuario(Equipo equipo) {
        return local.equals(equipo) || visitante.equals(equipo);
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
