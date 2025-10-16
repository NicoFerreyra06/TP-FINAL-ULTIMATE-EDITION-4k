import Modelo.*;
import enums.Eformacion;
import enums.EstiloJuego;
import enums.Posicion;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main() {
    System.out.println("--- Creando equipos de prueba para el Superclásico ---");

    //RIVER
    Estadio monumental = new Estadio("Mâs Monumental", 83196, 50.00);
    Tactica tacticaRiver = new Tactica(Eformacion.F_433, EstiloJuego.OFENSIVO);
    DirectorTecnico dtDemichelis = new DirectorTecnico("Martín Demichelis", 44, "Argentino", 80, tacticaRiver);
    Equipo river = new Equipo("River Plate", monumental, dtDemichelis, 25000000.00);

    // Agregando jugadores a River
    river.agregarJugador(new Jugador("Franco Armani", 38, "Argentino", Posicion.ARQUERO,100 , 90, 92));
    river.agregarJugador(new Jugador("Paulo Díaz", 30, "Chileno", Posicion.DEFENSOR, 65, 88, 90));
    river.agregarJugador(new Jugador("Leandro González Pirez", 33, "Argentino", Posicion.DEFENSOR, 60, 85, 88));
    river.agregarJugador(new Jugador("Milton Casco", 37, "Argentino", Posicion.DEFENSOR, 75, 78, 85));
    river.agregarJugador(new Jugador("Rodrigo Villagra", 24, "Argentino", Posicion.MEDIOCAMPISTA, 70, 82, 94));
    river.agregarJugador(new Jugador("Nacho Fernández", 35, "Argentino", Posicion.MEDIOCAMPISTA, 88, 65, 87));
    river.agregarJugador(new Jugador("Esequiel Barco", 26, "Argentino", Posicion.MEDIOCAMPISTA, 85, 50, 91));
    river.agregarJugador(new Jugador("Claudio Echeverri", 19, "Argentino", Posicion.DELANTERO, 90, 40, 95));
    river.agregarJugador(new Jugador("Facundo Colidio", 25, "Argentino", Posicion.DELANTERO, 86, 45, 89));
    river.agregarJugador(new Jugador("Miguel Borja", 32, "Colombiano", Posicion.DELANTERO, 92, 30, 86));
    river.agregarJugador(new Jugador("Pablo Solari", 24, "Argentino", Posicion.DELANTERO, 84, 50, 93));

    //Boca
    System.out.println("\nCreando a Boca Juniors...");
    Estadio bombonera = new Estadio("La Bombonera", 54000, 45.00);
    Tactica tacticaBoca = new Tactica(Eformacion.F_442, EstiloJuego.EQUILIBRADO);
    DirectorTecnico dtMartinez = new DirectorTecnico("Diego Martínez", 46, "Argentino", 78, tacticaBoca);
    Equipo boca = new Equipo("Boca Juniors", bombonera, dtMartinez, 22000000.00);

    // Agregando jugadores a Boca
    boca.agregarJugador(new Jugador("Sergio Romero", 38, "Argentino", Posicion.ARQUERO, 25, 89, 90));
    boca.agregarJugador(new Jugador("Marcos Rojo", 35, "Argentino", Posicion.DEFENSOR, 68, 87, 84));
    boca.agregarJugador(new Jugador("Cristian Lema", 35, "Argentino", Posicion.DEFENSOR, 58, 89, 87));
    boca.agregarJugador(new Jugador("Luis Advíncula", 35, "Peruano", Posicion.DEFENSOR, 80, 75, 89));
    boca.agregarJugador(new Jugador("Pol Fernández", 33, "Argentino", Posicion.MEDIOCAMPISTA, 78, 79, 88));
    boca.agregarJugador(new Jugador("Ezequiel Fernández", 22, "Argentino", Posicion.MEDIOCAMPISTA, 75, 84, 96));
    boca.agregarJugador(new Jugador("Cristian Medina", 22, "Argentino", Posicion.MEDIOCAMPISTA, 83, 70, 94));
    boca.agregarJugador(new Jugador("Kevin Zenón", 23, "Argentino", Posicion.MEDIOCAMPISTA, 86, 65, 91));
    boca.agregarJugador(new Jugador("Edinson Cavani", 38, "Uruguayo", Posicion.DELANTERO, 91, 45, 85));
    boca.agregarJugador(new Jugador("Miguel Merentiel", 29, "Uruguayo", Posicion.DELANTERO, 87, 48, 90));
    boca.agregarJugador(new Jugador("Luca Langoni", 23, "Argentino", Posicion.DELANTERO, 85, 40, 92));


    //Datos equipos
    System.out.println(" ===DATOS EQUIPOS== ");
    IO.println(river);
    IO.println(boca);

    Partido partido = new Partido(river, boca);

    //simulacion rapida
    partido.simularRapido();

    //simulacion "Interactiva"
    try {
        partido.simularInteractivo();
    } catch (InterruptedException e) {
        System.out.println("INTERRUPTED");
    }


}
