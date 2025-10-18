package Modelo;

import java.util.ArrayList;
import java.util.Random;

public class Partido {
    private final Equipo local;
    private final Equipo visitante;
    private int golesLocal;
    private int golesVisitante;
    private int faltasVisitante;
    private int faltasLocal;
    private int rojaVisitante;
    private int amarillaVisitante;
    private int rojaLocal;
    private int amarillaLocal;
    private final Random random;

    public Partido(Equipo local, Equipo visitante) {
        this.local = local;
        this.visitante = visitante;
        this.golesLocal = 0;
        this.golesVisitante = 0;
        this.random = new Random();
        this.rojaVisitante = 0;
        this.amarillaVisitante = 0;
        this.rojaLocal = 0;
        this.amarillaLocal = 0;
        this.faltasLocal = 0;
        this.faltasVisitante = 0;
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

    public int getRojaVisitante() {
        return rojaVisitante;
    }

    public void setRojaVisitante(int rojaVisitante) {
        this.rojaVisitante = rojaVisitante;
    }

    public int getAmarillaVisitante() {
        return amarillaVisitante;
    }

    public void setAmarillaVisitante(int amarillaVisitante) {
        this.amarillaVisitante = amarillaVisitante;
    }

    public int getRojaLocal() {
        return rojaLocal;
    }

    public void setRojaLocal(int rojaLocal) {
        this.rojaLocal = rojaLocal;
    }

    public int getAmarillaLocal() {
        return amarillaLocal;
    }

    public void setAmarillaLocal(int amarillaLocal) {
        this.amarillaLocal = amarillaLocal;
    }

    public int getFaltasVisitante() {
        return faltasVisitante;
    }

    public void setFaltasVisitante(int faltasTotalVisitante) {
        this.faltasVisitante = faltasTotalVisitante;
    }

    public int getFaltasLocal() {
        return faltasLocal;
    }

    public void setFaltasLocal(int faltasTotaLocal) {
        this.faltasLocal = faltasTotaLocal;
    }


    // ===================Metodos=======================
    public void simularInteractivo () throws InterruptedException{
        System.out.println("Empieza el partido entre " + local.getNombre() + " y " + visitante.getNombre());
        double probLocal = local.calcularMediaGeneral() * 0.0002;
        double probVisitante = visitante.calcularMediaGeneral() * 0.0002;

        Jugador falta;

        for (int i = 1; i <= 90; i++){
            System.out.println("Minuto " + i);
            simularMinuto(probLocal, probVisitante, true);
            //Falta probabilidades faltas y sucesos randoms del partido
            //con souts para que el usuario vea

            double probabilidadFalta = 0.133;
            double ajusteFalta = (local.calcularMediaGeneral() > visitante.calcularMediaGeneral())? 0.45 : 0.50;
            int tipoDeFalta = faltaPeligrosa();

            if(random.nextDouble() < probabilidadFalta){
                if(random.nextDouble() > ajusteFalta){
                    falta = visitante.elegirAutorFalta();
                    this.faltasVisitante++;

                    if(tipoDeFalta == 1){

                        System.out.println("Falta del equipo visitante..." + falta.getNombre() +
                                " A sido amodestado con Amarilla");

                        this.amarillaVisitante++;

                    } else if (tipoDeFalta == 2) {
                        System.out.println("Falta peligrosa del equipo visitante..." + falta.getNombre() +
                                " Expulsado!!!");

                        this.rojaVisitante++;

                    }else{

                        System.out.println("Falta del equipo visitante...");

                    }

                }
                else{
                    falta = local.elegirAutorFalta();
                    this.faltasLocal++;

                    if(tipoDeFalta == 1){

                        System.out.println("Falta del equipo Local..." + falta.getNombre() +
                                " A sido amonestado con Amarilla");

                        this.amarillaLocal++;

                        this.amarillaLocal++;

                    } else if (tipoDeFalta == 2) {
                        System.out.println("Falta peligrosa del equipo Local..." + falta.getNombre() +
                                " Expulsado!!!");

                        this.rojaLocal++;

                    }else{

                        System.out.println("Falta del equipo local...");
                    }

                    }

            }
            Thread.sleep(500);
        }

        mostrarResultadoFinal();
        System.out.println(local.getNombre() + " " + golesLocal + " - " + visitante.getNombre() + " " + golesVisitante);
        System.out.println("Faltas: " +  faltasLocal + " - " + faltasVisitante );
        System.out.println("Rojas: " +  rojaLocal + " - " + rojaVisitante);
        System.out.println("Amarillas: " + amarillaLocal + " - " + amarillaVisitante);
        System.out.println("Termina el partido");
    }

    public int faltaPeligrosa(){

        double tarjeta = random.nextDouble();;
        double amarilla = 0.20;
        double roja = 0.03;

        if(tarjeta <= roja)return 2;
        else if (tarjeta > amarilla) return 0;
        else return 1;

    }

    // ===================Simulacion Rapida=======================
    public void simularRapido (){

        double probLocal = local.calcularMediaGeneral() * 0.0002;
        double probVisitante = visitante.calcularMediaGeneral() * 0.0002;

        Jugador falta;

        for (int i = 1; i <= 90; i++){

            simularMinuto(probLocal, probVisitante, false);

            double probabilidadFalta = 0.133;
            double ajusteFalta = (local.calcularMediaGeneral() > visitante.calcularMediaGeneral())? 0.45 : 0.50;
            int tipoDeFalta = faltaPeligrosa();

            if(random.nextDouble() < probabilidadFalta){
                if(random.nextDouble() > ajusteFalta){
                    falta = visitante.elegirAutorFalta();

                    if(tipoDeFalta == 1){

                        System.out.println("Falta del equipo visitante..." + falta.getNombre() +
                                " A sido amodestado con Amarilla");

                    } else if (tipoDeFalta == 2) {
                        System.out.println("Falta peligrosa del equipo visitante..." + falta.getNombre() +
                                " Expulsado!!!");
                    }else{

                        System.out.println("Falta del equipo visitante...");
                    }

                }
                else{
                    falta = local.elegirAutorFalta();

                    if(tipoDeFalta == 1){

                        System.out.println("Falta del equipo Local..." + falta.getNombre() +
                                " A sido amodestado con Amarilla");

                    } else if (tipoDeFalta == 2) {
                        System.out.println("Falta peligrosa del equipo Local..." + falta.getNombre() +
                                " Expulsado!!!");
                    }else{

                        System.out.println("Falta del equipo local...");
                    }

                }

            }

        }

        System.out.println("Simulado rapido " + local.getNombre() + " " + golesLocal + " - " + visitante.getNombre() + " " + golesVisitante);
    }

    // ==================== Lógica de un minuto ====================
    private void simularMinuto(double probLocal, double probVisitante, boolean mostrar) {
        if (random.nextDouble() < probLocal)
            procesarGolesAsistencias(local, true, mostrar);

        if (random.nextDouble() < probVisitante)
            procesarGolesAsistencias(visitante, false, mostrar);
    }

    // ==================== Gol y asistencia ====================
    public void procesarGolesAsistencias(Equipo equipo, boolean esLocal, boolean mostrar){
        Jugador goleador = equipo.elegirAutorGol();
        Jugador asistidor = equipo.elegirAutorAsistencia(goleador);

        if (esLocal){
            golesLocal++;
        } else {
            golesVisitante++;
        }

        goleador.anotarGoles();
        asistidor.anotarAsistencia();

        if (mostrar) {
            System.out.println("¡Gol de " + equipo.getNombre() + "! " + goleador.getNombre() +
                    " (Asistencia de " + asistidor.getNombre() + ")");
        }
    }

    // ==================== Resultado final ====================
    private void mostrarResultadoFinal() {
        System.out.println("\nResultado final:");
        System.out.println(local.getNombre() + " " + golesLocal + " - " +
                visitante.getNombre() + " " + golesVisitante);
        System.out.println("Termina el partido.\n");
    }

    // ================AYUDAS================
    public boolean involucraEquipoUsuario (Equipo equipo){
        return local.equals(equipo) || visitante.equals(equipo);
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
