package Modelo.Competicion;

import Modelo.Equipo.Equipo;
import Interfaces.iToJSON;
import org.json.JSONObject;

public class FilaTabla implements Comparable<FilaTabla>, iToJSON{

    public Equipo equipo;
    public int puntos;
    public int jugados;
    public int ganados;
    public int empatados;
    public int perdidos;
    public int golesFavor;
    public int golesContra;
    public int diferenciaGoles;

    private String nombreEquipoCargado;

    public FilaTabla(Equipo equipo) {
        this.equipo = equipo;
        this.puntos = 0;
        this.jugados = 0;
        this.ganados = 0;
        this.empatados = 0;
        this.perdidos = 0;
        this.golesFavor = 0;
        this.golesContra = 0;
        this.diferenciaGoles = 0;
    }

    public FilaTabla(JSONObject json) {

        this.nombreEquipoCargado = json.getString("nombreEquipo");

        // Cargamos todas las estadísticas
        this.puntos = json.getInt("puntos");
        this.jugados = json.getInt("jugados");
        this.ganados = json.getInt("ganados");
        this.empatados = json.getInt("empatados");
        this.perdidos = json.getInt("perdidos");
        this.golesFavor = json.getInt("golesFavor");
        this.golesContra = json.getInt("golesContra");
        this.diferenciaGoles = json.getInt("diferenciaGoles");
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put("nombreEquipo", this.equipo.getNombre());

        json.put("puntos", this.puntos);
        json.put("jugados", this.jugados);
        json.put("ganados", this.ganados);
        json.put("empatados", this.empatados);
        json.put("perdidos", this.perdidos);
        json.put("golesFavor", this.golesFavor);
        json.put("golesContra", this.golesContra);
        json.put("diferenciaGoles", this.diferenciaGoles);

        return json;
    }

    public void registrarPartido (int golesFavor, int golesContra) {
        this.jugados++;
        this.golesFavor += golesFavor;
        this.golesContra += golesContra;

        this.diferenciaGoles += golesFavor - golesContra;

        if (golesFavor > golesContra) {
            this.ganados++;
            this.puntos += 3;
        } else if (golesFavor == golesContra) {
            this.empatados++;
            this.puntos += 1;
        } else {
            this.perdidos++;
        }
    }

    @Override
    public int compareTo(FilaTabla otraFila) {
        // 1. Por Puntos (descendente)
        if (this.puntos != otraFila.puntos) {
            return Integer.compare(otraFila.puntos, this.puntos);
        }
        // 2. Por Diferencia de Goles (descendente)
        if (this.diferenciaGoles != otraFila.diferenciaGoles) {
            return Integer.compare(otraFila.diferenciaGoles, this.diferenciaGoles);
        }
        // 3. Por Goles a Favor (descendente)
        return Integer.compare(otraFila.golesFavor, this.golesFavor);
    }

    public String getNombreEquipoCargado() {
        return nombreEquipoCargado;
    }

    public void setNombreEquipoCargado(String nombreEquipoCargado) {
        this.nombreEquipoCargado = nombreEquipoCargado;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }
}
