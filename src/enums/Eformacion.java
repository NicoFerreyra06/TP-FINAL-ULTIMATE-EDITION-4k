package enums;

public enum Eformacion {
    F_433("4-3-3"),
    F_442("4-4-2"),
    F_4231 ("4-2-3-1"),
    F_4141 ("4-1-4-1"),
    F_451 ("4-5-1"),
    F_532("5-3-2"),
    F_352("3-5-2");

    private final String nombreFormacion;

    Eformacion(String nombreFormacion) {
        this.nombreFormacion = nombreFormacion;
    }

    @Override
    public String toString() {
        return this.nombreFormacion;
    }
}
