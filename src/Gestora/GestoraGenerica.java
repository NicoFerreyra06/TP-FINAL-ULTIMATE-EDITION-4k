package Gestora;

import Interfaces.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GestoraGenerica <T extends iToJSON> implements iToJSON {
    private String nombreJSON;
    private List <T> elementos;

    public GestoraGenerica(String nombreJSON) {
        this.nombreJSON = nombreJSON;
        this.elementos = new ArrayList<>();
    }

    public void agregarElemento(T elemento) {
        if (elemento != null) {
            this.elementos.add(elemento);
        }
    }

    public List<T> getElementos() {
        return elementos;
    }

    public int cantElementos() {
        return this.elementos.size();
    }

    @Override
    public JSONObject toJSON() {
        JSONObject jsonGestora = new JSONObject();

        JSONArray jsonElementos = new JSONArray();

        for (T elemento : this.elementos) {
            jsonElementos.put(elemento.toJSON());
        }

        jsonGestora.put(this.nombreJSON, jsonElementos);


        return jsonGestora;
    }
}
