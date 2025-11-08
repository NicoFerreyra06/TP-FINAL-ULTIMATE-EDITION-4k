package Modelo.Competicion;

import Modelo.Equipo.Equipo;
import Modelo.Persona.Jugador;
import Modelo.pPartido.Partido;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class Ronda
{
    private String nombre;
    private ArrayList <Partido> partidos;
    private ArrayList<Equipo> ganadoresDeLaRonda;

    //Constructor
    public Ronda(String nombre, ArrayList<Partido> partidos) {
        this.nombre = nombre;
        this.partidos = partidos;
        this.ganadoresDeLaRonda = null; //Se le da valor cuando se llame getGanadores()
    }

    public Ronda(JSONObject json) {
        this.nombre = json.getString("nombre");
        this.partidos = new ArrayList<>();

        JSONArray jsonPartidos = json.getJSONArray("partidos");
        for (int i = 0; i < jsonPartidos.length(); i++) {
            JSONObject jsonPartido = jsonPartidos.getJSONObject(i);

            Partido partidoCargado = new Partido(jsonPartido);
            this.partidos.add(partidoCargado);
        }
    }

    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nombre", this.nombre);

        JSONArray jsonPartidos = new JSONArray();

        for (Partido partido : this.partidos) {
            jsonPartidos.put(partido.toJson());
        }

        jsonObject.put("partidos", jsonPartidos);
        return jsonObject;
    }


    //Simula una tanda de penales hasta obtener un unico ganador
    private Equipo tandaDePenales(Equipo local, Equipo visitante, Equipo equipoJugador) throws InterruptedException {
        boolean esPartidoDelJugador = local.equals(equipoJugador) || visitante.equals(equipoJugador);

        if (esPartidoDelJugador) {
            System.out.println("¡COMIENZA LA TANDA DE PENALES ENTRE " + local.getNombre() + " Y " + visitante.getNombre() + "!");
            Thread.sleep(500);
        }

        int golesLocal = 0;
        int golesVisitante = 0;
        Random rand = new Random();
        ArrayList<Jugador> pateadoresLocales = new ArrayList<>(local.getTitulares());
        ArrayList<Jugador> pateadoresVisitantes = new ArrayList<>(visitante.getTitulares());

        if (pateadoresLocales.isEmpty() || pateadoresVisitantes.isEmpty()) {
            System.out.println("Uno de los equipos no tiene jugadores para patear. Se decidirá por sorteo.");
            return rand.nextBoolean() ? local : visitante;
        }

        // --- Tanda de 5 penales ---
        for (int i = 1; i <= 5; i++) {
            // Simula el penal del local
            Jugador pateadorLocal = pateadoresLocales.get(rand.nextInt(pateadoresLocales.size()));
            boolean golLocal = rand.nextDouble() < pateadorLocal.getHabilidadAtaque() / 125.0;
            if (golLocal) golesLocal++;

            // Simula el penal del visitante
            Jugador pateadorVisitante = pateadoresVisitantes.get(rand.nextInt(pateadoresVisitantes.size()));
            boolean golVisitante = rand.nextDouble() < pateadorVisitante.getHabilidadAtaque() / 125.0;
            if (golVisitante) golesVisitante++;

            // Muestra el detalle SOLO si es el partido del jugador
            if (esPartidoDelJugador) {
                System.out.println("\n--- Penal #" + i + " ---");
                Thread.sleep(250);
                System.out.println(golLocal ? "¡GOL de " + local.getNombre() + "!" : "¡FALLÓ " + local.getNombre() + "!");
                Thread.sleep(250);
                System.out.println(golVisitante ? "¡GOL de " + visitante.getNombre() + "!" : "¡FALLÓ " + visitante.getNombre() + "!");
                Thread.sleep(250);
                System.out.println("Resultado parcial: " + local.getNombre() + " " + golesLocal + " - " + visitante.getNombre() + " " + golesVisitante);
                Thread.sleep(250);
            }
        }

        // --- Muerte súbita si siguen empatados ---
        if (golesLocal == golesVisitante) {
            if (esPartidoDelJugador) System.out.println("\n--- ¡MUERTE SÚBITA! ---");
            while (golesLocal == golesVisitante) {
                if (rand.nextDouble() < pateadoresLocales.get(rand.nextInt(pateadoresLocales.size())).getHabilidadAtaque() / 125.0) golesLocal++;
                if (rand.nextDouble() < pateadoresVisitantes.get(rand.nextInt(pateadoresVisitantes.size())).getHabilidadAtaque() / 125.0) golesVisitante++;
            }
        }

        if (esPartidoDelJugador) {
            System.out.println("Resultado final de la tanda: " + local.getNombre() + " " + golesLocal + " - " + visitante.getNombre() + " " + golesVisitante);
        }

        return (golesLocal > golesVisitante) ? local : visitante;
    }

    
    //Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Partido> getPartidos() {
        return partidos;
    }

    public void setPartidos(ArrayList<Partido> partidos) {
        this.partidos = partidos;
    }

    public ArrayList<Equipo> getGanadoresDeLaRonda() {
        return ganadoresDeLaRonda;
    }

    //El metodo getGanadores ahora guarda el resultado antes de devolverlo
    public ArrayList<Equipo> getGanadores(Equipo equipoJugador) throws InterruptedException {
        ArrayList<Equipo> ganadores = new ArrayList<>();
        for (Partido partido : this.partidos) {
            Equipo ganador = partido.getGanador();
            if (ganador == null) {
                ganador = tandaDePenales(partido.getLocal(), partido.getVisitante(), equipoJugador);
            }
            ganadores.add(ganador);
        }
        //Guardamos la lista de ganadores en el atributo.
        this.ganadoresDeLaRonda = ganadores;
        return ganadores;
    }
}
