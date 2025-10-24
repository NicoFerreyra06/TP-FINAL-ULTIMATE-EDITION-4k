package Modelo.Competicion;

import Modelo.Equipo.Equipo;
import Interfaces.iToJSON;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;

public abstract class Torneo implements iToJSON{
    protected String nombre;
    protected LinkedHashMap<String, Equipo> equiposTorneo;

    public Torneo(String nombre) {
        this.nombre = nombre;
        this.equiposTorneo = new LinkedHashMap<>();
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("nombre", this.nombre);

        // Convertir el HashMap de equipos en un JSONArray
        JSONArray jsonEquipos = new JSONArray();
        for (Equipo equipo : this.equiposTorneo.values()) {
            jsonEquipos.put(equipo.toJSON()); // Llama a Equipo.toJSON()
        }

        json.put("equiposTorneo", jsonEquipos);
        return json;
    }

    public Torneo(JSONObject json) {
        this.nombre = json.getString("nombre");
        this.equiposTorneo = new LinkedHashMap<>();

        JSONArray jsonEquipos = json.getJSONArray("equiposTorneo");
        for (int i = 0; i < jsonEquipos.length(); i++) {
            JSONObject jsonEquipo = jsonEquipos.getJSONObject(i);
            Equipo equipoCargado = new Equipo(jsonEquipo); // Llama a Equipo(json)
            this.equiposTorneo.put(equipoCargado.getNombre(), equipoCargado);
        }
    }
    // ==================== Getters y Setters ====================
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public HashMap<String, Equipo> getEquipos() {
        return equiposTorneo;
    }

    // ==================== Metodos ====================
    public abstract void jugarProximaFecha(Equipo equipoJugador) throws InterruptedException;

    public boolean anotarEquipo (Equipo equipo) {
        if (equipo == null) return false;

        if (equiposTorneo.containsKey(equipo.getNombre())) {
            System.out.println("Equipo ya inscripto");
            return false;
        }
        equiposTorneo.put(equipo.getNombre(), equipo);
        return true;
    }


}
