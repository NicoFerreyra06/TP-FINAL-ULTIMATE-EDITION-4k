package Modelo;
import enums.*;

import org.json.JSONObject;

public class Tactica {
    private Eformacion eformacion;
    private EstiloJuego estiloJuego;

    public Tactica(Eformacion eformacion, EstiloJuego estiloJuego) {
        this.eformacion = eformacion;
        this.estiloJuego = estiloJuego;
    }

    //==================== JSON ====================
    public Tactica (JSONObject json){
        // Lee el valor String asociado a la clave "eformacion"
        String formacionStr = json.getString("eformacion");
        // Convierte el String de nuevo al tipo enum Eformacion
        this.eformacion = Eformacion.valueOf(formacionStr);

        // Lee el valor String asociado a la clave "estiloJuego"
        String estiloStr = json.getString("estiloJuego");
        // Convierte el String de nuevo al tipo enum EstiloJuego
        this.estiloJuego = EstiloJuego.valueOf(estiloStr);
    }

    public JSONObject toJSON(){
        JSONObject jsonTactica = new JSONObject();
        jsonTactica.put("eformacion", eformacion.name());
        jsonTactica.put("estiloJuego", estiloJuego.name());
        return jsonTactica;
    }

    // ==================== Getters y Setters ====================
    public Eformacion getEformacion() {
        return eformacion;
    }

    public void setEformacion(Eformacion eformacion) {
        this.eformacion = eformacion;
    }

    public EstiloJuego getEstiloJuego() {
        return estiloJuego;
    }

    public void setEstiloJuego(EstiloJuego estiloJuego) {
        this.estiloJuego = estiloJuego;
    }

    @Override
    public String toString() {
        return String.format("Táctica: %-7s | Estilo: %s",
                this.eformacion,
                this.estiloJuego
        );
    }
}