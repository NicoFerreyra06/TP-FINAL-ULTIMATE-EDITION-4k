// Clases del Modelo

import Gestora.GestoraGenerica;
import Gestora.JsonUtiles;
import enums.*;
import Modelo.Competicion.*;
import Modelo.Equipo.*;
import Modelo.Partido.*;
import Modelo.Persona.*;
import Modelo.Usuario.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="SRun"/> or
void main() {
    

}

    public static ArrayList<Equipo> crearEquiposIniciales() {
        ArrayList<Equipo> equipos = new ArrayList<>();

        //River
        Estadio monumental = new Estadio("Mas monumental", 86000, 55.00);

        Tactica tacticaRiver = new Tactica(Eformacion.F_442, EstiloJuego.OFENSIVO); // Táctica común
        DirectorTecnico dtGallardo = new DirectorTecnico("Marcelo Gallardo", 49, "Argentino", 82, tacticaRiver); // Datos estimados

        Equipo river = new Equipo("River Plate", monumental, dtGallardo, 30000000.00);

        //Jugadores
        river.agregarJugador(new Jugador("Franco Armani", 39, "Argentina", Posicion.ARQUERO, 30, 87, 67));
        //Defensores
        river.agregarJugador(new Jugador("Lucas Martinez Quarta", 29, "Argentina", Posicion.DEFENSOR, 50, 85, 80));
        river.agregarJugador(new Jugador("Lautaro Rivero", 21, "Argentina", Posicion.DEFENSOR, 50, 82, 80));
        river.agregarJugador(new Jugador("Paulo Diaz", 31, "Chilena", Posicion.DEFENSOR, 43, 82, 80));
        river.agregarJugador(new Jugador("Gonzalo Montiel", 28, "Chilena", Posicion.DEFENSOR, 50, 82, 80));

        //MedioCampo
        river.agregarJugador(new Jugador("Juan Carlos Portillo", 25, "Argentina", Posicion.MEDIOCAMPISTA, 60, 80, 83));
        river.agregarJugador(new Jugador("Kevin Castano", 25, "Colombiana", Posicion.MEDIOCAMPISTA, 80,75 , 70));
        river.agregarJugador(new Jugador("Santiago Lencina", 20, "Argentina", Posicion.MEDIOCAMPISTA, 81,64, 70));
        river.agregarJugador(new Jugador("Juan Quintero", 32, "Colombiana", Posicion.MEDIOCAMPISTA, 86,50, 69));

        //Atacante
        river.agregarJugador(new Jugador("Sebastian Driussi", 29, "Argentina", Posicion.DELANTERO, 87, 43, 75));
        river.agregarJugador(new Jugador("Maximiliano Salas", 27, "Argentina", Posicion.DELANTERO, 85, 43, 83));

        //Suplentes
        river.agregarJugador(new Jugador("Jeremias Ledesma", 32, "Argentino", Posicion.ARQUERO, 20, 89, 91));
        river.agregarJugador(new Jugador("Fabricio Bustos", 29, "Argentino", Posicion.DEFENSOR, 75, 78, 90));
        river.agregarJugador(new Jugador("Sebastian Boselli", 21, "Uruguayo", Posicion.DEFENSOR, 55, 84, 92));
        river.agregarJugador(new Jugador("Giuliano Galoppo", 26, "Argentino", Posicion.MEDIOCAMPISTA, 82, 65, 88));
        river.agregarJugador(new Jugador("Maximiliano Meza", 32, "Argentino", Posicion.MEDIOCAMPISTA, 80, 68, 87));
        river.agregarJugador(new Jugador("Cristian Jaime", 21, "Mexicano", Posicion.DELANTERO, 83, 40, 90));
        river.agregarJugador(new Jugador("Gonzalo Martinez", 32, "Argentino", Posicion.DELANTERO, 88, 50, 86));
        river.agregarJugador(new Jugador("Miguel Borja", 32, "Colombiano", Posicion.DELANTERO, 92, 30, 86));

        equipos.add(river);

        return equipos;
    }


