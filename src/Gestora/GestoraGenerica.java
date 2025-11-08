package Gestora;

import Interfaces.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase genérica reutilizable para gestionar una lista de elementos.
 * Esta clase está diseñada para contener cualquier tipo de objeto {@code <T>}
 * siempre que dicho objeto implemente la interfaz {@link iToJSON}
 */

public class GestoraGenerica <T extends iToJSON> implements iToJSON {
    private final String nombreJSON;
    private final List <T> elementos;

    /**
     * Construye una nueva gestora genérica.
     *
     * @param nombreJSON La clave que se va a usar para encapsular la lista
     * de elementos
     */
    public GestoraGenerica(String nombreJSON) {
        this.nombreJSON = nombreJSON;
        this.elementos = new ArrayList<>();
    }

    /**
     * Agrega un elemento a la lista.
     * No se permiten elementos nulos
     */
    public void agregarElemento(T elemento) {
        if (elemento != null) {
            this.elementos.add(elemento);
        }
    }

    public List<T> getElementos() {
        return elementos;
    }

    /**
     * Devuelve la cantidad de elementos de la lista
     */
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
