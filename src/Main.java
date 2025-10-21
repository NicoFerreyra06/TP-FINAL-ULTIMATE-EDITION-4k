import Modelo.*;
import enums.Eformacion;
import enums.EstiloJuego;
import enums.Posicion;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
void main() {


    try {
        // --- 1. CREACIÓN DE EQUIPOS DE PRUEBA ---
        System.out.println("Creando equipos para la prueba...");
        Estadio estGenerico = new Estadio("Estadio Municipal", 15000, 15.0);
        Tactica tacDefault = new Tactica(Eformacion.F_442, EstiloJuego.EQUILIBRADO);
        DirectorTecnico dtDefault = new DirectorTecnico("DT Prueba", 50, "Local", 70, tacDefault);

        Equipo equipoA = new Equipo("Equipo Nico", estGenerico, dtDefault, 100000);
        Equipo equipoB = new Equipo("Equipo Lucio", estGenerico, dtDefault, 100000);
        Equipo equipoC = new Equipo("Equipo Tomas", estGenerico, dtDefault, 100000);
        Equipo equipoD = new Equipo("Equipo Juan", estGenerico, dtDefault, 100000);
        Equipo equipoE = new Equipo("Equipo Oscar", estGenerico, dtDefault, 100000);
        Equipo equipoF = new Equipo("Equipo Ciro", estGenerico, dtDefault, 100000);
        Equipo equipoG = new Equipo("Equipo Nahuel", estGenerico, dtDefault, 100000);
        Equipo equipoH = new Equipo("Equipo Juanito", estGenerico, dtDefault, 100000);
        Equipo equipoI = new Equipo("Equipo Pepe", estGenerico, dtDefault, 100000);
        Equipo equipoJ = new Equipo("Equipo Fer", estGenerico, dtDefault, 100000);
        Equipo equipoK = new Equipo("Equipo Ger", estGenerico, dtDefault, 100000);
        Equipo equipoL = new Equipo("Equipo Ema", estGenerico, dtDefault, 100000);
        Equipo equipoM = new Equipo("Equipo Momo", estGenerico, dtDefault, 100000);
        Equipo equipoN = new Equipo("Equipo Bananirou", estGenerico, dtDefault, 100000);
        Equipo equipoO = new Equipo("Equipo Villano", estGenerico, dtDefault, 100000);
        Equipo equipoP = new Equipo("Equipo Coleccionista", estGenerico, dtDefault, 100000);

        // Añadir jugadores simplificados
        for (int i = 0; i < 11; i++) {
            equipoA.agregarJugador(new Jugador("Jugador Nico " + i, 25, "Local", Posicion.MEDIOCAMPISTA, 70, 70, 90));
            equipoB.agregarJugador(new Jugador("Jugador B" + i, 25, "Local", Posicion.MEDIOCAMPISTA, 75, 75, 90));
            equipoC.agregarJugador(new Jugador("Jugador C" + i, 25, "Local", Posicion.MEDIOCAMPISTA, 65, 65, 90));
            equipoD.agregarJugador(new Jugador("Jugador D" + i, 25, "Local", Posicion.MEDIOCAMPISTA, 80, 80, 90));
            equipoE.agregarJugador(new Jugador("Jugador E" + i, 25, "Local", Posicion.MEDIOCAMPISTA, 60, 60, 90));
            equipoF.agregarJugador(new Jugador("Jugador F" + i, 25, "Local", Posicion.MEDIOCAMPISTA, 70, 70, 90));
            equipoG.agregarJugador(new Jugador("Jugador G" + i, 25, "Local", Posicion.MEDIOCAMPISTA, 80, 80, 90));
            equipoH.agregarJugador(new Jugador("Jugador H" + i, 25, "Local", Posicion.MEDIOCAMPISTA, 60, 60, 90));
            equipoI.agregarJugador(new Jugador("Jugador I" + i, 25, "Local", Posicion.MEDIOCAMPISTA, 70, 70, 90));
            equipoJ.agregarJugador(new Jugador("Jugador J" + i, 25, "Local", Posicion.MEDIOCAMPISTA, 60, 60, 90));
            equipoK.agregarJugador(new Jugador("Jugador K" + i, 25, "Local", Posicion.MEDIOCAMPISTA, 70, 70, 90));
            equipoL.agregarJugador(new Jugador("Jugador L" + i, 25, "Local", Posicion.MEDIOCAMPISTA, 60, 60, 90));
            equipoM.agregarJugador(new Jugador("Jugador M" + i, 25, "Local", Posicion.MEDIOCAMPISTA, 70, 70, 90));
            equipoN.agregarJugador(new Jugador("Jugador N" + i, 25, "Local", Posicion.MEDIOCAMPISTA, 60, 60, 90));
            equipoO.agregarJugador(new Jugador("Jugador O" + i, 25, "Local", Posicion.MEDIOCAMPISTA, 70, 70, 90));
            equipoP.agregarJugador(new Jugador("Jugador P" + i, 25, "Local", Posicion.MEDIOCAMPISTA, 60, 60, 90));
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

        while (!ligaDePrueba.isTerminada()) {

            IO.println("Presione enter para simular la jornada");
            scanner.nextLine();

            ligaDePrueba.jugarProximaFecha(equipoA);
        }

        // --- 2. CREACIÓN Y CONFIGURACIÓN DE LA COPA ---
        System.out.println("\nCreando la Liga...");
        Copa copaDePrueba = new Copa("Copa de Prueba");

        //Anotamos los equipos en la copa
        copaDePrueba.anotarEquipo(equipoA);
        copaDePrueba.anotarEquipo(equipoB);
        copaDePrueba.anotarEquipo(equipoC);
        copaDePrueba.anotarEquipo(equipoD);
        copaDePrueba.anotarEquipo(equipoE);
        copaDePrueba.anotarEquipo(equipoF);
        copaDePrueba.anotarEquipo(equipoG);
        copaDePrueba.anotarEquipo(equipoH);
        copaDePrueba.anotarEquipo(equipoI);
        copaDePrueba.anotarEquipo(equipoJ);
        copaDePrueba.anotarEquipo(equipoK);
        copaDePrueba.anotarEquipo(equipoL);
        copaDePrueba.anotarEquipo(equipoM);
        copaDePrueba.anotarEquipo(equipoN);
        copaDePrueba.anotarEquipo(equipoO);
        copaDePrueba.anotarEquipo(equipoP);

        // --- 3. EJECUCION DE LA PRUEBA ---
        copaDePrueba.jugarProximaFecha(equipoA); //Le pasas el equipo que usa el usuario.


        ligaDePrueba.campeonLiga();
    } catch (InterruptedException e) {
        System.out.println("Errrorrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
    }




}

