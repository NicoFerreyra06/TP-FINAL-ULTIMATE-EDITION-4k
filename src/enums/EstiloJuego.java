package enums;

public enum EstiloJuego {
    ULTRAOFENSIVO ("Ultra Ofensivo"),
    OFENSIVO ("Ofensivo"),
    EQUILIBRADO ("Equilibrado"),
    DEFENSIVO ("Defensivo"),
    ULTRADEFENSIVO ("Ultra Defensivo");

    private final String estiloJuego;

    EstiloJuego(String estiloJuego) {
        this.estiloJuego = estiloJuego;
    }

    @Override
    public String toString() {
        return this.estiloJuego;
    }
}
