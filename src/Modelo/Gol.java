package Modelo;

public class Gol {
    private int minuto;
    private Jugador autor;
    private Jugador asistidor;

    public Gol(int minuto, Jugador autor, Jugador asistidor) {
        this.minuto = minuto;
        this.autor = autor;
        this.asistidor = asistidor;
    }

    public int getMinuto() {
        return minuto;
    }

    public void setMinuto(int minuto) {
        this.minuto = minuto;
    }

    public Jugador getAutor() {
        return autor;
    }

    public void setAutor(Jugador autor) {
        this.autor = autor;
    }

    public Jugador getAsistidor() {
        return asistidor;
    }

    public void setAsistidor(Jugador asistidor) {
        this.asistidor = asistidor;
    }
}
