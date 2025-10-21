package Modelo;

import java.util.*;

public class Liga extends Torneo{
    private int jornada;
    private ArrayList<Partido> fixture;
    private Map <Equipo, FilaTabla> tablaPosiciones;

    public Liga(String nombre) {
        super(nombre);
        this.jornada = 1;
        this.fixture = new ArrayList<>();
        this.tablaPosiciones = new HashMap<>();
    }

    public int getJornada() {
        return jornada;
    }

    public void setJornada(int jornada) {
        this.jornada = jornada;
    }

    public ArrayList<Partido> getFixture() {
        return fixture;
    }

    public void setFixture(ArrayList<Partido> fixture) {
        this.fixture = fixture;
    }

    public Map<Equipo, FilaTabla> getTablaPosiciones() {
        return tablaPosiciones;
    }

    public void setTablaPosiciones(Map<Equipo, FilaTabla> tablaPosiciones) {
        this.tablaPosiciones = tablaPosiciones;
    }

    public void generarFixture() {
        ArrayList<Equipo> listaEquipos = new ArrayList<>(super.getEquipos().values());
        ArrayList<Partido> fixtureTotal = new ArrayList<>();

        HashSet<String> partidosProgramados = new HashSet<>();

        for (Equipo equipo : listaEquipos) {
            this.tablaPosiciones.put(equipo, new FilaTabla(equipo));
        }

        int numeroEquipos = listaEquipos.size();

        // VALIDACIÓN: Es necesario un número par de equipos
        if (numeroEquipos % 2 != 0) {
            System.err.println("Error: El número de equipos debe ser par para generar el fixture.");
            // En un juego real, podrías añadir un equipo "fantasma" (BYE)
            return;
        }

        int numeroJornadas = (numeroEquipos - 1) * 2;
        int jornadasIda = numeroJornadas / 2; // La mitad de las jornadas son de "ida"

        for (int jornada = 0; jornada < numeroJornadas; jornada++) {
            ArrayList<Partido> partidosDeJornada = new ArrayList<>();

            // 1. Resetea el flag para esta jornada
            for (Equipo e : listaEquipos) {
                e.setJugoJornada(false);
            }

            // 2. Intenta armar la jornada
            for (int i = 0; i < listaEquipos.size(); i++) {
                for (int j = i + 1; j < listaEquipos.size(); j++) {

                    Equipo equipo1 = listaEquipos.get(i);
                    Equipo equipo2 = listaEquipos.get(j);

                    String claveIda = equipo1.getNombre() + "-" + equipo2.getNombre();
                    String claveVuelta = equipo2.getNombre() + "-" + equipo1.getNombre();

                    // Si ambos equipos están libres ESTA JORNADA
                    if (!equipo1.isJugoJornada() && !equipo2.isJugoJornada()) {

                        // ---- LÓGICA DE IDA ----
                        if (jornada < jornadasIda) {

                            // Si NUNCA se ha programado este cruce (ni A-B ni B-A)
                            if (!partidosProgramados.contains(claveIda) && !partidosProgramados.contains(claveVuelta)) {
                                partidosDeJornada.add(new Partido(equipo1, equipo2)); // A vs B
                                partidosProgramados.add(claveIda); // Registra "A-B"
                                equipo1.setJugoJornada(true);
                                equipo2.setJugoJornada(true);
                            }
                        }
                        // ---- LÓGICA DE VUELTA ----
                        else {

                            // Si el partido de VUELTA (B vs A) aún no se ha programado
                            if (!partidosProgramados.contains(claveVuelta)) { // <-- CORREGIDO
                                partidosDeJornada.add(new Partido(equipo2, equipo1)); // Partido INVERSO (B vs A)
                                partidosProgramados.add(claveVuelta); // Registra "B-A"
                                equipo1.setJugoJornada(true);
                                equipo2.setJugoJornada(true);
                            }
                        }
                    }
                }
            }

            // 4. Añade los partidos de esta jornada al fixture total
            fixtureTotal.addAll(partidosDeJornada);
        }

        // 5. Asigna el fixture completo a la variable de la clase
        this.fixture = fixtureTotal;
    }

