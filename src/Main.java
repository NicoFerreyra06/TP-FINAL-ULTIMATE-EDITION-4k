import Modelo.*;
import enums.Eformacion;
import enums.EstiloJuego;
import enums.Posicion;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
void main() {

    try{
        // --- 1. CREACIÓN DE EQUIPOS DE PRUEBA ---
        System.out.println("Creando equipos para la prueba...");
        Estadio estGenerico = new Estadio("Estadio Municipal", 15000, 15.0);
        Tactica tacDefault = new Tactica(Eformacion.F_442, EstiloJuego.EQUILIBRADO);
        DirectorTecnico dtDefault = new DirectorTecnico("DT Prueba", 50, "Local", 70, tacDefault);

        Equipo equipoA = new Equipo("Equipo Nico", estGenerico, dtDefault, 100000);
        Equipo equipoB = new Equipo("Equipo Lucio", estGenerico, dtDefault, 100000);
        Equipo equipoC = new Equipo("Equipo Tomas", estGenerico, dtDefault, 100000);
        Equipo equipoD = new Equipo("Equipo Juan", estGenerico, dtDefault, 100000);

        // Añadir jugadores simplificados
        for(int i=0; i< 11; i++) {
            equipoA.agregarJugador(new Jugador("Jugador Nico " +i, 25, "Local", Posicion.MEDIOCAMPISTA, 70, 70, 90));
            equipoB.agregarJugador(new Jugador("JugadorB"+i, 25, "Local", Posicion.MEDIOCAMPISTA, 75, 75, 90));
            equipoC.agregarJugador(new Jugador("JugadorC"+i, 25, "Local", Posicion.MEDIOCAMPISTA, 65, 65, 90));
            equipoD.agregarJugador(new Jugador("JugadorD"+i, 25, "Local", Posicion.MEDIOCAMPISTA, 80, 80, 90));
        }

        // --- 2. CREACIÓN Y CONFIGURACIÓN DE LA LIGA ---
        System.out.println("\nCreando la Liga...");
        Liga ligaDePrueba = new Liga("Liga de Prueba");

        // Anotamos los equipos en la liga
        ligaDePrueba.anotarEquipo(equipoA);
        ligaDePrueba.anotarEquipo(equipoB);
        ligaDePrueba.anotarEquipo(equipoC);
        ligaDePrueba.anotarEquipo(equipoD);

        // Generamos el fixture
        ligaDePrueba.generarFixture();

        Scanner scanner = new Scanner(System.in);

        while (!ligaDePrueba.isTerminada()){

            IO.println("Presione enter para simular la jornada");
            scanner.nextLine();

            ligaDePrueba.jugarProximaFecha(equipoA);
        }

        ligaDePrueba.campeonLiga();
    } catch (InterruptedException e){
        System.out.println("Errrorrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
    }

}

