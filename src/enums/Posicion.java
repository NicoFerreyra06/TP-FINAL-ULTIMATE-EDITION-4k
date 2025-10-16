package enums;

public enum Posicion {
    ARQUERO("Arquero"),
    DEFENSOR("Defensor"),
    MEDIOCAMPISTA("Mediocampista"),
    DELANTERO("Delantero");

    private final String posicion;

    Posicion(String posicion) {
        this.posicion = posicion;
    }

    @Override
    public String toString() {
        return this.posicion;
    }
}
