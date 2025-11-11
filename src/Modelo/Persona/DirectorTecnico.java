package Modelo.Persona;
import enums.*;
import Modelo.Equipo.*;
import org.json.JSONObject;
import Interfaces.iToJSON;

public class DirectorTecnico extends Persona implements iToJSON {
    private int experiencia; //0 a 100
    private Tactica tacticaPreferida;

    public DirectorTecnico(String nombre, int edad, String nacionalidad, int experiencia, Tactica tacticaPreferida) {
        super(nombre, edad, nacionalidad);
        this.experiencia = experiencia;
        this.tacticaPreferida = tacticaPreferida;
    }

    //==================== JSON ====================
    public DirectorTecnico (JSONObject json){
        //Clase padre
        super(json.getString("nombre"), json.getInt("edad"), json.getString("nacionalidad"));

        this.experiencia = json.getInt("experiencia");

        JSONObject jsonDT = json.getJSONObject("tacticaPreferida");
        this.tacticaPreferida = new Tactica(jsonDT);
    }

    public JSONObject toJSON(){
        JSONObject jsonDT = new JSONObject();
        jsonDT.put("nombre", this.nombre);
        jsonDT.put("edad", this.edad);
        jsonDT.put("nacionalidad", this.nacionalidad);
        jsonDT.put("experiencia", this.experiencia);
        //llama al toJson() de Tactica
        jsonDT.put("tacticaPreferida", this.tacticaPreferida.toJSON());
        return jsonDT;
    }

    // ==================== Getters y Setters ====================
    public int getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(int experiencia) {
        this.experiencia = experiencia;
    }

    public Tactica getTacticaPreferida() {
        return tacticaPreferida;
    }

    public void setTacticaPreferida(Tactica tacticaPreferida) {
        this.tacticaPreferida = tacticaPreferida;
    }

    @Override
    public String toString() {
        return String.format("%-20s | Táctica: %s (%s)",
                this.getNombre(),
                this.tacticaPreferida.getEformacion(),
                this.tacticaPreferida.getEstiloJuego()
        );
    }
}