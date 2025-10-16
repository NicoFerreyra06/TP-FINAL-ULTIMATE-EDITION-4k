package Modelo;

public class Tactica {
    Eformacion eformacion;
    Estilojuego estiloJuego;

    public Tactica(Eformacion eformacion, Estilojuego estiloJuego) {
        this.eformacion = eformacion;
        this.estiloJuego = estiloJuego;
    }

    public Eformacion getEformacion() {
        return eformacion;
    }

    public void setEformacion(Eformacion eformacion) {
        this.eformacion = eformacion;
    }

    public Estilojuego getEstiloJuego() {
        return estiloJuego;
    }

    public void setEstiloJuego(Estilojuego estiloJuego) {
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