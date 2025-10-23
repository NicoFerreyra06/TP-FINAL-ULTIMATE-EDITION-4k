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
        List <Jugador> topAsistidores = todos.stream().sorted(Comparator.comparingInt(Jugador::getAsistencias).reversed()).limit(5)
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
            System.out.println("\n-- EL JUGADOR DEL TORNEO --");
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

            }catch (Exception e1){
                IO.println(e1.getMessage());
            }
        }

        return all;
    }


}
