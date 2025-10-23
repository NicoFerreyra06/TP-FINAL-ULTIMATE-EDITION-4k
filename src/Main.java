
import Gestora.GestoraGenerica;
import Modelo.Podios.PodiosDeCompeticion;
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
import org.json.JSONTokener;


void main() {

    Scanner sc = new Scanner(System.in);

    JSONTokener tokener = JsonUtiles.leerUnJson("partida_pro_manager.json");
    JSONArray wrapperArray = new JSONArray(tokener);

    JSONObject partidaGuardada = wrapperArray.getJSONObject(0);

    JSONObject progresoLiga = partidaGuardada.getJSONObject("progreso_liga");
    int jornadaCargada = progresoLiga.getInt("jornadaActual");

    JSONObject datosEquipos = partidaGuardada.getJSONObject("datos_equipos");
    JSONArray jsonArrayEquipos = datosEquipos.getJSONArray("lista_equipos");

    ArrayList<Equipo> listaDeEquipos = new ArrayList<>();
    for (int i = 0; i < jsonArrayEquipos.length(); i++) {
        JSONObject jsonEquipo = jsonArrayEquipos.getJSONObject(i);
        Equipo equipo = new Equipo(jsonEquipo); // La magia ocurre aquí
        System.out.println(equipo.getNombre());
        listaDeEquipos.add(equipo);
    }

    try{

        System.out.println("¡Bienvenido al Mánager de Fútbol! ⚽");
        System.out.println("Creando la liga y los equipos...");

        Liga liga = new Liga("Liga prueba");


        for (Equipo equipo : listaDeEquipos){
            liga.anotarEquipo(equipo);
        }


        for (int i = 0; i < listaDeEquipos.size(); i++) {
            System.out.println((i + 1) + ". " + listaDeEquipos.get(i).getNombre());
        }

        System.out.println(" ==== Seleccione su equipo ====");
        int indice = sc.nextInt();
        Equipo usuarioEquipo = listaDeEquipos.get(indice - 1);

        liga.generarFixture();

        System.out.println("¡Has elegido a " + usuarioEquipo.getNombre() + "! Mucha suerte.");
        boolean salir = false;
        int entrenamientosJornada = 0;
        final int limiteEntrenamiento = 1;

        while (!liga.isTerminada() && !salir) {
            System.out.println("\n--- Jornada " + liga.getJornada() + " ---");
            ArrayList<Partido> fixture = liga.getFixture();
            int jornadaActual = liga.getJornada() - 1;

            Partido partidoUsuario = null;

            // Buscamos el partido donde juegue el equipo del usuario
            for (Partido p : fixture) {
                if (p.getLocal().equals(usuarioEquipo) || p.getVisitante().equals(usuarioEquipo)) {
                    partidoUsuario = p;
                    break;
                }
            }

            Equipo local = partidoUsuario.getLocal();
            Equipo visitante = partidoUsuario.getVisitante();
            String estadio = local.getEstadio().getNombre();

            if (partidoUsuario.getLocal().equals(usuarioEquipo)) {
                System.out.println("Tu partido: " + usuarioEquipo.getNombre() + " vs " + partidoUsuario.getVisitante().getNombre());
            } else {
                System.out.println("Tu partido: " + partidoUsuario.getLocal().getNombre() + " vs " + usuarioEquipo.getNombre());
            }

            System.out.println("Menú de Acciones:");
            System.out.println("1. Jugar próxima fecha");
            System.out.println("2. Ver tabla de posiciones");
            int restantes = limiteEntrenamiento - entrenamientosJornada;
            System.out.println("3. Entrenar tus jugadores, (entrenamientos restantes: " + restantes + ")");
            System.out.println("4. Hacer cambios");
            System.out.println("5. Salir del juego");
            System.out.print("Elige una opción: ");
            int opcion = sc.nextInt();

            switch (opcion){
                case 1:
                    liga.jugarProximaFecha(usuarioEquipo);
                    entrenamientosJornada = 0;
                    break;
                case 2:
                    liga.mostrarTabla();
                    break;
                case 3:
                    if (entrenamientosJornada < limiteEntrenamiento) {
                        usuarioEquipo.entrenarEquipo();
                        entrenamientosJornada++;
                        IO.println("Entrenamiento realizado con exito!");
                    } else {
                        IO.println("Limites por jornada alcanzados");
                    }

                    break;

                case 4:
                    realizarCambios(usuarioEquipo, sc);
                    break;

                case 5:
                    salir = true;
                    break;
             }
        }

        if (liga.isTerminada()) {
            System.out.println("\n--- ¡LA LIGA HA TERMINADO! ---");

            PodiosDeCompeticion <Liga> podiosDeCompeticion = new PodiosDeCompeticion<>(liga);
            podiosDeCompeticion.mostrarEstadisticasIndivuduales();
            liga.podioLiga();
        }
    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    }
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

    racing.agregarJugador(new Jugador("Gabriel Arias", 31, "Argentino", Posicion.ARQUERO, 33, 80,80));
    racing.agregarJugador(new Jugador("Franco Pardo", 28, "Argentino", Posicion.DEFENSOR, 60, 81,70));
    racing.agregarJugador(new Jugador("Marcos Rojo", 35, "Argentino", Posicion.DEFENSOR, 75, 75,70));
    racing.agregarJugador(new Jugador("Gabriel Rojas", 28, "Argentino", Posicion.DEFENSOR, 75, 81,70));
    racing.agregarJugador(new Jugador("Duvan Vergara", 29, "Colombiano", Posicion.DELANTERO, 79, 75,75));

    equipos.add(racing);

    // Estudiantes (LP)
    Estadio estadioEstudiantes = new Estadio("Estadio Jorge Luis Hirschi", 30000, 37.80);
    Tactica tacticaEstudiantes = new Tactica(Eformacion.F_442, EstiloJuego.DEFENSIVO);
    DirectorTecnico dtEstudiantes = new DirectorTecnico("Eduardo Domínguez", 46, "Argentino", 85, tacticaEstudiantes);
    Equipo estudiantes = new Equipo("Estudiantes (LP)", estadioEstudiantes, dtEstudiantes, 20000000.00);

    estudiantes.agregarJugador(new Jugador("Matías Mansilla", 28, "Argentino", Posicion.ARQUERO, 35, 81, 79));
    estudiantes.agregarJugador(new Jugador("Zaid Romero", 25, "Argentino", Posicion.DEFENSOR, 70, 82, 80));
    estudiantes.agregarJugador(new Jugador("Eros Mancuso", 26, "Argentino", Posicion.DEFENSOR, 72, 80, 78));
    estudiantes.agregarJugador(new Jugador("Santiago Núñez", 21, "Argentino", Posicion.DEFENSOR, 74, 79, 77));
    estudiantes.agregarJugador(new Jugador("Gastón Benedetti", 22, "Argentino", Posicion.DEFENSOR, 75, 81, 80));
    estudiantes.agregarJugador(new Jugador("José Sosa", 39, "Argentino", Posicion.MEDIOCAMPISTA, 80, 78, 81));
    estudiantes.agregarJugador(new Jugador("Enzo Pérez", 38, "Argentino", Posicion.MEDIOCAMPISTA, 76, 80, 82));
    estudiantes.agregarJugador(new Jugador("Javier Altamirano", 25, "Chileno", Posicion.MEDIOCAMPISTA, 77, 80, 79));
    estudiantes.agregarJugador(new Jugador("Tiago Palacios", 23, "Argentino", Posicion.DELANTERO, 82, 74, 81));
    estudiantes.agregarJugador(new Jugador("Guido Carrillo", 33, "Argentino", Posicion.DELANTERO, 84, 70, 83));
    estudiantes.agregarJugador(new Jugador("Mauro Méndez", 25, "Uruguayo", Posicion.DELANTERO, 81, 72, 82));

    estudiantes.agregarJugador(new Jugador("Mariano Andújar", 41, "Argentino", Posicion.ARQUERO, 28, 77, 75));
    estudiantes.agregarJugador(new Jugador("Luciano Lollo", 37, "Argentino", Posicion.DEFENSOR, 68, 78, 75));
    estudiantes.agregarJugador(new Jugador("Nicolás Palavecino", 27, "Argentino", Posicion.MEDIOCAMPISTA, 73, 80, 78));
    estudiantes.agregarJugador(new Jugador("Santiago Ascacibar", 28, "Argentino", Posicion.MEDIOCAMPISTA, 75, 83, 80));
    estudiantes.agregarJugador(new Jugador("Benjamín Rollheiser", 24, "Argentino", Posicion.DELANTERO, 85, 72, 84));

    equipos.add(estudiantes);

    // Vélez Sarsfield
    Estadio estadioVelez = new Estadio("José Amalfitani", 49000, 38.50);
    Tactica tacticaVelez = new Tactica(Eformacion.F_433, EstiloJuego.EQUILIBRADO);
    DirectorTecnico dtVelez = new DirectorTecnico("Gustavo Quinteros", 59, "Boliviano", 82, tacticaVelez);
    Equipo velez = new Equipo("Vélez Sarsfield", estadioVelez, dtVelez, 22000000.00);

    velez.agregarJugador(new Jugador("Tomás Marchiori", 29, "Argentino", Posicion.ARQUERO, 35, 82, 80));
    velez.agregarJugador(new Jugador("Lautaro Giannetti", 30, "Argentino", Posicion.DEFENSOR, 72, 83, 79));
    velez.agregarJugador(new Jugador("Valentín Gómez", 21, "Argentino", Posicion.DEFENSOR, 78, 84, 82));
    velez.agregarJugador(new Jugador("Elías Gómez", 30, "Argentino", Posicion.DEFENSOR, 74, 81, 80));
    velez.agregarJugador(new Jugador("Francisco Ortega", 25, "Argentino", Posicion.DEFENSOR, 76, 82, 78));
    velez.agregarJugador(new Jugador("Christian Ordóñez", 24, "Argentino", Posicion.MEDIOCAMPISTA, 72, 81, 77));
    velez.agregarJugador(new Jugador("Santiago Cáseres", 28, "Argentino", Posicion.MEDIOCAMPISTA, 70, 82, 80));
    velez.agregarJugador(new Jugador("Lenny Lobato", 23, "Brasilero", Posicion.MEDIOCAMPISTA, 77, 79, 78));
    velez.agregarJugador(new Jugador("Thiago Fernández", 20, "Argentino", Posicion.DELANTERO, 80, 74, 82));
    velez.agregarJugador(new Jugador("Braian Romero", 33, "Argentino", Posicion.DELANTERO, 83, 71, 83));
    velez.agregarJugador(new Jugador("Santiago Castro", 20, "Argentino", Posicion.DELANTERO, 84, 70, 84));

    velez.agregarJugador(new Jugador("Leonardo Burián", 40, "Uruguayo", Posicion.ARQUERO, 30, 78, 77));
    velez.agregarJugador(new Jugador("Damián Fernández", 24, "Argentino", Posicion.DEFENSOR, 70, 81, 78));
    velez.agregarJugador(new Jugador("Tomás Guidara", 28, "Argentino", Posicion.DEFENSOR, 74, 79, 77));
    velez.agregarJugador(new Jugador("Juan Méndez", 26, "Argentino", Posicion.MEDIOCAMPISTA, 73, 80, 79));
    velez.agregarJugador(new Jugador("Abiel Osorio", 22, "Argentino", Posicion.DELANTERO, 80, 72, 81));

    equipos.add(velez);

    // Gimnasia (LP)
    Estadio estadioGimnasia = new Estadio("Estadio Juan Carmelo Zerillo", 25000, 34.70);
    Tactica tacticaGimnasia = new Tactica(Eformacion.F_442, EstiloJuego.OFENSIVO);
    DirectorTecnico dtGimnasia = new DirectorTecnico("Marcelo Méndez", 43, "Uruguayo", 78, tacticaGimnasia);
    Equipo gimnasia = new Equipo("Gimnasia (LP)", estadioGimnasia, dtGimnasia, 13000000.00);

    gimnasia.agregarJugador(new Jugador("Nelson Insfrán", 29, "Argentino", Posicion.ARQUERO, 38, 80, 77));
    gimnasia.agregarJugador(new Jugador("Leonardo Morales", 33, "Argentino", Posicion.DEFENSOR, 67, 80, 78));
    gimnasia.agregarJugador(new Jugador("Yonathan Cabral", 31, "Argentino", Posicion.DEFENSOR, 68, 81, 77));
    gimnasia.agregarJugador(new Jugador("Matías Melluso", 27, "Argentino", Posicion.DEFENSOR, 74, 79, 80));
    gimnasia.agregarJugador(new Jugador("Rodrigo Saravia", 25, "Uruguayo", Posicion.MEDIOCAMPISTA, 72, 81, 78));
    gimnasia.agregarJugador(new Jugador("Lucas Castro", 35, "Argentino", Posicion.MEDIOCAMPISTA, 77, 79, 79));
    gimnasia.agregarJugador(new Jugador("Benjamín Domínguez", 21, "Argentino", Posicion.MEDIOCAMPISTA, 80, 76, 81));
    gimnasia.agregarJugador(new Jugador("Pablo De Blasis", 39, "Argentino", Posicion.MEDIOCAMPISTA, 78, 77, 80));
    gimnasia.agregarJugador(new Jugador("Rodrigo Castillo", 23, "Uruguayo", Posicion.DELANTERO, 82, 72, 82));
    gimnasia.agregarJugador(new Jugador("Ivo Mammini", 21, "Argentino", Posicion.DELANTERO, 80, 70, 81));
    gimnasia.agregarJugador(new Jugador("Cristian Tarragona", 33, "Argentino", Posicion.DELANTERO, 83, 68, 82));

    equipos.add(gimnasia);

    // Huracán
    Estadio estadioHuracan = new Estadio("Tomás Adolfo Ducó", 48000, 37.50);
    Tactica tacticaHuracan = new Tactica(Eformacion.F_4231, EstiloJuego.OFENSIVO);
    DirectorTecnico dtHuracan = new DirectorTecnico("Frank Kudelka", 63, "Argentino", 80, tacticaHuracan);
    Equipo huracan = new Equipo("Huracán", estadioHuracan, dtHuracan, 16000000.00);

    huracan.agregarJugador(new Jugador("Sebastián Meza", 24, "Argentino", Posicion.ARQUERO, 38, 83, 80));
    huracan.agregarJugador(new Jugador("Fernando Tobio", 35, "Argentino", Posicion.DEFENSOR, 67, 82, 78));
    huracan.agregarJugador(new Jugador("Lucas Carrizo", 27, "Argentino", Posicion.DEFENSOR, 70, 81, 79));
    huracan.agregarJugador(new Jugador("Guillermo Soto", 30, "Chileno", Posicion.DEFENSOR, 72, 80, 78));
    huracan.agregarJugador(new Jugador("Lucas Souto", 25, "Argentino", Posicion.DEFENSOR, 73, 80, 77));
    huracan.agregarJugador(new Jugador("Federico Fattori", 32, "Argentino", Posicion.MEDIOCAMPISTA, 74, 82, 80));
    huracan.agregarJugador(new Jugador("Santiago Hezze", 23, "Argentino", Posicion.MEDIOCAMPISTA, 78, 83, 82));
    huracan.agregarJugador(new Jugador("Walter Mazzantti", 27, "Argentino", Posicion.MEDIOCAMPISTA, 76, 78, 79));
    huracan.agregarJugador(new Jugador("Ignacio Pussetto", 30, "Argentino", Posicion.DELANTERO, 84, 73, 83));
    huracan.agregarJugador(new Jugador("Matías Cóccaro", 27, "Uruguayo", Posicion.DELANTERO, 85, 70, 84));
    huracan.agregarJugador(new Jugador("Héctor Fértoli", 31, "Argentino", Posicion.DELANTERO, 83, 72, 82));

    equipos.add(huracan);

    // Newell’s Old Boys
    Estadio estadioNewells = new Estadio("Estadio Marcelo Bielsa", 42000, 38.00);
    Tactica tacticaNewells = new Tactica(Eformacion.F_433, EstiloJuego.OFENSIVO);
    DirectorTecnico dtNewells = new DirectorTecnico("Mauricio Larriera", 53, "Uruguayo", 81, tacticaNewells);
    Equipo newells = new Equipo("Newell’s Old Boys", estadioNewells, dtNewells, 17500000.00);

// Titulares
    newells.agregarJugador(new Jugador("Lucas Hoyos", 35, "Argentino", Posicion.ARQUERO, 34, 82, 80));
    newells.agregarJugador(new Jugador("Armando Méndez", 29, "Uruguayo", Posicion.DEFENSOR, 75, 82, 79));
    newells.agregarJugador(new Jugador("Gustavo Velázquez", 34, "Paraguayo", Posicion.DEFENSOR, 72, 83, 78));
    newells.agregarJugador(new Jugador("Ian Glavinovich", 22, "Argentino", Posicion.DEFENSOR, 70, 80, 78));
    newells.agregarJugador(new Jugador("Ángelo Martino", 27, "Argentino", Posicion.DEFENSOR, 73, 81, 80));
    newells.agregarJugador(new Jugador("Rodrigo Fernández Cedrés", 29, "Uruguayo", Posicion.MEDIOCAMPISTA, 72, 81, 80));
    newells.agregarJugador(new Jugador("Juan Sforza", 22, "Argentino", Posicion.MEDIOCAMPISTA, 74, 84, 82));
    newells.agregarJugador(new Jugador("Éver Banega", 37, "Argentino", Posicion.MEDIOCAMPISTA, 82, 79, 83));
    newells.agregarJugador(new Jugador("Francisco González", 23, "Argentino", Posicion.DELANTERO, 83, 75, 83));
    newells.agregarJugador(new Jugador("Ignacio Ramírez", 28, "Uruguayo", Posicion.DELANTERO, 84, 72, 82));
    newells.agregarJugador(new Jugador("Brian Aguirre", 21, "Argentino", Posicion.DELANTERO, 81, 73, 82));

// Suplentes
    newells.agregarJugador(new Jugador("Lucas Macagno", 27, "Argentino", Posicion.ARQUERO, 33, 78, 77));
    newells.agregarJugador(new Jugador("Marcos Portillo", 24, "Argentino", Posicion.MEDIOCAMPISTA, 72, 79, 78));
    newells.agregarJugador(new Jugador("Cristian Ferreira", 25, "Argentino", Posicion.MEDIOCAMPISTA, 78, 77, 80));
    newells.agregarJugador(new Jugador("Giovani Chiaverano", 21, "Argentino", Posicion.MEDIOCAMPISTA, 74, 77, 79));
    newells.agregarJugador(new Jugador("Pablo Sabbag", 27, "Colombiano", Posicion.DELANTERO, 80, 71, 81));
    newells.agregarJugador(new Jugador("Víctor Velázquez", 30, "Paraguayo", Posicion.DEFENSOR, 69, 80, 77));
    newells.agregarJugador(new Jugador("Fabricio Tirado", 23, "Argentino", Posicion.DEFENSOR, 71, 78, 78));

    equipos.add(newells);


    // Rosario Central
    Estadio estadioCentral = new Estadio("Gigante de Arroyito", 41000, 37.80);
    Tactica tacticaCentral = new Tactica(Eformacion.F_442, EstiloJuego.EQUILIBRADO);
    DirectorTecnico dtCentral = new DirectorTecnico("Miguel Ángel Russo", 69, "Argentino", 84, tacticaCentral);
    Equipo rosarioCentral = new Equipo("Rosario Central", estadioCentral, dtCentral, 19000000.00);

// Titulares
    rosarioCentral.agregarJugador(new Jugador("Jorge Broun", 38, "Argentino", Posicion.ARQUERO, 33, 81, 79));
    rosarioCentral.agregarJugador(new Jugador("Damián Martínez", 34, "Argentino", Posicion.DEFENSOR, 72, 82, 80));
    rosarioCentral.agregarJugador(new Jugador("Carlos Quintana", 36, "Argentino", Posicion.DEFENSOR, 68, 81, 78));
    rosarioCentral.agregarJugador(new Jugador("Facundo Mallo", 29, "Uruguayo", Posicion.DEFENSOR, 70, 80, 79));
    rosarioCentral.agregarJugador(new Jugador("Agustín Sández", 24, "Argentino", Posicion.DEFENSOR, 74, 81, 80));
    rosarioCentral.agregarJugador(new Jugador("Kevin Ortiz", 23, "Argentino", Posicion.MEDIOCAMPISTA, 75, 83, 81));
    rosarioCentral.agregarJugador(new Jugador("Ignacio Malcorra", 37, "Argentino", Posicion.MEDIOCAMPISTA, 82, 79, 84));
    rosarioCentral.agregarJugador(new Jugador("Tomás O’Connor", 22, "Argentino", Posicion.MEDIOCAMPISTA, 76, 79, 80));
    rosarioCentral.agregarJugador(new Jugador("Jaminton Campaz", 25, "Colombiano", Posicion.DELANTERO, 86, 74, 85));
    rosarioCentral.agregarJugador(new Jugador("Lautaro Giaccone", 24, "Argentino", Posicion.DELANTERO, 82, 73, 82));
    rosarioCentral.agregarJugador(new Jugador("Agustín Módica", 21, "Argentino", Posicion.DELANTERO, 80, 72, 81));

// Suplentes
    rosarioCentral.agregarJugador(new Jugador("Gaspar Servio", 32, "Argentino", Posicion.ARQUERO, 36, 79, 78));
    rosarioCentral.agregarJugador(new Jugador("Juan Cruz Komar", 29, "Argentino", Posicion.DEFENSOR, 71, 79, 78));
    rosarioCentral.agregarJugador(new Jugador("Lautaro Oviedo", 23, "Argentino", Posicion.DEFENSOR, 70, 78, 77));
    rosarioCentral.agregarJugador(new Jugador("Francesco Lo Celso", 24, "Argentino", Posicion.MEDIOCAMPISTA, 74, 80, 79));
    rosarioCentral.agregarJugador(new Jugador("Luca Martínez Dupuy", 23, "Mexicano", Posicion.DELANTERO, 81, 72, 82));
    rosarioCentral.agregarJugador(new Jugador("Octavio Bianchi", 29, "Argentino", Posicion.DELANTERO, 78, 70, 80));
    rosarioCentral.agregarJugador(new Jugador("Tomás Jacob", 22, "Argentino", Posicion.DEFENSOR, 71, 77, 78));

    equipos.add(rosarioCentral);

    // Lanús
    Estadio estadioLanus = new Estadio("Estadio Ciudad de Lanús - Néstor Díaz Pérez", 47000, 38.00);
    Tactica tacticaLanus = new Tactica(Eformacion.F_433, EstiloJuego.OFENSIVO);
    DirectorTecnico dtLanus = new DirectorTecnico("Ricardo Zielinski", 65, "Argentino", 82, tacticaLanus);
    Equipo lanus = new Equipo("Lanús", estadioLanus, dtLanus, 18500000.00);

// Titulares
    lanus.agregarJugador(new Jugador("Lucas Acosta", 29, "Argentino", Posicion.ARQUERO, 36, 82, 80));
    lanus.agregarJugador(new Jugador("Juan Cáceres", 23, "Paraguayo", Posicion.DEFENSOR, 73, 81, 79));
    lanus.agregarJugador(new Jugador("Cristian Lema", 34, "Argentino", Posicion.DEFENSOR, 70, 83, 80));
    lanus.agregarJugador(new Jugador("José Canale", 27, "Paraguayo", Posicion.DEFENSOR, 72, 82, 79));
    lanus.agregarJugador(new Jugador("Nicolás Morgantini", 29, "Argentino", Posicion.DEFENSOR, 73, 80, 79));
    lanus.agregarJugador(new Jugador("Raúl Loaiza", 30, "Colombiano", Posicion.MEDIOCAMPISTA, 74, 83, 80));
    lanus.agregarJugador(new Jugador("Luciano Boggio", 27, "Uruguayo", Posicion.MEDIOCAMPISTA, 76, 81, 80));
    lanus.agregarJugador(new Jugador("Pedro De La Vega", 24, "Argentino", Posicion.MEDIOCAMPISTA, 85, 77, 83));
    lanus.agregarJugador(new Jugador("Walter Bou", 31, "Argentino", Posicion.DELANTERO, 83, 72, 82));
    lanus.agregarJugador(new Jugador("Ramiro Carrera", 31, "Argentino", Posicion.DELANTERO, 80, 73, 80));
    lanus.agregarJugador(new Jugador("Leandro Díaz", 33, "Argentino", Posicion.DELANTERO, 84, 70, 81));

// Suplentes
    lanus.agregarJugador(new Jugador("Alan Aguerre", 34, "Argentino", Posicion.ARQUERO, 35, 79, 77));
    lanus.agregarJugador(new Jugador("Braian Aguilar", 23, "Argentino", Posicion.DEFENSOR, 70, 78, 77));
    lanus.agregarJugador(new Jugador("Matías Pérez", 25, "Argentino", Posicion.DEFENSOR, 72, 79, 78));
    lanus.agregarJugador(new Jugador("Agustín Rodríguez", 23, "Argentino", Posicion.MEDIOCAMPISTA, 73, 78, 79));
    lanus.agregarJugador(new Jugador("Franco Orozco", 23, "Argentino", Posicion.DELANTERO, 82, 71, 81));
    lanus.agregarJugador(new Jugador("Marcelino Moreno", 30, "Argentino", Posicion.MEDIOCAMPISTA, 81, 75, 80));
    lanus.agregarJugador(new Jugador("Troyansky Franco", 28, "Argentino", Posicion.DELANTERO, 80, 72, 79));

    equipos.add(lanus);


    // Argentinos Juniors
    Estadio estadioArgentinos = new Estadio("Estadio Diego Armando Maradona", 26000, 37.50);
    Tactica tacticaArgentinos = new Tactica(Eformacion.F_433, EstiloJuego.EQUILIBRADO);
    DirectorTecnico dtArgentinos = new DirectorTecnico("Pablo Guede", 50, "Argentino", 84, tacticaArgentinos);
    Equipo argentinos = new Equipo("Argentinos Juniors", estadioArgentinos, dtArgentinos, 21000000.00);

// Titulares
    argentinos.agregarJugador(new Jugador("Alexis Martín Arias", 33, "Argentino", Posicion.ARQUERO, 36, 82, 80));
    argentinos.agregarJugador(new Jugador("Ángel Di María", 37, "Argentino", Posicion.MEDIOCAMPISTA, 88, 75, 84)); // regresó 2025
    argentinos.agregarJugador(new Jugador("Federico Redondo", 22, "Argentino", Posicion.MEDIOCAMPISTA, 80, 82, 82));
    argentinos.agregarJugador(new Jugador("Alan Rodríguez", 29, "Uruguayo", Posicion.DEFENSOR, 72, 81, 79));
    argentinos.agregarJugador(new Jugador("Miguel Torrén", 36, "Argentino", Posicion.DEFENSOR, 68, 80, 78));
    argentinos.agregarJugador(new Jugador("Marco Di Césare", 23, "Argentino", Posicion.DEFENSOR, 70, 81, 79));
    argentinos.agregarJugador(new Jugador("Francisco González Metilli", 27, "Argentino", Posicion.MEDIOCAMPISTA, 80, 78, 81));
    argentinos.agregarJugador(new Jugador("Gastón Verón", 23, "Argentino", Posicion.DELANTERO, 83, 72, 81));
    argentinos.agregarJugador(new Jugador("Maximiliano Romero", 26, "Argentino", Posicion.DELANTERO, 81, 70, 80));
    argentinos.agregarJugador(new Jugador("Gabriel Ávalos", 33, "Paraguayo", Posicion.DELANTERO, 84, 71, 82));
    argentinos.agregarJugador(new Jugador("Alan Lescano", 24, "Argentino", Posicion.MEDIOCAMPISTA, 78, 79, 80));

// Suplentes
    argentinos.agregarJugador(new Jugador("Leandro Brey", 22, "Argentino", Posicion.ARQUERO, 34, 79, 78));
    argentinos.agregarJugador(new Jugador("Jonathan Galván", 32, "Argentino", Posicion.DEFENSOR, 70, 79, 77));
    argentinos.agregarJugador(new Jugador("Luciano Sánchez", 30, "Argentino", Posicion.DEFENSOR, 71, 79, 78));
    argentinos.agregarJugador(new Jugador("Santiago Montiel", 26, "Argentino", Posicion.MEDIOCAMPISTA, 75, 78, 79));
    argentinos.agregarJugador(new Jugador("David Zalazar", 25, "Argentino", Posicion.MEDIOCAMPISTA, 73, 78, 78));
    argentinos.agregarJugador(new Jugador("Diego Rodríguez", 35, "Uruguayo", Posicion.MEDIOCAMPISTA, 76, 80, 79));
    argentinos.agregarJugador(new Jugador("Agustín Hausch", 21, "Argentino", Posicion.DELANTERO, 80, 72, 81));

    equipos.add(argentinos);

    // Ferro Carril Oeste
    Estadio estadioFerro = new Estadio("Estadio Arquitecto Ricardo Etcheverry", 24500, 36.20);
    Tactica tacticaFerro = new Tactica(Eformacion.F_442, EstiloJuego.EQUILIBRADO);
    DirectorTecnico dtFerro = new DirectorTecnico("Jorge Cordon", 50, "Argentino", 75, tacticaFerro);
    Equipo ferro = new Equipo("Ferro Carril Oeste", estadioFerro, dtFerro, 8000000.00);

// Titulares
    ferro.agregarJugador(new Jugador("Oscar Desimone", 27, "Argentino", Posicion.ARQUERO, 35, 79, 77));
    ferro.agregarJugador(new Jugador("Fabián Noguera", 31, "Argentino", Posicion.DEFENSOR, 68, 80, 78));
    ferro.agregarJugador(new Jugador("Jonathan Herrera", 32, "Argentino", Posicion.DELANTERO, 80, 70, 79));
    ferro.agregarJugador(new Jugador("Gastón Moreira", 25, "Argentino", Posicion.DEFENSOR, 70, 79, 78));
    ferro.agregarJugador(new Jugador("Tomás Molina", 29, "Argentino", Posicion.DELANTERO, 82, 72, 80));
    ferro.agregarJugador(new Jugador("Gabriel Díaz", 30, "Argentino", Posicion.DEFENSOR, 70, 78, 77));
    ferro.agregarJugador(new Jugador("Pablo Palacio", 28, "Argentino", Posicion.MEDIOCAMPISTA, 74, 80, 79));
    ferro.agregarJugador(new Jugador("Claudio Mosca", 33, "Argentino", Posicion.MEDIOCAMPISTA, 76, 78, 78));
    ferro.agregarJugador(new Jugador("Tomás Asprea", 29, "Argentino", Posicion.MEDIOCAMPISTA, 77, 77, 78));
    ferro.agregarJugador(new Jugador("Brian Fernández", 30, "Argentino", Posicion.DELANTERO, 83, 68, 81));
    ferro.agregarJugador(new Jugador("Lucio Mastromarino", 20, "Argentino", Posicion.MEDIOCAMPISTA, 72, 76, 77));

// Suplentes
    ferro.agregarJugador(new Jugador("Nicolás Carrizo", 25, "Argentino", Posicion.ARQUERO, 34, 76, 75));
    ferro.agregarJugador(new Jugador("Lucas Souto", 26, "Argentino", Posicion.DEFENSOR, 71, 78, 77));
    ferro.agregarJugador(new Jugador("Sebastián Olivarez", 30, "Argentino", Posicion.DEFENSOR, 69, 77, 76));
    ferro.agregarJugador(new Jugador("Lucas Pugh", 30, "Argentino", Posicion.DELANTERO, 78, 70, 78));
    ferro.agregarJugador(new Jugador("Cristian Carrizo", 27, "Argentino", Posicion.MEDIOCAMPISTA, 72, 77, 78));
    ferro.agregarJugador(new Jugador("Agustín Dátola", 24, "Argentino", Posicion.MEDIOCAMPISTA, 73, 78, 78));
    ferro.agregarJugador(new Jugador("Pablo Tiscornia", 23, "Argentino", Posicion.DELANTERO, 80, 72, 79));

    equipos.add(ferro);

    // Platense
    Estadio estadioPlatense = new Estadio("Estadio Ciudad de Vicente López", 31000, 35.00);
    Tactica tacticaPlatense = new Tactica(Eformacion.F_451, EstiloJuego.DEFENSIVO);
    DirectorTecnico dtPlatense = new DirectorTecnico("Sebastián Grazzini", 43, "Argentino", 78, tacticaPlatense);
    Equipo platense = new Equipo("Platense", estadioPlatense, dtPlatense, 12500000.00);

// Titulares
    platense.agregarJugador(new Jugador("Ramiro Macagno", 28, "Argentino", Posicion.ARQUERO, 40, 82, 80));
    platense.agregarJugador(new Jugador("Ignacio Vázquez", 27, "Argentino", Posicion.DEFENSOR, 70, 81, 78));
    platense.agregarJugador(new Jugador("Gastón Suso", 30, "Argentino", Posicion.DEFENSOR, 68, 82, 77));
    platense.agregarJugador(new Jugador("Juan Infante", 28, "Argentino", Posicion.DEFENSOR, 71, 80, 78));
    platense.agregarJugador(new Jugador("Raúl Lozano", 26, "Argentino", Posicion.DEFENSOR, 72, 81, 79));
    platense.agregarJugador(new Jugador("Nicolás Castro", 29, "Argentino", Posicion.MEDIOCAMPISTA, 75, 82, 80));
    platense.agregarJugador(new Jugador("Iván Rossi", 30, "Argentino", Posicion.MEDIOCAMPISTA, 74, 81, 80));
    platense.agregarJugador(new Jugador("Franco Baldassarra", 26, "Argentino", Posicion.MEDIOCAMPISTA, 77, 80, 79));
    platense.agregarJugador(new Jugador("Mateo Pellegrino", 23, "Argentino", Posicion.DELANTERO, 82, 72, 82));
    platense.agregarJugador(new Jugador("Maximiliano Zalazar", 24, "Argentino", Posicion.DELANTERO, 81, 70, 80));
    platense.agregarJugador(new Jugador("Franco Díaz", 24, "Argentino", Posicion.MEDIOCAMPISTA, 76, 79, 79));

// Suplentes
    platense.agregarJugador(new Jugador("Joaquín Ledesma", 23, "Argentino", Posicion.ARQUERO, 37, 78, 76));
    platense.agregarJugador(new Jugador("Raúl Loaiza", 29, "Colombiano", Posicion.MEDIOCAMPISTA, 72, 80, 78));
    platense.agregarJugador(new Jugador("Agustín Ocampo", 28, "Uruguayo", Posicion.DELANTERO, 81, 71, 81));
    platense.agregarJugador(new Jugador("Nicolás Servetto", 28, "Argentino", Posicion.DELANTERO, 83, 70, 82));
    platense.agregarJugador(new Jugador("Agustín Quiroga", 21, "Argentino", Posicion.DEFENSOR, 69, 78, 77));

    equipos.add(platense);

    // Banfield
    Estadio estadioBanfield = new Estadio("Florencio Sola", 34000, 36.00);
    Tactica tacticaBanfield = new Tactica(Eformacion.F_4231, EstiloJuego.EQUILIBRADO);
    DirectorTecnico dtBanfield = new DirectorTecnico("Julio César Falcioni", 69, "Argentino", 80, tacticaBanfield);
    Equipo banfield = new Equipo("Banfield", estadioBanfield, dtBanfield, 17000000.00);

// Titulares
    banfield.agregarJugador(new Jugador("Facundo Cambeses", 28, "Argentino", Posicion.ARQUERO, 41, 83, 80));
    banfield.agregarJugador(new Jugador("Emanuel Coronel", 27, "Argentino", Posicion.DEFENSOR, 70, 81, 78));
    banfield.agregarJugador(new Jugador("Alejandro Maciel", 27, "Argentino", Posicion.DEFENSOR, 68, 82, 79));
    banfield.agregarJugador(new Jugador("Aaron Quirós", 22, "Argentino", Posicion.DEFENSOR, 73, 79, 80));
    banfield.agregarJugador(new Jugador("Dylan Gissi", 33, "Suizo", Posicion.DEFENSOR, 67, 80, 77));
    banfield.agregarJugador(new Jugador("Ignacio Rodríguez", 25, "Argentino", Posicion.MEDIOCAMPISTA, 74, 80, 79));
    banfield.agregarJugador(new Jugador("Matías Romero", 28, "Argentino", Posicion.MEDIOCAMPISTA, 75, 81, 80));
    banfield.agregarJugador(new Jugador("Juan Bizans", 24, "Argentino", Posicion.MEDIOCAMPISTA, 77, 79, 80));
    banfield.agregarJugador(new Jugador("Milton Giménez", 29, "Argentino", Posicion.DELANTERO, 84, 70, 83));
    banfield.agregarJugador(new Jugador("Juan Manuel Cruz", 25, "Argentino", Posicion.DELANTERO, 82, 71, 81));
    banfield.agregarJugador(new Jugador("Sebastián Sosa Sánchez", 31, "Uruguayo", Posicion.DELANTERO, 83, 69, 82));

// Suplentes
    banfield.agregarJugador(new Jugador("Enzo Larrosa", 24, "Uruguayo", Posicion.DELANTERO, 80, 70, 79));
    banfield.agregarJugador(new Jugador("Eric Remedi", 29, "Argentino", Posicion.MEDIOCAMPISTA, 75, 81, 80));
    banfield.agregarJugador(new Jugador("Matías González", 21, "Argentino", Posicion.MEDIOCAMPISTA, 74, 78, 78));
    banfield.agregarJugador(new Jugador("Ian Escobar", 28, "Argentino", Posicion.DEFENSOR, 71, 78, 77));
    banfield.agregarJugador(new Jugador("Facundo Sanguinetti", 23, "Argentino", Posicion.ARQUERO, 38, 78, 76));

    equipos.add(banfield);

    // Chacarita Juniors
    Estadio estadioChacarita = new Estadio("Estadio Chacarita Juniors", 35000, 33.50);
    Tactica tacticaChacarita = new Tactica(Eformacion.F_442, EstiloJuego.DEFENSIVO);
    DirectorTecnico dtChacarita = new DirectorTecnico("Aníbal Biggeri", 54, "Argentino", 77, tacticaChacarita);
    Equipo chacarita = new Equipo("Chacarita Juniors", estadioChacarita, dtChacarita, 8500000.00);

// Titulares
    chacarita.agregarJugador(new Jugador("Juan Cruz Ávila", 26, "Argentino", Posicion.ARQUERO, 38, 81, 78));
    chacarita.agregarJugador(new Jugador("Federico Rosso", 35, "Argentino", Posicion.DEFENSOR, 66, 79, 76));
    chacarita.agregarJugador(new Jugador("Maximiliano Coronel", 35, "Argentino", Posicion.DEFENSOR, 67, 80, 77));
    chacarita.agregarJugador(new Jugador("Santiago Godoy", 23, "Argentino", Posicion.DELANTERO, 82, 70, 81));
    chacarita.agregarJugador(new Jugador("Juan Cruz González", 26, "Argentino", Posicion.DEFENSOR, 70, 79, 78));
    chacarita.agregarJugador(new Jugador("Ricardo Blanco", 33, "Colombiano", Posicion.MEDIOCAMPISTA, 74, 80, 79));
    chacarita.agregarJugador(new Jugador("Luciano Nieto", 32, "Argentino", Posicion.MEDIOCAMPISTA, 75, 80, 79));
    chacarita.agregarJugador(new Jugador("Ariel López", 24, "Argentino", Posicion.MEDIOCAMPISTA, 73, 78, 77));
    chacarita.agregarJugador(new Jugador("Matías Rodríguez", 27, "Argentino", Posicion.MEDIOCAMPISTA, 74, 79, 78));
    chacarita.agregarJugador(new Jugador("Matías Pisano", 33, "Argentino", Posicion.DELANTERO, 81, 72, 80));
    chacarita.agregarJugador(new Jugador("Elías Alderete", 29, "Argentino", Posicion.DELANTERO, 83, 70, 81));

// Suplentes
    chacarita.agregarJugador(new Jugador("Ignacio Arce", 33, "Argentino", Posicion.ARQUERO, 39, 79, 77));
    chacarita.agregarJugador(new Jugador("Nicolás Chávez", 27, "Argentino", Posicion.DEFENSOR, 70, 78, 77));
    chacarita.agregarJugador(new Jugador("Tomás Bottari", 23, "Argentino", Posicion.MEDIOCAMPISTA, 72, 77, 76));
    chacarita.agregarJugador(new Jugador("Santiago Moya", 21, "Argentino", Posicion.DELANTERO, 80, 69, 78));
    chacarita.agregarJugador(new Jugador("Franco Quiroz", 25, "Argentino", Posicion.MEDIOCAMPISTA, 73, 77, 77));

    equipos.add(chacarita);

    // Colón
    Estadio estadioColon = new Estadio("Estadio Brigadier General Estanislao López", 40000, 36.50);
    Tactica tacticaColon = new Tactica(Eformacion.F_433, EstiloJuego.OFENSIVO);
    DirectorTecnico dtColon = new DirectorTecnico("Iván Delfino", 53, "Argentino", 80, tacticaColon);
    Equipo colon = new Equipo("Colón", estadioColon, dtColon, 18500000.00);

// Titulares
    colon.agregarJugador(new Jugador("Ignacio Chicco", 28, "Argentino", Posicion.ARQUERO, 41, 83, 80));
    colon.agregarJugador(new Jugador("Eric Meza", 25, "Argentino", Posicion.DEFENSOR, 72, 82, 80));
    colon.agregarJugador(new Jugador("Facundo Garcés", 25, "Argentino", Posicion.DEFENSOR, 70, 83, 81));
    colon.agregarJugador(new Jugador("Rafael Delgado", 35, "Argentino", Posicion.DEFENSOR, 69, 81, 78));
    colon.agregarJugador(new Jugador("Leonardo Godoy", 29, "Argentino", Posicion.DEFENSOR, 71, 80, 79));
    colon.agregarJugador(new Jugador("Rubén Botta", 35, "Argentino", Posicion.MEDIOCAMPISTA, 77, 80, 82));
    colon.agregarJugador(new Jugador("Juan Pablo Álvarez", 27, "Argentino", Posicion.MEDIOCAMPISTA, 75, 81, 80));
    colon.agregarJugador(new Jugador("Santiago Pierotti", 24, "Argentino", Posicion.MEDIOCAMPISTA, 78, 82, 83));
    colon.agregarJugador(new Jugador("Tomás Galván", 24, "Argentino", Posicion.DELANTERO, 83, 72, 82));
    colon.agregarJugador(new Jugador("Joaquín Ibáñez", 29, "Argentino", Posicion.DELANTERO, 82, 70, 81));
    colon.agregarJugador(new Jugador("Ramón Ábila", 35, "Argentino", Posicion.DELANTERO, 85, 67, 83));

// Suplentes
    colon.agregarJugador(new Jugador("Matías Ibáñez", 38, "Argentino", Posicion.ARQUERO, 37, 78, 76));
    colon.agregarJugador(new Jugador("Gonzalo Escobar", 29, "Argentino", Posicion.DEFENSOR, 71, 79, 78));
    colon.agregarJugador(new Jugador("Cristian Vega", 30, "Argentino", Posicion.MEDIOCAMPISTA, 74, 80, 79));
    colon.agregarJugador(new Jugador("Facundo Farías", 22, "Argentino", Posicion.DELANTERO, 86, 74, 84));
    colon.agregarJugador(new Jugador("Nicolás Leguizamón", 29, "Argentino", Posicion.DELANTERO, 81, 70, 80));

    equipos.add(colon);


    // Atlanta
    Estadio estadioAtlanta = new Estadio("Estadio Don León Kolbowski", 25000, 33.00);
    Tactica tacticaAtlanta = new Tactica(Eformacion.F_4231, EstiloJuego.EQUILIBRADO);
    DirectorTecnico dtAtlanta = new DirectorTecnico("Mauricio Giganti", 47, "Argentino", 77, tacticaAtlanta);
    Equipo atlanta = new Equipo("Atlanta", estadioAtlanta, dtAtlanta, 9000000.00);

// Titulares
    atlanta.agregarJugador(new Jugador("Rodrigo Lugo", 33, "Argentino", Posicion.ARQUERO, 38, 82, 79));
    atlanta.agregarJugador(new Jugador("Alan Pérez", 30, "Argentino", Posicion.DEFENSOR, 68, 80, 77));
    atlanta.agregarJugador(new Jugador("Nicolás Previtali", 28, "Argentino", Posicion.MEDIOCAMPISTA, 73, 81, 79));
    atlanta.agregarJugador(new Jugador("Agustín Bolívar", 27, "Argentino", Posicion.MEDIOCAMPISTA, 74, 80, 79));
    atlanta.agregarJugador(new Jugador("Martín García", 24, "Argentino", Posicion.DEFENSOR, 70, 78, 77));
    atlanta.agregarJugador(new Jugador("Federico Bisanz", 25, "Argentino", Posicion.MEDIOCAMPISTA, 76, 79, 80));
    atlanta.agregarJugador(new Jugador("Alan Pérez", 30, "Argentino", Posicion.DEFENSOR, 69, 80, 78));
    atlanta.agregarJugador(new Jugador("Juan Galeano", 35, "Argentino", Posicion.MEDIOCAMPISTA, 75, 78, 78));
    atlanta.agregarJugador(new Jugador("Luis López", 37, "Argentino", Posicion.DELANTERO, 83, 68, 80));
    atlanta.agregarJugador(new Jugador("Fabricio Pedrozo", 31, "Argentino", Posicion.DELANTERO, 82, 70, 81));
    atlanta.agregarJugador(new Jugador("Tomás Silva", 22, "Argentino", Posicion.DELANTERO, 80, 72, 80));

// Suplentes
    atlanta.agregarJugador(new Jugador("Francisco Rago", 31, "Argentino", Posicion.ARQUERO, 38, 80, 78));
    atlanta.agregarJugador(new Jugador("Nahuel Tecilla", 28, "Argentino", Posicion.DEFENSOR, 70, 78, 77));
    atlanta.agregarJugador(new Jugador("Ignacio Colombini", 35, "Argentino", Posicion.DELANTERO, 82, 70, 80));
    atlanta.agregarJugador(new Jugador("Diego Becker", 27, "Argentino", Posicion.MEDIOCAMPISTA, 74, 79, 78));
    atlanta.agregarJugador(new Jugador("Matías Molina", 24, "Argentino", Posicion.MEDIOCAMPISTA, 72, 77, 76));

    equipos.add(atlanta);


    Equipo atlanta1 = new Equipo("Atlanta B", estadioAtlanta, dtAtlanta, 9000000.00);

// Titulares
    atlanta.agregarJugador(new Jugador("Rodrigo Lugo", 33, "Argentino", Posicion.ARQUERO, 38, 82, 79));
    atlanta.agregarJugador(new Jugador("Alan Pérez", 30, "Argentino", Posicion.DEFENSOR, 68, 80, 77));
    atlanta.agregarJugador(new Jugador("Nicolás Previtali", 28, "Argentino", Posicion.MEDIOCAMPISTA, 73, 81, 79));
    atlanta.agregarJugador(new Jugador("Agustín Bolívar", 27, "Argentino", Posicion.MEDIOCAMPISTA, 74, 80, 79));
    atlanta.agregarJugador(new Jugador("Martín García", 24, "Argentino", Posicion.DEFENSOR, 70, 78, 77));
    atlanta.agregarJugador(new Jugador("Federico Bisanz", 25, "Argentino", Posicion.MEDIOCAMPISTA, 76, 79, 80));
    atlanta.agregarJugador(new Jugador("Alan Pérez", 30, "Argentino", Posicion.DEFENSOR, 69, 80, 78));
    atlanta.agregarJugador(new Jugador("Juan Galeano", 35, "Argentino", Posicion.MEDIOCAMPISTA, 75, 78, 78));
    atlanta.agregarJugador(new Jugador("Luis López", 37, "Argentino", Posicion.DELANTERO, 83, 68, 80));
    atlanta.agregarJugador(new Jugador("Fabricio Pedrozo", 31, "Argentino", Posicion.DELANTERO, 82, 70, 81));
    atlanta.agregarJugador(new Jugador("Tomás Silva", 22, "Argentino", Posicion.DELANTERO, 80, 72, 80));

// Suplentes
    atlanta.agregarJugador(new Jugador("Francisco Rago", 31, "Argentino", Posicion.ARQUERO, 38, 80, 78));
    atlanta.agregarJugador(new Jugador("Nahuel Tecilla", 28, "Argentino", Posicion.DEFENSOR, 70, 78, 77));
    atlanta.agregarJugador(new Jugador("Ignacio Colombini", 35, "Argentino", Posicion.DELANTERO, 82, 70, 80));
    atlanta.agregarJugador(new Jugador("Diego Becker", 27, "Argentino", Posicion.MEDIOCAMPISTA, 74, 79, 78));
    atlanta.agregarJugador(new Jugador("Matías Molina", 24, "Argentino", Posicion.MEDIOCAMPISTA, 72, 77, 76));

    equipos.add(atlanta);

    return equipos;
}

public static void realizarCambios (Equipo usuarioEquipo, Scanner sc) {
    int i = 0;
    ArrayList <Jugador> titularesArray = new ArrayList<>(usuarioEquipo.getTitulares());
    ArrayList <Jugador> suplentesArray = new ArrayList<>(usuarioEquipo.getSuplentes());

    System.out.println("titulares");
    for (Jugador j : usuarioEquipo.getTitulares()){
        System.out.println(i + 1 + "-" +j.getNombre());
        i++;
    }
    System.out.println("Ingrese el titular a cambiar");
    int indiceTitular = sc.nextInt();

    i = 0;
    System.out.println("suplentes");
    for (Jugador j : usuarioEquipo.getSuplentes()){
        System.out.println(i+ 1 + "-" +j.getNombre());
        i++;
    }

    System.out.println("Ingrese el suplente a cambiar");
    int indiceSuplente = sc.nextInt();

    Jugador jugadorTitular = titularesArray.get(indiceTitular - 1);
    Jugador jugadorSuplente = suplentesArray.get(indiceSuplente - 1);

    usuarioEquipo.realizarCambio(jugadorTitular,jugadorSuplente);
}