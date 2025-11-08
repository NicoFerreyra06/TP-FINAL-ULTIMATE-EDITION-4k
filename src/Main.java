import Exceptions.EquipoExistenteException;
import Exceptions.JugadorNoEncontradoException;
import Exceptions.LimiteEntrenamientoException;
import Gestora.GestoraEquipos;
import Gestora.JsonUtiles;
import Modelo.Competicion.Copa;
import Modelo.Competicion.Liga;
import Modelo.Equipo.Equipo;
import Modelo.Persona.Jugador;
import Modelo.Podios.PodiosDeCompeticion;
import org.json.JSONObject;

public static void realizarCambios(Equipo usuarioEquipo, Scanner sc) {

    ArrayList<Jugador> titularesArray = new ArrayList<>(usuarioEquipo.getTitulares());
    ArrayList<Jugador> suplentesArray = new ArrayList<>(usuarioEquipo.getSuplentes());

    boolean check = false;

    while (!check) {
        try {
            int i = 0;
            System.out.println("\n=== TITULARES ===");
            for (Jugador j : usuarioEquipo.getTitulares()) {
                System.out.println(i + 1 + "-" + j.getNombre());
                i++;
            }

            System.out.println("\nIngrese el numero del titular a cambiar");
            int indiceTitular = sc.nextInt();

            if (indiceTitular < 1 || indiceTitular > titularesArray.size()) {
                throw new IllegalArgumentException();
            }

            i = 0;
            System.out.println("\n=== SUPLENTES ===");
            for (Jugador j : usuarioEquipo.getSuplentes()) {
                System.out.println(i + 1 + "-" + j.getNombre());
                i++;
            }

            System.out.println("Ingrese el numero del suplente a cambiar");
            int indiceSuplente = sc.nextInt();

            if (indiceSuplente < 1 || indiceSuplente > suplentesArray.size()) {
                throw new IllegalArgumentException();
            }

            Jugador jugadorTitular = titularesArray.get(indiceTitular - 1);
            Jugador jugadorSuplente = suplentesArray.get(indiceSuplente - 1);

            usuarioEquipo.realizarCambio(jugadorTitular, jugadorSuplente);

            check = true;
        } catch (InputMismatchException e) {
            System.out.println("Error, debe ingresar un numero valido");
            sc.nextLine();
        } catch (IllegalArgumentException e) {
            System.out.println("Error, numero fuera de rango. Intente nuevamente ");
        }catch (JugadorNoEncontradoException e)
        {
            System.out.println("Error en el cambio: "+ e.getMessage());
            System.out.println("Intente nuevamente");
        }

        catch (Exception e) {
            System.out.println("Ocurrio un error inesperado. Intente nuevamente");
            sc.nextLine();
        }
    }
}

public static Equipo seleccionarEquipo(Scanner sc, ArrayList<Equipo> listaDeEquipos) {
    boolean check = false;
    Equipo usuario = null;
    int indice;

    while (!check) {
        try {
            System.out.println("\n==== LISTA DE EQUIPOS DISPONIBLES ====");
            for (int i = 0; i < listaDeEquipos.size(); i++) {
                System.out.println((i + 1) + ". " + listaDeEquipos.get(i).getNombre());
            }

            System.out.print("Seleccione su equipo (1 - " + listaDeEquipos.size() + "): ");
            indice = sc.nextInt();

            if (indice < 1 || indice > listaDeEquipos.size()) {
                throw new ArrayIndexOutOfBoundsException();
            }

            usuario = listaDeEquipos.get(indice - 1);
            System.out.println("\nElegiste: " + usuario.getNombre());
            check = true;

        } catch (IndexOutOfBoundsException e) {
            System.out.println("No selecciono bien el equipo, intente de nuevo!");
        } catch (InputMismatchException e) {
            System.out.println("Debe ingresar un numero valido");
            sc.nextLine();
        }
    }
    return usuario;
}

public static int menuOpciones(Scanner sc, int limiteEntrenamiento, int entrenamientosJornada, Liga liga) {

    boolean check = false;
    int opcion = -1;

    while (!check) {
        try {
            System.out.println("\n--- Jornada " + liga.getJornada() + " ---");
            System.out.println("Menú de Acciones:");
            System.out.println("1. Jugar próxima fecha");
            System.out.println("2. Ver tabla de posiciones");
            int restantes = limiteEntrenamiento - entrenamientosJornada;
            System.out.println("3. Mostrar el equipo");
            System.out.println("4. Entrenar tus jugadores, (entrenamientos restantes: " + restantes + ")");
            System.out.println("5. Hacer cambios");
            System.out.println("6. Salir del juego");
            System.out.print("Elegi una opción: (1 - 6)");

            opcion = sc.nextInt();
            if (opcion < 1 || opcion > 6) {
                throw new InputMismatchException();
            }

            check = true;

        } catch (InputMismatchException e) {
            System.out.println("Seleccione un numero valido entre 1 y 7");
            sc.nextLine();
        }
    }
    return opcion;
}

public static int menuEntrenamiento(int entrenamientosJornada, int limiteEntrenamiento, Equipo usuarioEquipo) throws LimiteEntrenamientoException {
    if (entrenamientosJornada < limiteEntrenamiento) {
        usuarioEquipo.entrenarEquipo();
        return entrenamientosJornada + 1;
    } else {
        IO.println("Limites por jornada alcanzados");
        return entrenamientosJornada;
    }
}

