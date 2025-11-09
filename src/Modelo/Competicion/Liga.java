package Modelo.Competicion;

import Exceptions.LimiteEntrenamientoException;
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
        super(json); // 1. Carga nombre y equiposTorneo
        this.jornada = json.getInt("jornada");
        this.nombreEquipoUsuario = json.getString("equipoUsuario");

        this.fixture = new ArrayList<>();

        // 2. Carga y reconecta la Tabla de Posiciones
        this.tablaPosiciones = new HashMap<>();
        JSONArray jsonTablas = json.getJSONArray("tablaPosiciones");

        for (int i = 0; i < jsonTablas.length(); i++) {
            JSONObject jsonFila = jsonTablas.getJSONObject(i);

            // 3. Crea la Fila
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

        // 3. Carga y reconstruye el Fixture
        this.fixture = new ArrayList<>();
        JSONArray jsonFixture = json.getJSONArray("fixture");

        for (int i = 0; i < jsonFixture.length(); i++) {
            JSONObject jsonPartido = jsonFixture.getJSONObject(i);

            // Obtenemos los nombres de los equipos
            String nombreLocal = jsonPartido.getString("local");
            String nombreVisitante = jsonPartido.getString("visitante");

            // Buscamos los objetos 'Equipo' reales en el mapa que ya cargamos
            Equipo equipoLocal = super.equiposTorneo.get(nombreLocal);
            Equipo equipoVisitante = super.equiposTorneo.get(nombreVisitante);

            // Si encontramos ambos equipos, reconstruimos el partido
            if (equipoLocal != null && equipoVisitante != null) {
                Partido partidoCargado = new Partido(equipoLocal, equipoVisitante);
                this.fixture.add(partidoCargado);
            }
        }
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = super.toJSON();

        json.put("jornada", this.jornada);
        json.put("equipoUsuario", this.nombreEquipoUsuario);

        JSONArray jsonTabla = new JSONArray();

        for (FilaTabla fila : this.tablaPosiciones.values()) {
            jsonTabla.put(fila.toJSON());
        }

        // Guardamos el array completo en el JSON
        json.put("tablaPosiciones", jsonTabla);

        JSONArray jsonFixture = new JSONArray();
        for (Partido partido : this.fixture) {
            // Creamos un JSON simple para CADA partido
            JSONObject jsonPartido = new JSONObject();
            jsonPartido.put("local", partido.getLocal().getNombre());
            jsonPartido.put("visitante", partido.getVisitante().getNombre());
            jsonFixture.put(jsonPartido);
        }
        // Guardamos el array completo de partidos
        json.put("fixture", jsonFixture);

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

    public String getNombreEquipoUsuario() {
        return nombreEquipoUsuario;
    }

    public void generarFixture() {
        ArrayList<Equipo> equipos = new ArrayList<>(super.getEquipos().values());
        Collections.shuffle(equipos);
        int numEquipos = equipos.size();
        int numJornadas = numEquipos - 1;

        // Crear tabla de posiciones si es nueva
        if (tablaPosiciones.isEmpty()) {
            System.out.println("Creando tabla de posiciones para liga nueva...");
            for (Equipo e : equipos) tablaPosiciones.put(e, new FilaTabla(e));
        }

        // Separar equipo fijo y rotativos
        Equipo fijo = equipos.getFirst();
        List<Equipo> rotativos = new ArrayList<>(equipos.subList(1, numEquipos));

        fixture = new ArrayList<>();

        // Generar partidos de IDA
        for (int j = 0; j < numJornadas; j++) {
            // Partido del equipo fijo (balanceado)
            Equipo rival = rotativos.getFirst();
            if (j % 2 == 0)
                fixture.add(new Partido(rival, fijo)); // fijo visitante
            else
                fixture.add(new Partido(fijo, rival)); // fijo local

            // Emparejar el resto
            int mitad = (numEquipos / 2) - 1;
            for (int i = 0; i < mitad; i++) {
                Equipo local = rotativos.get(i + 1);
                Equipo visitante = rotativos.get(rotativos.size() - 1 - i);
                fixture.add(new Partido(local, visitante));
            }

            // Rotar equipos
            rotativos.addFirst(rotativos.removeLast());
        }

        // Generar partidos de VUELTA (local/visitante invertidos)
        ArrayList<Partido> fixtureVuelta = new ArrayList<>();
        for (Partido p : fixture) {
            fixtureVuelta.add(new Partido(p.getVisitante(), p.getLocal()));
        }

        // Unir ida + vuelta
        fixture.addAll(fixtureVuelta);
    }

    @Override
    public void jugarProximaFecha(Equipo equipoJugador, Scanner sc) throws InterruptedException, LimiteEntrenamientoException{
        if (jornada > (super.getEquipos().size() - 1) * 2) {
            IO.println("LA LIGA HA FINALIZADO");
            mostrarTabla();
            return;
        }

        ArrayList<Partido> partidosJornada = getPartidosFecha(this.jornada);
        ArrayList<Partido> partidosRapidos = new ArrayList<>();

        for (Partido p : partidosJornada) {
            if (p.involucraEquipoUsuario(equipoJugador)) {
                p.simularInteractivo(equipoJugador, sc);
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
