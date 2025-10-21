// Clases del Modelo

import Gestora.GestoraGenerica;
import enums.*;
import Modelo.Competicion.*;
import Modelo.Equipo.*;
import Modelo.Partido.*;
import Modelo.Persona.*;
import Modelo.Usuario.*;
import java.util.*;
import Gestora.*;
import org.json.JSONArray;
import org.json.JSONObject;


void main(String[] args) {
    /*String archivoGuardado = "partida_pro_manager.json";

    // --- 1. Crear Equipos Iniciales ---
    System.out.println("Creando equipos iniciales...");
    ArrayList<Equipo> equiposIniciales = crearEquiposIniciales();

    // --- 2. Cargar Equipos en la Gestora ---
    GestoraGenerica<Equipo> gestoraEquipos = new GestoraGenerica<>("lista_equipos"); // Clave para JSON
    System.out.println("Agregando equipos a la gestora...");
    for (Equipo equipo : equiposIniciales) {
        gestoraEquipos.agregarElemento(equipo);
    }
    System.out.println("Equipos agregados a la gestora.");

    // Creamos la liga solo para tener un objeto Liga y obtener la jornada inicial
    Liga ligaDePrueba = new Liga("Liga de Prueba");
    for (Equipo eq : gestoraEquipos.getElementos()) {
        ligaDePrueba.anotarEquipo(eq);
    }
    // ligaDePrueba.generarFixture(); // No es necesario para solo guardar

    System.out.println("\nPreparando datos para guardar...");
    JSONObject partidaParaGuardar = new JSONObject();

    JSONObject progresoLiga = new JSONObject();
    progresoLiga.put("jornadaActual", ligaDePrueba.getJornada());
    partidaParaGuardar.put("progreso_liga", progresoLiga);

    partidaParaGuardar.put("datos_equipos", gestoraEquipos.toJSON()); // Llama a toJSON() de Gestora

    JSONArray wrapper = new JSONArray(); // Envoltorio
    wrapper.put(partidaParaGuardar);
    JsonUtiles.grabarUnJson(wrapper, archivoGuardado); // Guardar
    System.out.println("¡Partida inicial guardada exitosamente!");
     */

    //Json (No descomentar)
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

    // 1. Estadio, Táctica y DT
    Estadio bombonera = new Estadio("La Bombonera", 54000, 48.00);
    Tactica tacticaBoca = new Tactica(Eformacion.F_442, EstiloJuego.EQUILIBRADO);
    DirectorTecnico russo = new DirectorTecnico("Miguel angel Russo", 69, "Argentino", 80, tacticaBoca); // Datos estimados

    // 2. Crear el Equipo
    Equipo boca = new Equipo("Boca Juniors", bombonera, russo, 28000000.00);

    // Portero
    boca.agregarJugador(new Jugador("Agustín Marchesín", 37, "Argentino", Posicion.ARQUERO, 25, 88, 85));

    // Defensores
    boca.agregarJugador(new Jugador("Lautaro Di Lollo", 21, "Argentino", Posicion.DEFENSOR, 50, 80, 88));
    boca.agregarJugador(new Jugador("Lautaro Blanco", 26, "Argentino", Posicion.DEFENSOR, 70, 75, 89));
    boca.agregarJugador(new Jugador("Marcelo Costa", 31, "Argentino", Posicion.DEFENSOR, 60, 82, 86));

    // Mediocampistas
    boca.agregarJugador(new Jugador("Leandro Paredes", 31, "Argentino", Posicion.MEDIOCAMPISTA, 85, 80, 87));
    boca.agregarJugador(new Jugador("Rodrigo Battaglia", 34, "Argentino", Posicion.MEDIOCAMPISTA, 68, 83, 88));
    boca.agregarJugador(new Jugador("Juan Barinaga", 24, "Argentino", Posicion.MEDIOCAMPISTA, 78, 70, 90));
    boca.agregarJugador(new Jugador("Alan Velasco", 23, "Argentino", Posicion.MEDIOCAMPISTA, 84, 55, 91));

    // Delanteros
    boca.agregarJugador(new Jugador("Miguel Merentiel", 29, "Uruguayo", Posicion.DELANTERO, 87, 48, 90));
    boca.agregarJugador(new Jugador("Milton Gimenez", 29, "Argentino", Posicion.DELANTERO, 85, 48, 86));
    boca.agregarJugador(new Jugador("Exequiel Zeballos", 23, "Argentino", Posicion.DELANTERO, 86, 45, 92));

    equipos.add(boca);

    //San Lorenzo
    Estadio pedroBidegain = new Estadio("Pedro Bidegain", 47964, 48.00);

    Tactica tacticaSanLorenzo = new Tactica(Eformacion.F_4141, EstiloJuego.DEFENSIVO);
    DirectorTecnico dtSanLorenzo = new DirectorTecnico("Damian Ayude", 43, "Argentina", 70, tacticaSanLorenzo);

    Equipo sanLorenzo = new Equipo("San Lorenzo", pedroBidegain, dtSanLorenzo,  10000000.00);

    sanLorenzo.agregarJugador(new Jugador("Orlando Gil", 25, "Paraguayo", Posicion.ARQUERO, 20, 70 , 70));
    sanLorenzo.agregarJugador(new Jugador("Ezequiel Herrera", 22, "Argentino", Posicion.DEFENSOR, 40, 77 , 79));
    sanLorenzo.agregarJugador(new Jugador("Jhohan Romana", 27, "Colombiano", Posicion.DEFENSOR, 40, 85, 89));
    sanLorenzo.agregarJugador(new Jugador("Gaston Hernandez", 27, "Argentino", Posicion.DEFENSOR, 40, 79, 80));
    sanLorenzo.agregarJugador(new Jugador("Elian Baez", 21, "Argentino", Posicion.DEFENSOR, 40, 77, 73));
    sanLorenzo.agregarJugador(new Jugador("Fabricio Lopez", 22, "Argentino", Posicion.MEDIOCAMPISTA, 60, 77, 73));
    sanLorenzo.agregarJugador(new Jugador("Ignacio Perruzzi", 20, "Argentino", Posicion.MEDIOCAMPISTA, 78, 77, 79));
    sanLorenzo.agregarJugador(new Jugador("Nicolas Tripichio", 29, "Argentino", Posicion.MEDIOCAMPISTA, 82, 72, 80));
    sanLorenzo.agregarJugador(new Jugador("Ezequiel Cerutti", 33, "Argentino", Posicion.MEDIOCAMPISTA, 80, 68, 86));
    sanLorenzo.agregarJugador(new Jugador("Facundo Gulli" , 20, "Argentino", Posicion.MEDIOCAMPISTA, 82, 68, 82));
    sanLorenzo.agregarJugador(new Jugador("Alexis Cuello" , 25, "Argentino", Posicion.DELANTERO, 85, 58, 82));

    sanLorenzo.agregarJugador(new Jugador("Diego Herazo", 29, "Colombiano", Posicion.DELANTERO, 84, 40, 85));
    sanLorenzo.agregarJugador(new Jugador("Daniel Herrera", 21, "Colombiano", Posicion.DEFENSOR, 60, 78, 88));
    sanLorenzo.agregarJugador(new Jugador("Francisco Perruzzi", 24, "Argentino", Posicion.MEDIOCAMPISTA, 72, 75, 89));
    sanLorenzo.agregarJugador(new Jugador("Agustin Ladstatter", 20, "Argentino", Posicion.MEDIOCAMPISTA, 68, 70, 87));
    sanLorenzo.agregarJugador(new Jugador("Branco Salinardi", 19, "Argentino", Posicion.DELANTERO, 79, 35, 86));

    equipos.add(sanLorenzo);

    //Independiente
    Estadio estadioIndependiente = new Estadio("Estadio Libertadores de América ", 42000, 40.00);

    Tactica tacticaIndependiente = new Tactica(Eformacion.F_433, EstiloJuego.EQUILIBRADO);

    DirectorTecnico dtIndependiente = new DirectorTecnico("Gustavo Quinteros", 60, "Boliviano", 75, tacticaIndependiente);
    Equipo independiente = new Equipo("Independiente", estadioIndependiente, dtIndependiente, 15000000.00);

    independiente.agregarJugador(new Jugador("Rodrigo Rey", 34, "Argentina", Posicion.ARQUERO, 30, 80, 87));
    independiente.agregarJugador(new Jugador("Federico Vera", 27, "Argentina", Posicion.DEFENSOR, 62, 75, 80));
    independiente.agregarJugador(new Jugador("Kevin Lomonaco", 23, "Argentina", Posicion.DEFENSOR, 60, 84, 86));
    independiente.agregarJugador(new Jugador("Sebastian Valdez", 29, "Argentina", Posicion.DEFENSOR, 67, 82, 82));
    independiente.agregarJugador(new Jugador("Milton Valenzuela", 27, "Argentina", Posicion.DEFENSOR, 62, 79, 78));
    independiente.agregarJugador(new Jugador("Pablo Galdamez", 28, "Chileno", Posicion.MEDIOCAMPISTA, 75, 72, 80));
    independiente.agregarJugador(new Jugador("Rodrigo Fernandez", 29, "Uruguayo", Posicion.MEDIOCAMPISTA, 72, 72, 80));
    independiente.agregarJugador(new Jugador("Luciano Cabral", 30, "Chileno", Posicion.MEDIOCAMPISTA, 80, 70, 83));
    independiente.agregarJugador(new Jugador("Walter Mazzanti", 29, "Argentina", Posicion.DELANTERO, 86, 65, 87));
    independiente.agregarJugador(new Jugador("Ignacio Pussetto", 29, "Argentina", Posicion.DELANTERO, 84, 62, 85));
    independiente.agregarJugador(new Jugador("Ignacio Pussetto", 29, "Argentina", Posicion.DELANTERO, 84, 62, 85));
    independiente.agregarJugador(new Jugador("Matias Abaldo", 21, "Uruguayo", Posicion.DELANTERO, 80, 62, 79));

    independiente.agregarJugador(new Jugador("Facundo Zabala", 26, "Argentino", Posicion.DEFENSOR, 70, 76, 88)); // Edad estimada
    independiente.agregarJugador(new Jugador("Iván Marcone", 35, "Argentino", Posicion.MEDIOCAMPISTA, 65, 80, 87)); // Edad estimada
    independiente.agregarJugador(new Jugador("Gabriel Ávalos", 34, "Paraguayo", Posicion.DELANTERO, 85, 40, 84)); // Edad estimada
    independiente.agregarJugador(new Jugador("Leonardo Godoy", 30, "Argentino", Posicion.DEFENSOR, 78, 74, 89)); // Edad estimada
    independiente.agregarJugador(new Jugador("Lautaro Millan", 19, "Argentino", Posicion.MEDIOCAMPISTA, 75, 65, 86)); // Edad estimada

    equipos.add(independiente);

    //Racing
    Estadio estadioRacing = new Estadio("Estadio Eva Peron", 50000, 40.00);

    Tactica tacticaRacing = new Tactica(Eformacion.F_433, EstiloJuego.OFENSIVO);
    DirectorTecnico dtRacing = new DirectorTecnico("Gustavo Costas", 62, "Argentino ",79 ,tacticaRacing);
    Equipo racing = new Equipo("Racing", estadioRacing, dtRacing, 25000001.00);

    racing.agregarJugador(new Jugador("Facundo Cambeses", 28, "Argentino", Posicion.ARQUERO, 40, 83, 80));
    racing.agregarJugador(new Jugador("Gaston Martirena", 25, "Uruguayo", Posicion.DEFENSOR, 72, 80,80));
    racing.agregarJugador(new Jugador("Santiago Quiros", 22, "Argentino", Posicion.DEFENSOR, 69, 82,76));
    racing.agregarJugador(new Jugador("Agustin Basso", 33, "Argentino", Posicion.DEFENSOR, 65, 82,76));
    racing.agregarJugador(new Jugador("Marco Di Cesare", 23, "Argentino", Posicion.DEFENSOR, 70, 82,80));
    racing.agregarJugador(new Jugador("Bruno Zuculini", 32, "Argentino", Posicion.MEDIOCAMPISTA, 75, 82,80));
    racing.agregarJugador(new Jugador("Juan Ignacio Nardoni", 23, "Argentino", Posicion.MEDIOCAMPISTA, 72, 85,83));
    racing.agregarJugador(new Jugador("Ramiro Degregorio", 22, "Argentino", Posicion.MEDIOCAMPISTA, 72, 80,83));
    racing.agregarJugador(new Jugador("Luciano Dario Vietto", 31, "Argentino", Posicion.DELANTERO, 79, 76,80));
    racing.agregarJugador(new Jugador("Adrián Emmanuel Martínez", 33, "Argentino", Posicion.DELANTERO, 89, 60,85));
    racing.agregarJugador(new Jugador("Adrián Balboa", 31, "Uruguayo", Posicion.DELANTERO, 80, 60,85));

    equipos.add(racing);
    return equipos;
}


