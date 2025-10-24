package Modelo.Competicion;

import Modelo.Equipo.Equipo;
import Modelo.pPartido.Partido;
import Interfaces.*;
import org.json.JSONArray;
import org.json.JSONObject;
import Gestora.*;
import java.util.*;

public class Liga extends Torneo implements iToJSON{
    private int jornada;
    private ArrayList<Partido> fixture;
    private Map <Equipo, FilaTabla> tablaPosiciones;

    private String nombreEquipoUsuario;

    public Liga(String nombre) {
        super(nombre);
        this.jornada = 1;
        this.fixture = new ArrayList<>();
        this.tablaPosiciones = new HashMap<>();
    }

    public Liga (JSONObject json){
        super(json); // 1. Carga 'nombre' y 'equiposTorneo'
        this.jornada = json.getInt("jornada");

        this.nombreEquipoUsuario = json.getString("equipoUsuario");

        // 2. Carga y reconecta la Tabla de Posiciones
        this.tablaPosiciones = new HashMap<>();
        JSONArray jsonTablas = json.getJSONArray("tablaPosiciones");

        for (int i = 0; i < jsonTablas.length(); i++) {
            JSONObject jsonFila = jsonTablas.getJSONObject(i);

            // 3. Crea la Fila (que guarda stats y el nombre temporal)
            FilaTabla filaCargada = new FilaTabla(jsonFila);

            // 4. Obtiene el nombre guardado
            String nombreEquipo = filaCargada.getNombreEquipoCargado();

            // 5. Busca el Equipo REAL en el mapa que cargó 'super(json)'
            Equipo equipoReal = super.equiposTorneo.get(nombreEquipo);

            if (equipoReal != null) {
                filaCargada.setEquipo(equipoReal);
                this.tablaPosiciones.put(equipoReal, filaCargada);
            }
        }
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = super.toJSON();

        json.put("jornada", this.jornada); // Clave: "jornada"

        json.put("equipoUsuario", this.nombreEquipoUsuario);

        JSONArray jsonTabla = new JSONArray();

        for (FilaTabla fila : this.tablaPosiciones.values()) {
            // Llama a fila.toJSON() (que guarda los stats y el nombreEquipo)
            jsonTabla.put(fila.toJSON());
        }

        // Guardamos el array completo en el JSON
        json.put("tablaPosiciones", jsonTabla);

        return json;
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

    public void setNombreEquipoUsuario(String nombreEquipoUsuario) {
        this.nombreEquipoUsuario = nombreEquipoUsuario;
    }

    // (Y este es el 'get' que usará el main para buscar tu equipo)
    public String getNombreEquipoUsuario() {
        return nombreEquipoUsuario;
    }

    public void generarFixture() {
        // 1. Obtener la lista de equipos y manejar N° impar
        ArrayList<Equipo> equipos = new ArrayList<>(super.getEquipos().values());

        if (equipos.size() % 2 != 0) {
            Equipo equipoBye = new Equipo();
            equipoBye.setNombre("FANTASMA");
            equipos.add(equipoBye);
        }

        if (this.tablaPosiciones.isEmpty()) {
            System.out.println("Creando tabla de posiciones para liga nueva...");
            for (Equipo equipo : equipos) {
                if (!equipo.getNombre().equals("FANTASMA")) {
                    this.tablaPosiciones.put(equipo, new FilaTabla(equipo));
                }
            }
        }

        int numEquipos = equipos.size();
        int numJornadasIda = numEquipos - 1;


        // 3. Separar el equipo fijo y la lista de rotación
        Equipo equipoFijo = equipos.get(0);
        // Creamos una sublista con el resto de equipos.
        List<Equipo> equiposRotativos = new ArrayList<>(equipos.subList(1, numEquipos));

        ArrayList<Partido> fixtureIda = new ArrayList<>();
        ArrayList<Partido> fixtureVuelta = new ArrayList<>();

        // 4. Generar todas las jornadas de "IDA"
        for (int jornada = 0; jornada < numJornadasIda; jornada++) {

            // El equipo fijo juega contra el primer equipo de la lista rotativa
            Equipo rivalFijo = equiposRotativos.get(0);

            // Balanceo simple de local/visitante para el equipo fijo
            if (jornada % 2 == 0) {
                fixtureIda.add(new Partido(rivalFijo, equipoFijo)); // Fijo es visitante
            } else {
                fixtureIda.add(new Partido(equipoFijo, rivalFijo)); // Fijo es local
            }

            // Emparejar al resto de la lista "desde afuera hacia adentro"
            int mitad = (numEquipos / 2) - 1; // N° de partidos restantes en la jornada
            for (int i = 0; i < mitad; i++) {
                Equipo local = equiposRotativos.get(i + 1);
                Equipo visitante = equiposRotativos.get(equiposRotativos.size() - 1 - i);
                fixtureIda.add(new Partido(local, visitante));
            }

            // 5. ROTAR la lista para la siguiente jornada
            // Guardamos el último equipo
            Equipo ultimo = equiposRotativos.remove(equiposRotativos.size() - 1);
            // Lo insertamos al principio (posición 0), desplazando al resto
            equiposRotativos.add(0, ultimo);
        }

        // 6. Generar la "VUELTA" invirtiendo los partidos de ida
        for (Partido partidoIda : fixtureIda) {
            // Solo añadimos partidos reales (ninguno es FANTASMA)
            if (!partidoIda.getLocal().getNombre().equals("FANTASMA") &&
                    !partidoIda.getVisitante().getNombre().equals("FANTASMA"))
            {
                // Creamos el partido de vuelta invirtiendo local y visitante
                fixtureVuelta.add(new Partido(partidoIda.getVisitante(), partidoIda.getLocal()));
            }
        }

        // 7. Limpiar los partidos "FANTASMA" de la lista de IDA
        ArrayList<Partido> fixtureIdaLimpio = new ArrayList<>();
        for(Partido p : fixtureIda) {
            if (!p.getLocal().getNombre().equals("FANTASMA") &&
                    !p.getVisitante().getNombre().equals("FANTASMA"))
            {
                fixtureIdaLimpio.add(p);
            }
        }

        // 8. Asignar el fixture total
        this.fixture = new ArrayList<>();
        this.fixture.addAll(fixtureIdaLimpio);
        this.fixture.addAll(fixtureVuelta);
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

        //Guarda la partida despues de cada jornada
        String nombreArchivo = "partida_guardada.json";
        JsonUtiles.grabarUnJson(toJSON(), nombreArchivo);
        System.out.println("===== Partida guardada en: " + nombreArchivo + " =====");

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


    public void podioLiga() {
        List<FilaTabla> filasOrdenadas = new ArrayList<>(tablaPosiciones.values());
        Collections.sort(filasOrdenadas);

        System.out.println("🏆 PODIO DE LA LIGA 🏆");
        System.out.println("------------------------");
        System.out.println("🥇 1° " + filasOrdenadas.get(0).equipo.getNombre());
        System.out.println("🥈 2° " + filasOrdenadas.get(1).equipo.getNombre());
        System.out.println("🥉 3° " + filasOrdenadas.get(2).equipo.getNombre());
        System.out.println("------------------------");

    }

    //Metodo para saber si la liga esta terminada
    public boolean isTerminada (){
        int numeroEquipos = super.getEquipos().size();
        int jornadasTotales;

        // Si hay cantidad impar, uno queda libre por fecha
        if (numeroEquipos % 2 != 0) {
            jornadasTotales = numeroEquipos * 2;
        } else {
            jornadasTotales = (numeroEquipos - 1) * 2;
        }

        return this.jornada > jornadasTotales;
    }
}
