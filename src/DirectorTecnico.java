
public class DirectorTecnico extends Persona{
    private int experiencia; //0 a 100
    Tactica tacticaPreferida;

    public DirectorTecnico(String nombre, int edad, String nacionalidad, int experiencia, Tactica tacticaPreferida) {
        super(nombre, edad, nacionalidad);
        this.experiencia = experiencia;
        this.tacticaPreferida = tacticaPreferida;
    }

    // ==================== Getters y Setters ====================
    public int getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(int experiencia) {
        this.experiencia = experiencia;
    }

    public Tactica getTacticaPreferida() {
        return tacticaPreferida;
    }

    public void setTacticaPreferida(Tactica tacticaPreferida) {
        this.tacticaPreferida = tacticaPreferida;
    }

    @Override
    public String toString() {
        return String.format("%-20s | Táctica: %s (%s)",
                this.getNombre(),
                this.tacticaPreferida.getEformacion(),
                this.tacticaPreferida.getEstiloJuego()
        );
    }
}