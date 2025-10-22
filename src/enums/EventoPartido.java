package enums;

public enum EventoPartido {

    Tiro_Libre("Tiro libre"),
    Tiro_LibreLejano("Tiro libre indirecto"),
    Corner("Corner"),
    Penal("Penal"),
    Pos_Adelantada("Posición adelantada"),
    Ninguno("null");

    private final String event;
    EventoPartido(String event){this.event = event;}

    @Override
    public String toString() {
        return this.event;
    }

}
