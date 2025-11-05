package Modelo.Equipo;

import Interfaces.iToJSON;
import Modelo.Persona.DirectorTecnico;
import Modelo.Persona.Jugador;
import enums.Posicion;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public class Equipo implements iToJSON {
    private String nombre;
    private Estadio estadio;
    private DirectorTecnico tecnico;
    private double presupuesto;
    private HashSet<Jugador> titulares;
    private HashSet<Jugador> suplentes;
    private LinkedHashSet <Jugador> Expulsados;
    private int puntos;
    private final Random random;
    private boolean jugoJornada;

    public Equipo() {
        random = new Random();
    }

    public Equipo(String nombre, Estadio estadio, DirectorTecnico tecnico, double presupuesto) {
        this.nombre = nombre;
        this.estadio = estadio;
        this.tecnico = tecnico;
        this.presupuesto = presupuesto;
        this.titulares = new HashSet<>();
        this.suplentes = new HashSet<>();
        this.Expulsados = new LinkedHashSet <>();
        this.puntos = 0;
        this.random = new Random();
    }
    //==================== JSON ====================
    public Equipo (JSONObject json){
        this.nombre = json.getString("nombre");

        JSONObject estadio = json.getJSONObject("estadio");
        this.estadio = new Estadio(estadio);

        this.presupuesto = json.getDouble("presupuesto");
        this.puntos = json.getInt("puntos");
        this.random = new Random();

        JSONObject jsonTecnico = json.getJSONObject("tecnico");
        this.tecnico = new DirectorTecnico(jsonTecnico);

        //reconstruir lista titulares
        this.titulares = new HashSet<>();
        JSONArray jsonTitulares = json.getJSONArray("titulares");
        for (int i = 0; i < jsonTitulares.length(); i++) {
            JSONObject jsonJugador = jsonTitulares.getJSONObject(i);
            this.titulares.add(new Jugador(jsonJugador));
        }

        //reconstruir lista suplentes
        this.suplentes = new HashSet<>();
        JSONArray jsonSuplentes = json.getJSONArray("suplentes");
        for (int i = 0; i < jsonSuplentes.length(); i++) {
            JSONObject jsonJugador = jsonSuplentes.getJSONObject(i);
            this.suplentes.add(new Jugador(jsonJugador));
        }

        //reconstruir lista expulsados
        this.Expulsados = new LinkedHashSet<>();
        JSONArray jsonExpulsados = json.getJSONArray("expulsados");
        for (int i = 0; i < jsonExpulsados.length(); i++) {
            JSONObject jsonJugador = jsonExpulsados.getJSONObject(i);
            this.Expulsados.remove(new Jugador(jsonJugador));
        }
    }

    public JSONObject toJSON(){
        JSONObject obj = new JSONObject();
        obj.put("nombre", nombre);
        obj.put("tecnico", tecnico.toJson());
        obj.put("estadio", estadio.toJson());
        obj.put("presupuesto", presupuesto);
        obj.put("puntos", puntos);

        //Convertir HashSet en un JSONArray
        JSONArray jsonTitulares = new JSONArray();
        for (Jugador jugador : titulares) {
            jsonTitulares.put(jugador.toJSON());
        }
        obj.put("titulares", jsonTitulares);

        //Convertir HashSet en un JSONArray
        JSONArray jsonSuplentes = new JSONArray();
        for (Jugador jugador : suplentes) {
            jsonSuplentes.put(jugador.toJSON());
        }
        obj.put("suplentes", jsonSuplentes);

        //Convertir Stack en un JSONArray
        JSONArray jsonExpulsados = new JSONArray();
        for (Jugador jugador : Expulsados) {
            jsonExpulsados.put(jugador.toJSON());
        }
        obj.put("expulsados", jsonExpulsados);

        return obj;
    }
    // ==================== Getters y Setters ====================
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Estadio getEstadio() {
        return estadio;
    }

    public void setEstadio(Estadio estadio) {
        this.estadio = estadio;
    }

    public DirectorTecnico getTecnico() {
        return tecnico;
    }

    public void setTecnico(DirectorTecnico tecnico) {
        this.tecnico = tecnico;
    }

    public double getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(double presupuesto) {
        this.presupuesto = presupuesto;
    }

    public HashSet<Jugador> getTitulares() {
        return titulares;
    }

    public void setTitulares(HashSet<Jugador> titulares) {
        this.titulares = titulares;
    }

    public HashSet<Jugador> getSuplentes() {
        return suplentes;
    }

    public void setSuplentes(HashSet<Jugador> suplentes) {
        this.suplentes = suplentes;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public LinkedHashSet<Jugador> getExpulsados() {
        return Expulsados;
    }

    public void setExpulsados(LinkedHashSet<Jugador> expulsados) {
        Expulsados = expulsados;
    }

    public boolean isJugoJornada() {
        return jugoJornada;
    }

    public void setJugoJornada(boolean jugoJornada) {
        this.jugoJornada = jugoJornada;
    }


    // ===================Metodos=======================

    /**
     * Agrega jugador al equipo asegurandose que no haya duplicados
     * Si no existe, intenta agregarlo a la lista de titulares. Si esta ya tiene 11 jugadores,
     * lo agrega a la lista de suplentes.
     *
     * @return true si se agrego, falso si no.
     */

    public boolean agregarJugador(Jugador jugador) {
        if (jugador == null) return false;
        if (titulares.contains(jugador) || suplentes.contains(jugador)) {
            return false;
        }

        if (titulares.size() < 11) {
            return titulares.add(jugador);
        } else {
            return suplentes.add(jugador);
        }
    }

    /**
     * Calcula la media de habilidad general del equipo, basándose en el
     * promedio de habilidad de sus jugadores titulares.
     *
     * @return La valoración general del equipo en una escala de 0 a 100.
     */

    public double calcularMediaGeneral() {
        if (titulares.isEmpty()) return 0.0;
        double media = 0;
        for (Jugador jugador : titulares) {
            media += jugador.calcularMediaJugador();
        }
        return media / this.titulares.size();
    }


    private Jugador getJugadorAzar() {
        if (titulares.isEmpty()) return null;

        // 1. Convertimos el HashSet a un ArrayList.
        ArrayList<Jugador> listaTitulares = new ArrayList<>(titulares);

        // 2. Ahora sí podemos usar .get() para seleccionar un jugador al azar.
        return listaTitulares.get(random.nextInt(listaTitulares.size()));
    }


    /**
     * Selecciona un jugador titular para ser el autor de un gol mediante un sorteo probabilístico.
     * El metodo funciona en un bucle que garantiza la selección de un goleador. En cada iteración:
     * Se elige un jugador titular completamente al azar.
     * Se le asigna una probabilidad de anotar basada estrictamente en su posición en el campo.
     */

    public Jugador elegirAutorGol() {
        // Si no hay jugadores, no se puede elegir a nadie.
        if (titulares.isEmpty()) {
            return null;
        }

        // Bucle infinito que se rompe solo cuando se elija un goleador.
        while (true) {
            //Selecciona un jugador completamente al azar.
            Jugador candidato = getJugadorAzar();
            double probabilidadDeAnotar = 0;

            //Dependiendo de la posicion del "candidato" hay mas probabilidad de meter gol
            switch (candidato.getPosicion()) {
                case DELANTERO -> probabilidadDeAnotar = 0.46;
                case MEDIOCAMPISTA -> probabilidadDeAnotar = 0.3;
                case DEFENSOR -> probabilidadDeAnotar = 0.2;
                case ARQUERO -> probabilidadDeAnotar = 0.02;
            }

            // Si el número al azar es menor que su probabilidad de
            if (random.nextDouble() < probabilidadDeAnotar) {
                return candidato; // Se encontró al goleador, salimos del bucle.
            }
        }
    }

    /**
     * Convierte nuestro Hashset de titulares
     * en un arraylist para seleccionar
     * un jugador al azar.
     *
     * @return {@link Jugador}
     */

    public Jugador elegirAutorAsistencia(Jugador goleador) {
        //Funcion para probabilidad de cometer falta

        if (titulares.isEmpty()) {
            return null;
        }
        while (true) {
            Jugador asistidor = getJugadorAzar();

            if (!(asistidor.equals(goleador)) && asistidor.getPosicion() != Posicion.ARQUERO) {
                return asistidor;
            }
        }
    }


    /**
     * Funcion para probabilidad de cometer falta
     */

    public Jugador elegirAutorFalta() {

        if (titulares.isEmpty()) {
            return null;
        }

        while (true) {

            Jugador candidato = getJugadorAzar();
            double probabilidadDeFalta = 0;
            switch (candidato.getPosicion()) {
                case DEFENSOR -> probabilidadDeFalta = 0.45;
                case MEDIOCAMPISTA -> probabilidadDeFalta = 0.30;
                case DELANTERO -> probabilidadDeFalta = 0.20;
                case ARQUERO -> probabilidadDeFalta = 0.05;
            }

            if (random.nextDouble() < probabilidadDeFalta) {
                return candidato; // Se encontro al que cometio la falta.
            }

        }
    }

    /**
     * Funcion que verifica cuantas tarjetas tiene el jugador
     * Si son dos amarillas afuera, si es una roja afuera
     */

    public void ExpulsarJugador(Jugador expulsado) {

        if (expulsado.getTarjetaLiga() == 2) {

            titulares.remove(expulsado);
            Expulsados.add(expulsado);

        }
    }

    /**
     * Por cada partido que pasa se baja la tarjeta
     * De esa forma cuando el contador llegue a 0
     * El jugador expulsado podra volver a jugar
     */

    public void bajarSancion() {

        Iterator<Jugador> it = Expulsados.iterator();

        while (it.hasNext()) {
            Jugador expulsado = it.next();

            expulsado.setTarjetaLiga(expulsado.getTarjetaLiga() - 1);

            if (expulsado.getTarjetaLiga() <= 0) {
                it.remove();

                suplentes.add(expulsado);
            }
        }
    }


    //-------Mostrar jugadores Expulsados-------

    public void mostrarExpulsados(){
        for (Jugador jugador : Expulsados) {

            System.out.println("- " + jugador.getNombre());
        }
    }

    /**
     * Metodo para que el usuario pueda
     * realizar los cambios que deseé
     */

    public void realizarCambio(Jugador saleTitular, Jugador saleSuplente) {

        if (titulares.contains(saleTitular) && suplentes.contains(saleSuplente)) {

            suplentes.remove(saleSuplente);
            titulares.remove(saleTitular);
            titulares.add(saleSuplente);
            suplentes.add(saleTitular);

            System.out.println("⬇ Sale: " + saleTitular.getNombre());
            System.out.println("⬆ Entra: " + saleSuplente.getNombre());

        }
    }

    /**
     * Si los titulares no son 11
     * Se agregara uno del banco
     */

    public void verificarTitulares() {

        Iterator<Jugador> it = suplentes.iterator();
        while (it.hasNext() && titulares.size() < 11) {
            Jugador j = it.next();
            it.remove();
            titulares.add(j);
        }

    }


    //---------Equals & HashCode---------
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Equipo equipo = (Equipo) o;
        return Objects.equals(nombre, equipo.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nombre);
    }

    public void entrenarEquipo (){
        System.out.println("Entrenando a " + this.nombre);

        for (Jugador titular : this.titulares) {
            titular.entrenar();
        }

        for (Jugador suplente : this.suplentes) {
            suplente.entrenar();
        }
        System.out.println(" ==========Sesion de entrenamiento exitosa========== ");
    }

    public ArrayList<Jugador> getPlantilla () {
        ArrayList<Jugador> plantilla = new ArrayList<>(titulares.size() + suplentes.size());

        plantilla.addAll(titulares);
        plantilla.addAll(suplentes);

        return plantilla;
    }

    //---------metodo toString---------

    @Override
    public String toString() {
        return "Equipo: " + nombre + "\n" +
                "  - Estadio: " + estadio.getNombre() + "\n" +
                "  - Director Técnico: " + tecnico.getNombre() + "\n" +
                "  - Presupuesto: $" + presupuesto + "\n" +
                "  - Puntos: " + puntos + "\n" +
                "  - Jugadores Titulares: " + titulares.size() + "\n" +
                "  - Jugadores Suplentes: " + suplentes.size();
    }

}


