package Modelo;
import enums.*;

public class Tactica {
    Eformacion eformacion;
    EstiloJuego estiloJuego;

    public Tactica(Eformacion eformacion, EstiloJuego estiloJuego) {
        this.eformacion = eformacion;
        this.estiloJuego = estiloJuego;
    }

    public Eformacion getEformacion() {
        return eformacion;
    }

    public void setEformacion(Eformacion eformacion) {
        this.eformacion = eformacion;
    }

    public EstiloJuego getEstiloJuego() {
        return estiloJuego;
    }

    public void setEstiloJuego(EstiloJuego estiloJuego) {
        this.estiloJuego = estiloJuego;
    }

    @Override
    public String toString() {
        return String.format("Táctica: %-7s | Estilo: %s",
                this.eformacion,
                this.estiloJuego
        );
    }
}