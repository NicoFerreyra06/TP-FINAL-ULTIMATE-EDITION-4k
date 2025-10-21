// Clases del Modelo

import enums.*;
import Modelo.Competicion.*;
import Modelo.Equipo.*;
import Modelo.Partido.*;
import Modelo.Persona.*;
import Modelo.Usuario.*;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="SRun"/> or
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
            equipoA.agregarJugador(new Jugador("Jugador Nico " + i, 25, "Local", Posicion.MEDIOCAMPISTA, 99, 99, 99));
            equipoB.agregarJugador(new Jugador("Jugador B" + i, 25, "Local", Posicion.MEDIOCAMPISTA, 75, 75, 90));
            equipoC.agregarJugador(new Jugador("Jugador C" + i, 25, "Local", Posicion.MEDIOCAMPISTA, 65, 65, 90));
            equipoD.agregarJugador(new Jugador("Jugador D" + i, 25, "Local", Posicion.MEDIOCAMPISTA, 80, 80, 80));
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
        ligaDePrueba.anotarEquipo(equipoE);
        ligaDePrueba.anotarEquipo(equipoF);
        ligaDePrueba.anotarEquipo(equipoG);
        ligaDePrueba.anotarEquipo(equipoH);
        ligaDePrueba.anotarEquipo(equipoI);
        ligaDePrueba.anotarEquipo(equipoJ);
        ligaDePrueba.anotarEquipo(equipoL);
        ligaDePrueba.anotarEquipo(equipoM);
        ligaDePrueba.anotarEquipo(equipoN);
        ligaDePrueba.anotarEquipo(equipoO);
        ligaDePrueba.anotarEquipo(equipoP);
        ligaDePrueba.anotarEquipo(equipoK);
        ligaDePrueba.anotarEquipo(equipoL);
        ligaDePrueba.anotarEquipo(equipoM);
        ligaDePrueba.anotarEquipo(equipoN);
        ligaDePrueba.anotarEquipo(equipoO);
        ligaDePrueba.anotarEquipo(equipoP);

        // Generamos el fixture
        ligaDePrueba.generarFixture();

        Scanner scanner = new Scanner(System.in);

        /*while (!ligaDePrueba.isTerminada()) {

            IO.println("Presione enter para simular la jornada");
            scanner.nextLine();

            ligaDePrueba.jugarProximaFecha(equipoA);
        }*/

        // --- 1. CREACIÓN Y CONFIGURACIÓN DE LA COPA ---
        System.out.println("\nCreando la Copa...");
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

        // --- 2. EJECUCION DE LA PRUEBA ---
        //copaDePrueba.jugarProximaFecha(equipoA); //Le pasas el equipo que usa el usuario.
        copaDePrueba.mostrarBracket();

        // El bucle se ejecuta mientras quede más de 1 equipo
        while (copaDePrueba.getEquipos().size() > 1) {
            System.out.println("\nPresiona Enter para simular la siguiente ronda...");
            scanner.nextLine();

            // Juega la ronda (anuncia partidos, simula y define ganadores)
            copaDePrueba.jugarProximaFecha(equipoA);

            // Muestra el bracket actualizado con los resultados
            copaDePrueba.mostrarBracket();
        }

        System.out.println("\n--- FIN DEL TORNEO ---");
        // No necesitas llamar a un metodo "campeonCopa", la misma clase ya lo anuncia.





        ligaDePrueba.campeonLiga();
    } catch (InterruptedException e) {
        System.out.println("Errrorrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
    }




}

public static void cargarEquipos(){
    ArrayList<Equipo> equipos = new ArrayList<>();

    Estadio estadioRosario = new Estadio("Gigante de Arroyito",51000,55.0);
    Tactica tacticaRosario = new Tactica(Eformacion.F_4231,EstiloJuego.OFENSIVO);
    DirectorTecnico dtRosario = new DirectorTecnico("Ariel Holan",65,"Argentina",73,tacticaRosario);
    Equipo rosarioCentral = new Equipo("Rosario Central",estadioRosario,dtRosario,5000000.0);

    rosarioCentral.agregarJugador(new Jugador("Axel Werner",29,"Argentina",Posicion.ARQUERO,24,67,70));
    rosarioCentral.agregarJugador(new Jugador("Agustin Sández",24,"Paraguay",Posicion.DEFENSOR,60,69,65));
    rosarioCentral.agregarJugador(new Jugador("Juan Komar",29,"Argentina",Posicion.DEFENSOR,50,69,72));
    rosarioCentral.agregarJugador(new Jugador("Facundo Mallo",30,"Uruguay",Posicion.DEFENSOR,52,66,75));
    rosarioCentral.agregarJugador(new Jugador("Emanuel Coronel",28,"Argentina",Posicion.DEFENSOR,58,64,61));
    rosarioCentral.agregarJugador(new Jugador("Ignacio Malcorra",38,"Argentina",Posicion.MEDIOCAMPISTA,75,39,60));
    rosarioCentral.agregarJugador(new Jugador("Franco Ibarra",24,"Argentina",Posicion.MEDIOCAMPISTA,63,67,62));
    rosarioCentral.agregarJugador(new Jugador("Jaminton Campaz",25,"Colombia",Posicion.MEDIOCAMPISTA,69,49,62));
    rosarioCentral.agregarJugador(new Jugador("Angel Di Maria",37,"Argentina",Posicion.DELANTERO,82,58,70));
    rosarioCentral.agregarJugador(new Jugador("Enzo Giménez",27,"Paraguay",Posicion.MEDIOCAMPISTA,63,56,62));
    rosarioCentral.agregarJugador(new Jugador("Alejo Véliz",22,"Argentina",Posicion.DELANTERO,76,48,78));
    rosarioCentral.agregarJugador(new Jugador("Enzo Copetti",29,"Argentina",Posicion.DELANTERO,68,35,74));
    rosarioCentral.agregarJugador(new Jugador("Juan Manuel Elordi",31,"Argentina",Posicion.DEFENSOR,58,60,62));
    rosarioCentral.agregarJugador(new Jugador("Agustin Modica",22,"Argentina",Posicion.DELANTERO,62,33,55));
    rosarioCentral.agregarJugador(new Jugador("Federico Navarro",25,"Argentina",Posicion.MEDIOCAMPISTA,62,61,62));
    rosarioCentral.agregarJugador(new Jugador("Samuel Beltrán",21,"Argentina",Posicion.MEDIOCAMPISTA,53,51,56));
    rosarioCentral.agregarJugador(new Jugador("Jorge Braun",39,"Argentina",Posicion.ARQUERO,18,70,69));
    rosarioCentral.agregarJugador(new Jugador("Gioavanni Cantizano",18,"Argentina",Posicion.MEDIOCAMPISTA,63,51,60));
    rosarioCentral.agregarJugador(new Jugador("Francesco Lo Celso",25,"Argentina",Posicion.MEDIOCAMPISTA,63,58,62));
    rosarioCentral.agregarJugador(new Jugador("Santiago López",19,"Argentina",Posicion.DELANTERO,66,45,62));
    

}

