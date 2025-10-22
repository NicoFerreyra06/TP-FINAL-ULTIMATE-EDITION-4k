package Modelo.Partido;
import enums.EventoPartido;

import java.util.Random;


public class GestionEventoDePartido {

    private Random random = new Random();
    private boolean offside = false;


    public EventoPartido generarlo(boolean afterGol) {
        double r = random.nextDouble();


        if (afterGol && r < 0.05) {
            if (random.nextDouble() < 0.5) {
                return EventoPartido.Pos_Adelantada;
            }
        }

        if (r < 0.10) return EventoPartido.Penal;
        else if (r < 0.30) return EventoPartido.Tiro_LibreLejano;
        else if (r < 0.25) return EventoPartido.Tiro_Libre;
        else if (r < 0.40) return EventoPartido.Corner;
        else return EventoPartido.Ninguno;
    }

    public double modificarProbGol(EventoPartido evento, double proBase) {

        return switch (evento) {
            case Penal -> proBase * 6;
            case Tiro_Libre -> proBase * 3;
            case Tiro_LibreLejano -> proBase * 2;
            case Corner -> proBase * 1.5;
            default -> proBase;
        };


    }
}