    @Override
    public void jugarProximaFecha(Equipo equipoJugador) throws InterruptedException {
        if (jornada > (super.getEquipos().size() - 1) * 2) {
            IO.println("LA LIGA HA FINALIZADO");
            mostrarTabla();
            return;
        }

        ArrayList<Partido> partidosJornada = getPartidosFecha(this.jornada);
        ArrayList<Partido> partidosRapidos = new ArrayList<>();

        for (Partido p : partidosJornada) {
            if (p.involucraEquipoUsuario(equipoJugador)) {
                p.simularInteractivo();
            } else {
                p.simularRapido();
                partidosRapidos.add(p);
            }
            actualizarTabla(p);
        }

        System.out.println("\n--- Otros Resultados de la Fecha " + jornada + " ---");
        if (partidosRapidos.isEmpty()) {
            System.out.println("No hubo otros partidos.");
        } else {
            for (Partido pRapido : partidosRapidos) {
                System.out.println(pRapido.getLocal().getNombre() + " " + pRapido.getGolesLocal() +
                        " - " + pRapido.getVisitante().getNombre() + " " + pRapido.getGolesVisitante());
            }
        }

        mostrarTabla();
        this.jornada++;
    }

    private ArrayList<Partido> getPartidosFecha(int numeroFecha) {
        ArrayList<Partido> partidosDeLaFecha = new ArrayList<>();

        int numeroEquipos = super.getEquipos().size();
        int partidosPorFecha = numeroEquipos / 2;

        int indiceInicio = (numeroFecha - 1) * partidosPorFecha;
        int indiceFin = indiceInicio + partidosPorFecha;

        for (int i = indiceInicio; i < indiceFin && i < fixture.size(); i++) {
            partidosDeLaFecha.add(fixture.get(i));
        }

        return partidosDeLaFecha;
    }

    private void actualizarTabla(Partido partido) {
        Equipo local = partido.getLocal();
        Equipo visitante = partido.getVisitante();

        // Obtener las filas correspondientes
        FilaTabla filaLocal = tablaPosiciones.get(local);
        FilaTabla filaVisitante = tablaPosiciones.get(visitante);

        // Registrar el resultado en cada fila
        if (filaLocal != null) {
            filaLocal.registrarPartido(partido.getGolesLocal(), partido.getGolesVisitante());
        }
        if (filaVisitante != null) {
            filaVisitante.registrarPartido(partido.getGolesVisitante(), partido.getGolesLocal());
        }
    }

    /**
     * Imprime la tabla de posiciones actual en la consola.
     */
    public void mostrarTabla() {
        // Convertimos los valores del mapa a una lista
        List<FilaTabla> filasOrdenadas = new ArrayList<>(tablaPosiciones.values());

        Collections.sort(filasOrdenadas);

        // Encabezado
        System.out.println("\n---------------------------- TABLA DE POSICIONES ------------------------------");
        System.out.println("-------------------------------------------------------------------------------");
        System.out.printf("%-3s | %-20s | %3s | %2s | %2s | %2s | %2s | %3s | %3s | %3s\n",
                "Pos", "Equipo", "Pts", "PJ", "PG", "PE", "PP", "GF", "GC", "DG");
        System.out.println("-------------------------------------------------------------------------------");

        // Filas
        int posicion = 1;
        for (FilaTabla fila : filasOrdenadas) {
            System.out.printf("%-3d | %-20s | %3d | %2d | %2d | %2d | %2d | %3d | %3d | %2d\n",
                    posicion++,
                    fila.equipo.getNombre(),
                    fila.puntos,
                    fila.jugados,
                    fila.ganados,
                    fila.empatados,
                    fila.perdidos,
                    fila.golesFavor,
                    fila.golesContra,
                    fila.diferenciaGoles);
        }
        System.out.println("-------------------------------------------------------------------------------");
    }

    public void campeonLiga() {
        // Convertimos los valores del mapa a un arraylist y lo ordenamos
        List<FilaTabla> filasOrdenadas = new ArrayList<>(tablaPosiciones.values());
        Collections.sort(filasOrdenadas);

        if (filasOrdenadas.isEmpty()) {
            System.out.println("La tabla esta vacia");
        }
        //Traigo al campeon
        Equipo campeon = filasOrdenadas.getFirst().equipo;
        System.out.println("🏆 ¡El campeón de la liga es: " + campeon.getNombre() + "!");
    }

    //Metodo para saber si la liga esta terminada
    public boolean isTerminada (){
        int numeroEquipos = super.getEquipos().size();

        int jornadasTotales = (numeroEquipos - 1) * 2;

        return this.jornada > jornadasTotales;
    }
}
