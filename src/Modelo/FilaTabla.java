package Modelo;

public class FilaTabla implements Comparable<FilaTabla>{

    public Equipo equipo;
    public int puntos;
    public int jugados;
    public int ganados;
    public int empatados;
    public int perdidos;
    public int golesFavor;
    public int golesContra;
    public int diferenciaGoles;

    public FilaTabla(Equipo equipo) {
        this.equipo = equipo;
        this.puntos = 0;
        this.jugados = 0;
        this.ganados = 0;
        this.empatados = 0;
        this.perdidos = 0;
        this.golesFavor = 0;
        this.golesContra = 0;
        this.diferenciaGoles = 0;
    }

    public void registrarPartido (int golesFavor, int golesContra) {
        this.jugados++;
        this.golesFavor += golesFavor;
        this.golesContra += golesContra;

        this.diferenciaGoles += golesFavor - golesContra;

        if (golesFavor > golesContra) {
            this.ganados++;
            this.puntos += 3;
        } else if (golesFavor == golesContra) {
            this.empatados++;
            this.puntos += 1;
        } else {
            this.perdidos++;
        }
    }

    @Override
    public int compareTo(FilaTabla otraFila) {
        // 1. Por Puntos (descendente)
        if (this.puntos != otraFila.puntos) {
            return Integer.compare(otraFila.puntos, this.puntos);
        }
        // 2. Por Diferencia de Goles (descendente)
        if (this.diferenciaGoles != otraFila.diferenciaGoles) {
            return Integer.compare(otraFila.diferenciaGoles, this.diferenciaGoles);
        }
        // 3. Por Goles a Favor (descendente)
        return Integer.compare(otraFila.golesFavor, this.golesFavor);
    }
}
