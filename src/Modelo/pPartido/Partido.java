package Modelo.pPartido;

import Exceptions.JugadorNoEncontradoException;
import Exceptions.LimiteEntrenamientoException;
import Modelo.Equipo.Equipo;
import Modelo.Persona.Jugador;
import enums.EventoPartido;
import org.json.JSONArray;
import org.json.JSONObject;

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

    public Partido (JSONObject jsonObject){
        JSONObject jsonLocal = jsonObject.getJSONObject("local");
        this.local = new Equipo(jsonLocal);

        JSONObject jsonVisitante = jsonObject.getJSONObject("visitante");
        this.visitante = new Equipo(jsonVisitante);

        this.golesLocal = jsonObject.getInt("golesLocal");
        this.golesVisitante = jsonObject.getInt("golesVisitante");
        this.faltasLocal = jsonObject.getInt("faltasLocal");
        this.faltasVisitante = jsonObject.getInt("faltasVisitante");
        this.amarillasLocal = jsonObject.getInt("amarillasLocal");
        this.amarillasVisitante = jsonObject.getInt("amarillasVisitante");
        this.rojasLocal = jsonObject.getInt("rojasLocal");
        this.rojasVisitante = jsonObject.getInt("rojasVisitante");

        this.goleadores = new ArrayList<>();

        this.random = new Random();
    }

    public JSONObject toJson(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("local", this.local.toJSON());
        jsonObject.put("visitante", this.visitante.toJSON());

        jsonObject.put("golesLocal", this.golesLocal);
        jsonObject.put("golesVisitante", this.golesVisitante);
        jsonObject.put("faltasLocal", this.faltasLocal);
        jsonObject.put("faltasVisitante", this.faltasVisitante);
        jsonObject.put("amarillasLocal", this.amarillasLocal);
        jsonObject.put("amarillasVisitante", this.amarillasVisitante);
        jsonObject.put("rojasLocal", this.rojasLocal);
        jsonObject.put("rojasVisitante", this.rojasVisitante);
        return jsonObject;
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
    public void simularInteractivo(Equipo equipoUsuario, Scanner sc) throws InterruptedException {
        boolean check = false;

        int opcion = -1;
        int cambiosRestantes = 5;

        double baseProb = 0.001;

        double probabilidadLocal = baseProb * local.calcularMediaGeneral();
        double probabilidadVisitante = visitante.calcularMediaGeneral() * baseProb;

        double probabilidadLocalCorner = local.calcularMediaGeneral() / (visitante.calcularMediaGeneral() + 10) * corner;
        double probabilidadVisitanteCorner = visitante.calcularMediaGeneral() / (local.calcularMediaGeneral() + 10) * corner;

        presentacionPartido(sc);

        for (int i = 1; i <= 90; i++) {
            IO.println("Minuto " + i);

            simularMinuto(probabilidadLocal, probabilidadVisitante, true, i,probabilidadLocalCorner,probabilidadVisitanteCorner);

            if (i == 45){
                IO.println("Finalizo el primer tiempo! ");
                IO.println(local.getNombre() + " " + golesLocal + " - " + visitante.getNombre() + " " + golesVisitante + "\n");

                do {
                    try {
                        IO.println("---------ENTRETIEMPO---------");
                        IO.println("Presione 1 para comenzar el ST");
                        IO.println("Presione 2 para realizar cambios" + " (Cambios restantes) " + cambiosRestantes + "\n");

                        opcion = sc.nextInt();

                        if (opcion < 1 || opcion  > 2){
                            System.out.println("Ingrese una opcion valida");
                            continue;
                        }

                        check = true;
                    } catch (InputMismatchException e) {
                        System.out.println("Ingrese una opcion valida");
                        sc.nextLine();
                    }


                    if (opcion == 1) check = true;

                    if (opcion == 2) {
                        if (cambiosRestantes > 0){
                            if (local.equals(equipoUsuario)) {
                                cambiosRestantes -= realizarCambioPartido(sc, local, cambiosRestantes);
                                check = false;
                            } else if (visitante.equals(equipoUsuario)) {
                                cambiosRestantes -= realizarCambioPartido(sc, visitante, cambiosRestantes);
                                check = false;
                            }
                        } else {
                            System.out.println("ADVERTENCIA: Límite de 5 cambios alcanzado. Seleccione 1 para continuar.");
                            check = true;
                        }
                    }

                } while (!check);


                IO.println("Empieza el segundo tiempo! ");
            }

            Thread.sleep(250);
        }

        Thread.sleep(1500);
        mostrarResultado();

        visitante.bajarSancion();
        local.bajarSancion();

        local.verificarTitulares();
        visitante.verificarTitulares();
    }

    public void simularRapido() throws InterruptedException {

        double probabilidadLocal = local.calcularMediaGeneral() * 0.0002;
        double probabilidadVisitante = visitante.calcularMediaGeneral() * 0.0002;
        double probabilidadLocalCorner = local.calcularMediaGeneral() / (visitante.calcularMediaGeneral() + 10) * corner;
        double probabilidadVisitanteCorner = visitante.calcularMediaGeneral() / (local.calcularMediaGeneral() + 10) * corner;

        for (int i = 1; i <= 90; i++) {
            simularMinuto(probabilidadLocal, probabilidadVisitante, false, i,probabilidadLocalCorner,probabilidadVisitanteCorner);
        }

        local.verificarTitulares();
        visitante.verificarTitulares();

        local.bajarSancion();
        visitante.bajarSancion();
    }

    public void simularMinuto(double probGolLocal, double probGolVisitante, boolean mostrar, int minuto, double c_Local, double c_Visitante) throws InterruptedException {

        double evento = random.nextDouble();
        double multiplicadorUltimosMinutos = (minuto > 80) ? 1.8 : (minuto > 70 ? 1.3 : 1.0);

        // VENTAJA PARA EQUIPO CON MÁS MEDIA
        double totalMedia = local.calcularMediaGeneral() + visitante.calcularMediaGeneral();
        double probAtaqueLocal = (local.calcularMediaGeneral() / totalMedia);

        // ---- 1. FALTAS ----
        if (evento < 0.08) { // 8% de chances de falta
            if (random.nextDouble() < 0.5) {
                gestionarFaltas(local, true, mostrar, minuto);
            } else {
                gestionarFaltas(visitante, false, mostrar, minuto);
            }
        }

        // ---- 2. CORNER ----
        else if (evento < 0.12) { // 4% de chances de corner
            if (random.nextDouble() < probAtaqueLocal) {
                if (random.nextDouble() < c_Local * 2.5 * multiplicadorUltimosMinutos) {
                    gestionarGolesAsistencias(local, true, mostrar, minuto);
                } else if (mostrar){
                    System.out.println("Minuto " + minuto + ": Corner local sin suerte");
                    Thread.sleep(100);
                }
            } else {
                if (random.nextDouble() < c_Visitante * 2.5 * multiplicadorUltimosMinutos) {
                    gestionarGolesAsistencias(visitante, false, mostrar, minuto);
                } else if (mostrar){
                    System.out.println("Minuto " + minuto + ": Corner visitante sin suerte");
                    Thread.sleep(100);
                }
            }
        }

        // ---- 3. ATAQUE NORMAL ----
        else if (evento < 0.4) { // 28% minutos con intento de ataque
            if (random.nextDouble() < probAtaqueLocal) {
                if (random.nextDouble() < probGolLocal * 2 * multiplicadorUltimosMinutos) {
                    gestionarGolesAsistencias(local, true, mostrar, minuto);
                }
            } else {
                if (random.nextDouble() < probGolVisitante * 2 * multiplicadorUltimosMinutos) {
                    gestionarGolesAsistencias(visitante, false, mostrar, minuto);
                }
            }
        }

        // ---- 4. LOCURA ESPECIAL ----
        else if (evento > 0.98 && minuto > 85) { // 2% chance en los últimos minutos
            if (random.nextBoolean()) {
                if (mostrar){
                    System.out.println("🔥 ¡" + local.getNombre() + " se lanza con todo al ataque final!");
                    Thread.sleep(250);
                }
                if (random.nextDouble() < probGolLocal * 5) {
                    gestionarGolesAsistencias(local, true, mostrar, minuto);
                }
            } else {
                if (mostrar){
                    System.out.println("🔥 ¡Contra letal de " + visitante.getNombre() + "!");
                    Thread.sleep(250);
                }
                if (random.nextDouble() < probGolVisitante * 5) {
                    gestionarGolesAsistencias(visitante, false, mostrar, minuto);
                }
            }
        }
    }

    public void gestionarGolesAsistencias (Equipo equipo, boolean local, boolean mostrar, int minuto) throws InterruptedException {

        Jugador goleador = equipo.elegirAutorGol();
        Jugador asistidor = equipo.elegirAutorAsistencia(goleador);

        if (goleador == null || asistidor == null) {
            return; // Si el equipo no tiene jugadores, no puede anotar. Salta el evento.
        }

        if (local){
            this.golesLocal++;
        } else {
            this.golesVisitante++;
        }

        goleador.anotarGoles();
        asistidor.anotarAsistencia();

        if (mostrar){
            System.out.println("⚽ ¡Goool de " + equipo.getNombre() + "! Anotó: " + goleador.getNombre());
            Thread.sleep(250);
            goleadores.add(new Gol(minuto, goleador, asistidor));
        }

        //VERIFICA QUE NO SEA FUERA DE JUEGO
        GestionEventoDePartido eventoOFF = new GestionEventoDePartido();
        EventoPartido offside = eventoOFF.generarlo(true);

        if(offside == EventoPartido.Pos_Adelantada){
            if(mostrar) {
                System.out.println("Gol anulado por fuera de juego...");
                Thread.sleep(250);
                goleadores.remove(goleadores.size()-1);
            }

            //anular gol y asistencia.
            if(local){
                if(this.golesLocal > 0) this.golesLocal--;
            }else{
                if(this.golesVisitante>0)this.golesVisitante--;
            }
            goleador.cancelarGoles();
            asistidor.cancelarAsistencia();
        }
    }

    public void gestionarFaltas (Equipo equipo, boolean local, boolean mostrar, int minuto) throws InterruptedException {
        Jugador autorFalta = equipo.elegirAutorFalta();

        if (autorFalta == null) {
            return; // Si el equipo no tiene jugadores, no puede cometer falta. Salta el evento.
        }
        int tipoTarjeta = determinarTarjeta();

        if (local){
            this.faltasLocal++;
            if (tipoTarjeta == 1){
                this.amarillasLocal++;
                autorFalta.setTarjetaLiga(autorFalta.getTarjetaLiga() + 1);

                if (mostrar){
                    System.out.println("️Minuto " + minuto + ": Falta de " + autorFalta.getNombre() + ". Amarilla para " + autorFalta.getNombre());
                    Thread.sleep(250);
                }

                if (autorFalta.getTarjetaLiga() >= 2){
                    this.rojasLocal++; // Contabiliza la roja
                    if(mostrar) System.out.println("¡Segunda amarilla! ROJA para " + autorFalta.getNombre());
                    Thread.sleep(250);
                    equipo.ExpulsarJugador(autorFalta);
                }
            } else if (tipoTarjeta == 2){
                this.rojasLocal++;
                autorFalta.setTarjetaLiga(2);

                if (mostrar){
                    System.out.println("Minuto " + minuto + ": ¡Falta grave de " + autorFalta.getNombre() + "! ROJA para " + autorFalta.getNombre());
                    Thread.sleep(250);
                }

                equipo.ExpulsarJugador(autorFalta);

            } else {
                if (mostrar){
                    System.out.println("Minuto " + minuto + ": Falta de " + autorFalta.getNombre());
                    Thread.sleep(250);
                }
            }
        } else {
            this.faltasVisitante++;
            if (tipoTarjeta == 1){
                this.amarillasVisitante++;
                autorFalta.setTarjetaLiga(autorFalta.getTarjetaLiga() + 1);

                if (mostrar){
                    System.out.println("️Minuto " + minuto + ": Falta de " + autorFalta.getNombre() + ". Amarilla para " + autorFalta.getNombre());
                    Thread.sleep(250);
                }

                if (autorFalta.getTarjetaLiga() >= 2) {
                    this.rojasVisitante++;
                    if(mostrar) System.out.println("¡Segunda amarilla! ROJA para " + autorFalta.getNombre());
                    Thread.sleep(250);
                    equipo.ExpulsarJugador(autorFalta);
                }

            } else if (tipoTarjeta == 2){
                this.rojasVisitante++;
                autorFalta.setTarjetaLiga(2);
                if (mostrar){
                    System.out.println("Minuto " + minuto + ": ¡Falta grave de " + autorFalta.getNombre() + "! ROJA para " + autorFalta.getNombre());
                }

                equipo.ExpulsarJugador(autorFalta);
            } else {
                if (mostrar){
                    System.out.println("Minuto " + minuto +": Falta de " + autorFalta.getNombre());
                    Thread.sleep(250);
                }
            }
        }
    }

    private int determinarTarjeta() {
        double valorAleatorio = random.nextDouble(); // Un número entre 0.0 y 1.0
        double probRoja = 0.001; // 3% de probabilidad de roja directa
        double probAmarilla = 0.1 ; // 20% de probabilidad de amarilla (adicional al 3% de roja)

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

    private void presentacionPartido(Scanner sc) {

        System.out.println("------------------------------------------------------------------");
        System.out.printf("%-5s %s %5s\n",
                local.getNombre().toUpperCase(),
                "VS",
                visitante.getNombre().toUpperCase());


        System.out.println("\n 🏟️ Estadio: " + local.getEstadio().getNombre() + " (Capacidad: " + local.getEstadio().getCapacidad() + ")\n");

        System.out.println("------------------------------------------------------------------");
        System.out.printf("%-30s | %-30s\n",
                "Táctica: " + local.getTecnico().getTacticaPreferida().getEformacion(),
                "Táctica: " + visitante.getTecnico().getTacticaPreferida().getEformacion());
        System.out.printf("%-30s | %-30s\n",
                "DT: " + local.getTecnico().getNombre(),
                "DT: " + visitante.getTecnico().getNombre());
        System.out.println("------------------------------------------------------------------");

        System.out.println("\nPresione Enter para el pitazo inicial...");
        sc.nextLine();
        sc.nextLine();

    }

    private void mostrarResultado () throws InterruptedException{
        System.out.println("Termina el partido");
        System.out.println(local.getNombre() + " " + golesLocal + " - " + visitante.getNombre() + " " + golesVisitante);

        for (Gol gol : goleadores) {
            System.out.println("Minuto " + gol.getMinuto() +
                    ": Gol de " + gol.getAutor().getNombre() +
                    " (Asistencia: " + gol.getAsistidor().getNombre() + ")");
            Thread.sleep(250);
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
                    throw new IllegalArgumentException();
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
                    throw new IllegalArgumentException ();
                }

                Jugador jugadorTitular = titularesArray.get(indiceTitular - 1);
                Jugador jugadorSuplente = suplentesArray.get(indiceSuplente - 1);


                equipo.realizarCambio(jugadorTitular,jugadorSuplente);

                check = true;

                return 1;
            } catch (InputMismatchException e) {
                System.out.println("Error, debe ingresar un numero");
                sc.nextLine();
            } catch (IllegalArgumentException e) {
                System.out.println("Error, numero fuera de rango. Intente nuevamente ");
            }
            catch (JugadorNoEncontradoException e){
                System.out.println("Error al procesar el cambio: "+ e.getMessage());
                System.out.println("Intente nuevamente");
            }
            catch (Exception e){
                System.out.println("Ocurrio un error inesperado. Intente nuevamente");
                sc.nextLine();
            }
        }
        return 0;
    }
}