public static void mostrarEquipo(Equipo equipo) {
    ArrayList<Jugador> titularesArray = new ArrayList<>(equipo.getTitulares());
    ArrayList<Jugador> suplentesArray = new ArrayList<>(equipo.getSuplentes());

    System.out.println("\n=== TITULARES ===");
    for (Jugador jugador : titularesArray) {
        System.out.println(jugador.toString());
    }

    System.out.println("\n=== SUPLENTES ===");
    for (Jugador jugador : suplentesArray) {
        System.out.println(jugador.toString());
    }
}


void main() {

    GestoraEquipos gestoraEquipos = new GestoraEquipos("equipos");
    gestoraEquipos.cargarEquipos("equipos");
    ArrayList <Equipo> listaDeEquipos = (ArrayList<Equipo>) gestoraEquipos.getElementos();

    System.out.println("¡Bienvenido al Mánager de Fútbol! ⚽");
    Scanner sc = new Scanner(System.in);
    Liga liga = null;
    Copa copa = null;
    Equipo usuarioEquipo = null;
    boolean partidaLista = false;

    while (!partidaLista) {
        try {
            System.out.println("1. Nueva Partida");
            System.out.println("2. Cargar Partida");
            System.out.print("Seleccione una opción: ");
            int opcionInicio = sc.nextInt();

            if (opcionInicio == 1) {
                System.out.println("Creando la liga, copa y equipos");
                liga = new Liga("Liga Marino");
                copa = new Copa("Copa oscar");

                usuarioEquipo = seleccionarEquipo(sc, listaDeEquipos);

                for (Equipo equipo : listaDeEquipos) {
                    liga.anotarEquipo(equipo);
                }

                for (int i = 0; i < 16; i++){
                    copa.anotarEquipo(listaDeEquipos.get(i));
                }



                liga.setNombreEquipoUsuario(usuarioEquipo.getNombre());

                liga.generarFixture();
                partidaLista = true;

            } else if (opcionInicio == 2) {

                liga = cargarLiga();
                copa = cargarCopa();

                if (liga != null || copa != null) {

                    String nombreEquipoUsuario = liga.getNombreEquipoUsuario();
                    usuarioEquipo = liga.getEquipos().get(nombreEquipoUsuario);

                    if (usuarioEquipo == null) {
                        System.out.println("Error fatal: No se encontró tu equipo (" + nombreEquipoUsuario + ") en la liga cargada.");
                    } else {
                        System.out.println("¡Partida cargada! Su equipo es: " + usuarioEquipo.getNombre());
                        partidaLista = true;
                    }
                } else {
                    System.out.println("No se pudo cargar la partida. Intente de nuevo.");
                }
            } else {
                System.out.println("Opción no válida.");
            }
        } catch (IllegalArgumentException | EquipoExistenteException e) {
            System.out.println(e.getMessage());
            sc.nextLine();
        }
    }

    try {
        boolean salir = false;
        int entrenamientosJornada = 0;
        final int limiteEntrenamiento = 1;

        while (!liga.isTerminada() && !salir) {
            int opcion = menuOpciones(sc, limiteEntrenamiento, entrenamientosJornada, liga);

            switch (opcion) {
                case 1:
                    if (copa != null){
                        if (liga.getJornada() == 8 || liga.getJornada() == 16 ||
                                liga.getJornada() == 24 || liga.getJornada() == 33){

                            copa.jugarProximaFecha(usuarioEquipo, sc);
                            copa.mostrarBracket();
                        }
                    }

                    liga.jugarProximaFecha(usuarioEquipo, sc);
                    entrenamientosJornada = 0;
                try {
                    System.out.println("Guardando partida....");
                    JsonUtiles.grabarUnJson(liga.toJSON(), "Liga.json");
                    JsonUtiles.grabarUnJson(copa.toJSON(), "Copa.json");
                    System.out.println("Partida guardada correctamente! ");
                } catch (Exception e) {
                    System.out.println("ERROR AL GUARDAR PARTIDA!");
                }

                    break;
                case 2:
                    liga.mostrarTabla();
                    break;
                case 3:
                    mostrarEquipo(usuarioEquipo);
                    break;
                case 4:
                    entrenamientosJornada = menuEntrenamiento(entrenamientosJornada, limiteEntrenamiento, usuarioEquipo);
                    break;

                case 5:
                    realizarCambios(usuarioEquipo, sc);
                    break;
                case 6:
                    salir = true;
                    break;
            }
        }

        if (liga.isTerminada()) {
            System.out.println("\n--- ¡LA LIGA HA TERMINADO! ---");

            PodiosDeCompeticion<Liga> podiosDeCompeticion = new PodiosDeCompeticion<>(liga);
            podiosDeCompeticion.mostrarEstadisticasIndivuduales();
            liga.podioLiga();
        }
    } catch (LimiteEntrenamientoException | InterruptedException e){
        System.out.println(e.getMessage());
    }
}

public Liga cargarLiga() {
    String jsonString = JsonUtiles.leer("Liga");

    JSONObject jsonPartida = new JSONObject(jsonString);

    Liga ligaCargada = new Liga(jsonPartida);

    System.out.println("¡Partida cargada! Listo para jugar la jornada " + ligaCargada.getJornada());

    return ligaCargada;
}

public Copa cargarCopa() {
    String jsonString = JsonUtiles.leer("Copa");

    JSONObject jsonPartida = new JSONObject(jsonString);

    Copa copaCargada = new Copa(jsonPartida);

    System.out.println("¡Partida cargada! Listo para jugar la jornada ");

    return copaCargada;
}