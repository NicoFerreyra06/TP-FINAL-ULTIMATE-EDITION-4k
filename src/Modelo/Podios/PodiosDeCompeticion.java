package Modelo.Podios;

import Modelo.Competicion.Copa;
import Modelo.Competicion.FilaTabla;
import Modelo.Competicion.Liga;
import Modelo.Competicion.Torneo;
import Modelo.Equipo.Equipo;
import Modelo.Partido.Partido;
import Modelo.Persona.Jugador;

import java.util.*;
import java.util.stream.Collectors;

public class PodiosDeCompeticion <T extends Torneo> {

    private final T torneo;

    public PodiosDeCompeticion(T torneo) {
        this.torneo = torneo;
    }

    public void mostrarResumenCompleto(){


        if(torneo instanceof Liga liga){
            mostrarTablaClasificados((Liga) torneo);

        }
        else{mostrarTablaPorPuntos();}

        if(torneo instanceof Liga){

            Equipo campeon = obtenerEquiposOrdenados().stream().findFirst().orElse(null);
            if(campeon != null){
                System.out.println("🏆 Campeon de la liga: " + campeon.getNombre());
            }
        }

        mostrarTop3();

        mostrarEstadisticasIndivuduales();

    }



    /// -------------------------
    /// Tabla y clasificados
    /// -------------------------
    private void mostrarTablaClasificados(Liga liga){

        List<Equipo> ordenados = obtenerEquiposOrdenados();

        System.out.println("\n================ TABLA FINAL DE LA LIGA =================");
        System.out.printf("%-3s %-20s %-6s %-6s%n", "Pos", "Equipo", "Puntos", "Clasificación");
        System.out.println("--------------------------------------------------------");

        for(int i = 0; i< ordenados.size(); i++){

            Equipo e = ordenados.get(i);
            String clasif = obtenerClasificacion(i);
            System.out.printf("%-3d %-20s %-6d %-6s%n", i + 1, e.getNombre(), e.getPuntos(), clasif);
        }

        List<FilaTabla> posiciones = new ArrayList<>(liga.getTablaPosiciones().values());
        Collections.sort(posiciones);

        System.out.println("Clasificados a Copas Internacionales: ");
        for (int i = 0; i < posiciones.size();i++){

        }

    }

    public void mostrarTablaPorPuntos(){

        List<Equipo> ordenados = obtenerEquiposOrdenados();

        System.out.println("\n================ TABLA FINAL DE LA LIGA =================");
        System.out.printf("%-3s %-20s %-6s %-6s%n", "Pos", "Equipo", "Puntos");
        System.out.println("--------------------------------------------------------");

        for(int i = 0; i< ordenados.size(); i++){

            Equipo e = ordenados.get(i);
            String clasif = obtenerClasificacion(i);
            System.out.printf("%-3d %-20s %-6d %-6s%n", i + 1, e.getNombre(), e.getPuntos());
        }

    }

    public String obtenerClasificacion(int posicion){

        if(posicion < 3) return "Clasificado a ➡ Libertadores";
        if(posicion < 6) return "Clasificado a ➡ Sudamericana";
        return "-";

    }

    private List<Equipo> obtenerEquiposOrdenados(){

        Collection<Equipo> equipos = torneo.getEquipos().values();
        List<Equipo> lista = new ArrayList<>(equipos);
        lista.sort(Comparator.comparingInt(Equipo::getPuntos).reversed());
        return lista;
    }

    /// ---------------------
    /// Podio, top 3 (copa)
    /// ---------------------

    private void mostrarTop3(){

        List <Equipo> ordenados = obtenerEquiposOrdenados();
        System.out.println("\n================ PODIO / TOP 3 DEL TORNEO =================");
        for (int i = 0; i < Math.min(3,ordenados.size()); i ++){

            Equipo e = ordenados.get(i);
            System.out.printf("%d° %s (%d pts)%n", i + 1, e.getNombre(), e.getPuntos());

        }

    }

    /// -------------------------
    /// Estadisticas individuales
    /// -------------------------

    public void mostrarEstadisticasIndivuduales(){

        List<Jugador> todos = obtenerTodosLosJugadores();

        if(todos.isEmpty()){

            System.out.println("\n-No se encontraron jugadores.");
            return;
        }

        //goleadores
        List<Jugador> topGoleadores = todos.stream().sorted(Comparator.comparingInt(Jugador::getGolesConvertidos).reversed()).limit(5)
                .collect(Collectors.toList());


        //Asistidores
        ListList<Jugador> topAsistidores = todos.stream().sorted(Comparator.comparingInt(Jugador::getAsistencias).reversed()).limit(5)
                .collect(Collectors.toList());


        //Balon Oro = max(Asistencias + Goles);
        Jugador balonOro = todos.stream().max(Comparator.comparingInt(j-> j.getGolesConvertidos() + j.getAsistencias())).orElse(null);


        System.out.println("\n================ ESTADÍSTICAS INDIVIDUALES =================");
        System.out.println("\n-- Top 5 Máximos Goleadores --");
        for (int i = 0; i < topGoleadores.size(); i++) {
            Jugador j = topGoleadores.get(i);
            System.out.printf("%d. %s — %d goles%n", i + 1, j.getNombre(), j.getGolesConvertidos());
        }

        System.out.println("\n-- Top 5 Máximos Asistidores --");
        for (int i = 0; i < topAsistidores.size(); i++) {
            Jugador j = topAsistidores.get(i);
            System.out.printf("%d. %s — %d asistencias%n", i + 1, j.getNombre(), j.getAsistencias());
        }

        if (balonOro != null) {
            System.out.println("\n-- 🏅 BALON DE ORO --");
            System.out.printf("%s — %d contribuciones (goles + asistencias)%n",
                    balonOro.getNombre(), balonOro.getGolesConvertidos() + balonOro.getAsistencias());
        }

    }

    private List<Jugador>obtenerTodosLosJugadores(){

        List<Jugador> all = new ArrayList<>();
        for (Equipo e : torneo.getEquipos().values()){

            try {

                all.addAll(e.getTitulares());
                all.addAll(e.getSuplentes());

            }catch (Exception e1){}
        }

        return all;
    }


}
