package Modelo;

import java.util.ArrayList;
import java.util.HashSet;

public class Liga extends Torneo{
    private int jornada;
    private ArrayList<Partido> fixture;

    public Liga(String nombre) {
        super(nombre);
        this.jornada = 1;
        this.fixture = new ArrayList<>();
    }

// ... El resto de tu clase Liga ...

    public void generarFixture() {
        ArrayList<Equipo> listaEquipos = new ArrayList<>(super.getEquipos().values());
        ArrayList<Partido> fixtureTotal = new ArrayList<>();

        // Usar HashSet es mucho más rápido (O(1)) para búsquedas que ArrayList (O(N))
        HashSet<String> partidosProgramados = new HashSet<>();

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

            // 3.
            System.out.println("--- Jornada " + (jornada + 1) + " ---");
            for (Partido p : partidosDeJornada) {
                System.out.println(p.getLocal().getNombre() + " vs " + p.getVisitante().getNombre());
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
}
