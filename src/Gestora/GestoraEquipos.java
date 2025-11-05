package Gestora;

import Modelo.Equipo.Equipo;
import org.json.JSONArray;
import org.json.JSONObject;

public class GestoraEquipos extends GestoraGenerica <Equipo> {
    public GestoraEquipos(String nombreJSON) {
        super(nombreJSON);
    }

    public void cargarEquipos(String nombreArchivo) {
        try {
            String jsonString = " ";

            jsonString = JsonUtiles.leer(nombreArchivo);
            JSONArray jsonArray = new JSONArray(jsonString);

            super.getElementos().clear();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonEquipo =  jsonArray.getJSONObject(i);
                Equipo equipo = new Equipo(jsonEquipo);

                super.agregarElemento(equipo);
            }
        } catch (Exception e) {
            System.out.println("Error al cargar el equipo: " + e.getMessage());
        }

    }
}
