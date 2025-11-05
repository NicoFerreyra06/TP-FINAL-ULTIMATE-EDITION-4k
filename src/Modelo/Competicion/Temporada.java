package Modelo.Competicion;

import Interfaces.iToJSON;
import org.json.JSONObject;

public final class Temporada implements iToJSON {
    private int anio;
    private Copa copa;
    private Liga liga;

    //Constructor
    public Temporada(int anio, Copa copa, Liga liga) {
        this.anio = anio;
        this.copa = copa;
        this.liga = liga;
    }

    public Temporada (JSONObject jsonObject){
        this.anio = jsonObject.getInt("anio");
        //JSON de copa no implementada
        //this.copa = new Copa(jsonObject.getString("copa"));
        this.liga = new Liga(jsonObject.getString("liga"));
    }

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("anio", this.anio);
        jsonObject.put("liga", this.liga.toJSON());
        return jsonObject;
    }

    //Getters y Setters
    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public Copa getCopa() {
        return copa;
    }

    public void setCopa(Copa copa) {
        this.copa = copa;
    }

    public Liga getLiga() {
        return liga;
    }

    public void setLiga(Liga liga) {
        this.liga = liga;
    }
}
