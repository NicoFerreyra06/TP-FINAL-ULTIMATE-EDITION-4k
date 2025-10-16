package Modelo;

import java.util.Random;

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

    public void simularInteractivo () throws InterruptedException{
        System.out.println("Empieza el partido");
        Thread.sleep(1000);

        double probabilidadLocal = local.calcularMediaGeneral() * 0.0002;
        double probabilidadVisitante = visitante.calcularMediaGeneral() * 0.0002;

        for (int i = 1; i <= 90; i++){
            System.out.println("Minuto" + i);
            if (random.nextDouble()< probabilidadLocal){
                System.out.println("Goool de " + getLocal().getNombre() + " " + local.getJugadorAzar().getNombre());

                this.golesLocal++;
            }

            if (random.nextDouble() < probabilidadVisitante){
                System.out.println("Goool de " + getVisitante().getNombre() + " "+ visitante.getJugadorAzar().getNombre());
                this.golesVisitante++;
            }
            Thread.sleep(500);
        }

        System.out.println(local.getNombre() + " " + golesLocal + " - " + visitante.getNombre() + " " + golesVisitante);
        System.out.println("Termina el partido");
    }

    public void simularRapido (){

        double probabilidadLocal = local.calcularMediaGeneral() * 0.0002;
        double probabilidadVisitante = visitante.calcularMediaGeneral() * 0.0002;

        for (int i = 1; i <= 90; i++){
            if (random.nextDouble() < probabilidadLocal){
                this.golesLocal++;
            }

            if (random.nextDouble() < probabilidadVisitante){
                this.golesVisitante++;
            }
        }

        System.out.println("Simulado rapido " + local.getNombre() + " " + golesLocal + " - " + visitante.getNombre() + " " + golesVisitante);
    }

    /**
     * Metodo para determinar el equipo ganador del partido. va a servir para copa y liga..
     * @return El objeto Equipo ganador, o null si fue empate.
     */

    public Equipo getGanador(){
        if (this.golesLocal == this.golesVisitante){
            return null;
        }

        if (this.golesLocal > this.golesVisitante){
            return local;
        } else {
            return visitante;
        }
    }

}
